package de.jensomato.sample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.jensomato.sample.R
import de.jensomato.sample.presentation.LoginContract
import de.jensomato.sample.presentation.PostsContract.View.Companion.USER_ID
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), KoinComponent, LoginContract.View {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private val loginPresenter: LoginContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()

        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            val userId = inputUserId.text.toString().toLongOrNull()
            if (userId != null) {
                loginPresenter.login(userId)
            } else {
                displayError()
            }

        }
    }

    override fun displayError() {
        Toast.makeText(this, "display error", Toast.LENGTH_LONG).show()
    }

    override fun navigateToPosts(userId: Long) {
        val intent = Intent(this, PostsActivity::class.java).apply {
            putExtra(USER_ID, userId)
        }
        startActivity(intent)
    }
}
