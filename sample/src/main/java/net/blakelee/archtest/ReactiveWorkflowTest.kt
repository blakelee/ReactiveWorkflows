package net.blakelee.archtest

import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.blakelee.library.AbstractViewFactory
import net.blakelee.library.WorkflowScreen
import kotlin.reflect.jvm.jvmName

//class LoginWorkflow : Workflow<Unit, String>,
//        LoginScreen.Events {
//
//    private val currentScreen = BehaviorSubject.create<String>()
//
//    private val loginMessage = BehaviorSubject.create<String>()
//
//    override fun screen(): Observable<WorkflowScreen<*, *>> =
//            currentScreen.map {
//                when(it) {
//                    LoginScreen.KEY -> LoginScreen(loginMessage, this)
//                    else -> throw IllegalArgumentException("Unknown key $it")
//                }
//            }
//
//    override fun abort() {}
//
//    override fun onLogin(event: LoginScreen.SubmitLogin) {
//
//    }
//}

class GameRunner {
    sealed class Command {
        data class NewGame(val xPlayer: String, val oPlayer: String) : Command()
        data class RestoreGame(val id: String) : Command()
        data class TakeSquare(val row: Int, val col: Int) : Command()
        object End : Command()
    }

//    fun asTransformer() {
//        ObservableTransformer<Command, GameState>
//    }
}

class LoginScreen(
        errorMessage: Observable<String>,
        errorHandler: Events
) : WorkflowScreen<String, LoginScreen.Events>(KEY, errorMessage, errorHandler) {

    companion object {
        val KEY = LoginScreen::class.jvmName
    }

    interface Events {
        fun onLogin(event: SubmitLogin)
    }

    data class SubmitLogin(val email: String, val password: String)
}

class AuthViewFactory : AbstractViewFactory(listOf(
        bindLayout(LoginScreen.KEY, R.layout.main_layout) { screen ->
            LoginCoordinator(screen as LoginScreen)
        }
)
)

class LoginCoordinator(private val screen: LoginScreen) : Coordinator() {
    private var disposable: Disposable = CompositeDisposable()

    override fun detach(view: View) {
        super.detach(view)
        disposable.dispose()
    }
}
