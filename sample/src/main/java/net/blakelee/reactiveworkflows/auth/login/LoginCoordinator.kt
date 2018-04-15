package net.blakelee.reactiveworkflows.auth.login

import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.login.view.*

class LoginCoordinator(private val screen: LoginScreen) : Coordinator() {
    private var disposable: Disposable = CompositeDisposable()

    override fun attach(view: View) {
        val error = view.error
        val email = view.email
        val password = view.password
        val login = view.login

        login.setOnClickListener {
            val event = LoginScreen.SubmitLogin(email.text.toString(),
                    password.text.toString())

            screen.eventHandler.onLogin(event)
        }

        disposable = screen.screenData.subscribe { screenData ->
            error.text = screenData
        }
    }

    override fun detach(view: View) {
        disposable.dispose()
    }
}