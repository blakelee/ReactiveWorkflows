package net.blakelee.archtest

import android.content.Context
import android.view.View
import com.squareup.coordinators.Coordinator
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.jvm.jvmName

interface Workflow<in I, out R> {
    fun screen(): Observable<WorkflowScreen<*,*>>

    fun abort() //Destroy this workflow
}


class LoginWorkflow : Workflow<Unit, String>,
    LoginScreen.Events {

    private val currentScreen = BehaviorSubject.create<String>()

    private val loginMessage = BehaviorSubject.create<String>()

    override fun screen(): Observable<WorkflowScreen<*, *>> =
        currentScreen.map { it ->
            when(it) {
                LoginScreen.KEY -> LoginScreen(loginMessage, this)
                else -> throw IllegalArgumentException("Unknown key " + it)
            }
        }

    override fun onLogin(event: LoginScreen.SubmitLogin) {

    }

    override fun abort() {

    }
}

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

abstract class WorkflowScreen<D, out E> protected constructor(
        val key: String,
        val screenData: Observable<D>,
        val eventHandler: E
)

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

//class AuthViewFactory : AbstractViewFactory(
//        listOf(
//                bindLayout(LoginScreen.KEY, R.layout.main_layout) { screen ->
//                    LoginCoordinator(screen as LoginScreen)
//                }
//        )
//)

open class AbstractViewFactory(val screens: List<Coordinator>) {

    private lateinit var context: Context

    companion object {

//        inline fun <T: Coordinator, R, S> bindLayout(
//                key: String,
//                @LayoutRes id: Int,
//                screen: (Wogit rkflowScreen<*,*>) -> Coordinator
//                ): Coordinator {
//            return screen.invoke(WorkflowScreen<R,S>(key, ))
//        }
    }
}

class LoginCoordinator(private val screen: LoginScreen) : Coordinator() {
    private var disposable: Disposable = CompositeDisposable()

    override fun detach(view: View) {
        super.detach(view)
        disposable.dispose()
    }
}
