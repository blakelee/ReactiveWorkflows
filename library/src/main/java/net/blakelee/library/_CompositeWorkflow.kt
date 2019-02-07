package net.blakelee.library

import io.reactivex.Maybe
import kotlin.reflect.KClass

//open class CompositeWorkflow(
//        vararg workflowBinding: Workflow<*, *>
//) {
//
//    lateinit var composite: Any
//    lateinit var result: Maybe<Any>
//
//    companion object {
//        fun <W : Any>WorkflowBinding(
//                workflow: KClass<W>, provider: () -> WorkflowProvider,
//                action: (Any, Any) -> Workflow<Any, Any>
//        ): Workflow<*,*> {
//            return TestWorkflow()
//        }
//
//        fun ignoreStartArg(workflow: Workflow<*, *>): () -> Workflow<*, *> {
//            return { workflow }
//        }
//
//    }
//}

//abstract class WorkflowProvider {
//    abstract fun get(): Workflow<*,*>
//}

//class testWorkflowProvider : WorkflowProvider() {
//    override fun get(): Workflow<*, *> {
//        return TestWorkflow()
//    }
//}

//class testbinding : CompositeWorkflow(
//    WorkflowBinding(TestWorkflow::class,
//            () -> ignoreStartArg(testWorkflowProvider.get()),
//            (composite, result) -> composite.startWorkflow(
//                forArg(TestWorkflow::class, (Unit) UNIT)))
//)