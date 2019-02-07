package net.blakelee.library

import com.squareup.coordinators.Coordinator
import io.reactivex.Maybe
import io.reactivex.Observable
import net.blakelee.library.WorkflowBinding.Companion.ignoreStartArg

class CompositeWorkflow (vararg bindings: WorkflowBinding) {

}

class WorkflowBinding(val clazz: Any, val provider: () -> Workflow<*, *>) {

    companion object {
        fun ignoreStartArg(workflow: Workflow<*, *>): Workflow<*, *> {
            return workflow
        }
    }
}

class Test {

    var result: Maybe<Any> = Maybe.empty()
    lateinit var composite: Observable<Workflow<*, *>>

    init {
        val x = CompositeWorkflow(
                WorkflowBinding(TestWorkflow::class.java,
                { ignoreStartArg(TestWorkflowProvider.get()) })
        )
    }
}

class TestWorkflowProvider {
    companion object {
        fun get(): Workflow<*, *> {
            return TestWorkflow()
        }
    }
}

class TestWorkflow : Workflow<Unit, Unit> {
    override val viewFactory: AbstractViewFactory = TestViewFactory()
    override fun back() {}
    override fun start(input: Unit) {}
    override fun abort() {}
    override fun screen(): Observable<WorkflowScreen<*, *>> { return Observable.never() }
    override fun result(): Maybe<out Unit> { return Maybe.never() }
}

class TestViewFactory : AbstractViewFactory(listOf(
        bindLayout(Key(""), 0) {
            TestCoordinator()
        }
))

class TestCoordinator : Coordinator()
class TestWorkflowScreen : WorkflowScreen<Unit, Unit>(Key(""), Observable.never(), Unit)