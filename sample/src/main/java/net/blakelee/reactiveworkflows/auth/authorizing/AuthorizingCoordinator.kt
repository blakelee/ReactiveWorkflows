package net.blakelee.reactiveworkflows.auth.authorizing

import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.authorizing.view.*

class AuthorizingCoordinator(
        private val screen: AuthorizingScreen
) : Coordinator() {

    private lateinit var disposable: Disposable

    override fun attach(view: View) {
        val status = view.status

        disposable = screen.screenData.subscribe {
            status.text = it
        }
    }

    override fun detach(view: View) {
        disposable.dispose()
    }
}