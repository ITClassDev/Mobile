package ru.slavapmk.shtp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest


class LoginActivity : AppCompatActivity() {
    private lateinit var loginLayout: LinearLayout
    private lateinit var loginInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var incorrectDataStatus: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginLayout = findViewById(R.id.loginLayout)
        loginInput = findViewById(R.id.loginInput)
        passwordInput = findViewById(R.id.passwordInput)
        incorrectDataStatus = findViewById(R.id.incorrectDataStatus)
        progressBar = findViewById(R.id.progressBar)

        loginInput.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            incorrectDataStatus.visibility = View.GONE
        }
        passwordInput.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            incorrectDataStatus.visibility = View.GONE
        }
    }

    fun login(view: View?) {
        progressBar.visibility = View.VISIBLE
        loginLayout.visibility = View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authLogin =
                    Values.api.login(
                        AuthLoginRequest(
                            loginInput.text.toString(),
                            passwordInput.text.toString()
                        )
                    )
                Values.token = "Bearer ${authLogin.accessToken}"
                Values.user = Values.api.getMe(Values.token).user
                runOnUiThread {
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    this@LoginActivity.startActivity(myIntent)
                }
            } catch (e: HttpException) {
                if (e.code() == 422)
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        loginLayout.visibility = View.VISIBLE
                        incorrectDataStatus.visibility = View.VISIBLE
                    }
            }
        }
    }
}