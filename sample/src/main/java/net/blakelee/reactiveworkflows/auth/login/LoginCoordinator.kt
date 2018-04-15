package net.blakelee.reactiveworkflows.auth.login

import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginCoordinator(private val screen: LoginScreen) : Coordinator() {
    private var disposable: Disposable = CompositeDisposable()

    override fun detach(view: View) {
        super.detach(view)
        disposable.dispose()
    }
}