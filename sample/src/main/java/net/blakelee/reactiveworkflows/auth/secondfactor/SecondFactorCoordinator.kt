package net.blakelee.reactiveworkflows.auth.secondfactor

import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.second_factor.view.*

class SecondFactorCoordinator(
        private val screen: SecondFactorScreen
) : Coordinator() {
    private lateinit var disposable: Disposable

    override fun attach(view: View) {
        val status = view.status
        val login = view.login
        val token = view.token

        login.setOnClickListener {
            val event = SecondFactorScreen.SecondFactor(token.text.toString())
            screen.eventHandler.onSecondFactor(event)
        }

        disposable = screen.screenData.subscribe {
            status.text = it
        }
    }

    override fun detach(view: View) {
        disposable.dispose()
    }
}