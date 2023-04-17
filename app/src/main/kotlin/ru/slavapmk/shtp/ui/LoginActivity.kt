package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.adapter.rxjava3.HttpException
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.ActivityLoginBinding
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import java.net.ConnectException


private const val appId = "ru.slavapmk.shtp"
private const val authToken = "AUTH_TOKEN"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences(appId, MODE_PRIVATE)
        val loadedToken = prefs.getString(authToken, null)
        loadedToken?.let {
            Values.token = it
            Values.api.getMe(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { me ->
                    Values.user = me.user
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    this@LoginActivity.startActivity(myIntent)
                }
            return
        }

        binding.loginLayout.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
        binding.statusProgress.visibility = View.GONE

        binding.loginInput.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            hideErrors()
        }
        binding.passwordInput.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            hideErrors()
        }
    }

    @SuppressLint("CheckResult")
    fun login(view: View?) {
        hideErrors()
        binding.statusProgress.visibility = View.VISIBLE

        Values.api.login(
            AuthLoginRequest(
                binding.loginInput.text.toString(), binding.passwordInput.text.toString()
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ loginResponse ->
                Values.token = "Bearer ${loginResponse.accessToken}"

                Values.api.getMe(Values.token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { me ->
                        Values.user = me.user
                        getSharedPreferences(appId, MODE_PRIVATE).edit {
                            this.putString(authToken, Values.token).apply()
                        }
                        val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        this@LoginActivity.startActivity(myIntent)
                    }
            }, {
                if (it is ConnectException)
                    binding.noInternet.visibility = View.VISIBLE
                else if (it is HttpException && it.code() == 422)
                    binding.incorrectDataStatus.visibility = View.VISIBLE
                else
                    binding.networkError.visibility = View.VISIBLE
                binding.statusProgress.visibility = View.GONE
            })


    }

    private fun hideErrors() {
        binding.incorrectDataStatus.visibility = View.GONE
        binding.noInternet.visibility = View.GONE
        binding.networkError.visibility = View.GONE
    }
}