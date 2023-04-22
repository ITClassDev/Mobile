package ru.slavapmk.shtp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.ActivityLoginBinding
import ru.slavapmk.shtp.io.dto.achievements.AchievementsResponse
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse
import ru.slavapmk.shtp.io.dto.notifications.AllNotifications
import ru.slavapmk.shtp.io.dto.tasks.DailyChallenge
import ru.slavapmk.shtp.io.dto.user.LeaderBoard
import java.net.ConnectException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loadings = ArrayList<Disposable>()

    override fun onStop() {
        super.onStop()
        for (loading in loadings)
            loading.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.login_activity_bar_color)
        setContentView(binding.root)

        val prefs = getSharedPreferences(Values.APP_ID, MODE_PRIVATE)
        val loadedToken = prefs.getString(Values.AUTH_KEY_ID, null)
        loadedToken?.let {
            Values.token = it
            downloadDataAndRun()
            return
        }

        binding.loginLayout.visibility = View.VISIBLE
        binding.buttonLogin.visibility = View.VISIBLE
        binding.statusProgressBar.visibility = View.GONE

        binding.inputEmail.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            hideErrors()
        }
        binding.inputPassword.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            hideErrors()
        }
        binding.buttonLogin.setOnClickListener {
            hideErrors()
            binding.statusProgressBar.visibility = View.VISIBLE

            loadings.add(
                Values.api.login(
                    AuthLoginRequest(
                        binding.inputEmail.text.toString(), binding.inputPassword.text.toString()
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ loginResponse ->
                        Values.token = "Bearer ${loginResponse.accessToken}"
                        getSharedPreferences(Values.APP_ID, MODE_PRIVATE).edit {
                            putString(Values.AUTH_KEY_ID, Values.token).apply()
                        }
                        downloadDataAndRun()
                    }, {
                        when (it) {
                            is ConnectException -> binding.messageNoServerAccess.visibility =
                                View.VISIBLE

                            is HttpException -> when (it.code()) {
                                401 ->
                                    binding.messageIncorrectCredentials.visibility = View.VISIBLE

                                422 ->
                                    binding.messageIncorrectInput.visibility = View.VISIBLE
                            }

                            else -> binding.messageNetworkError.visibility = View.VISIBLE
                        }
                        binding.statusProgressBar.visibility = View.GONE
                    })
            )
        }
    }

    private fun downloadDataAndRun() {
        loadings.add(
            Observable.concatArray(
                Values.api.getMe(Values.token),
                Values.api.getLeaderBoard(),
                Values.api.getAchievements(Values.token),
                Values.api.getNotifications(Values.token),
                Values.api.getDailyChallenge(Values.token)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    when (it) {
                        is AuthMeResponse -> Values.user = it.user

                        is LeaderBoard -> Values.leaderboard = it

                        is AchievementsResponse -> Values.achievements = it.achievements

                        is AllNotifications -> Values.notifications = it

                        is DailyChallenge -> Values.dailyChallenge = it
                    }
                }, {
                    throw RuntimeException(it)
                }, {
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    myIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(myIntent)
                })
        )
    }

    private fun hideErrors() {
        binding.messageIncorrectCredentials.visibility = View.GONE
        binding.messageNoServerAccess.visibility = View.GONE
        binding.messageNetworkError.visibility = View.GONE
        binding.messageIncorrectInput.visibility = View.GONE
    }
}