package net.blakelee.archtest

import io.reactivex.Observable
import net.blakelee.archtest.test.TestWorkflow
import kotlin.reflect.KClass

open class CompositeWorkflow(vararg workflowBinding: WorkflowBinding<Any>){

    val composite: Observable<Any> = Observable.empty()
    val result: Observable<Any> = Observable.empty()

    data class WorkflowBinding<W : Any>(
            val clazz: KClass<W>,
            val provider: () -> Workflow<*,*>,
            val next: (Observable<*>, Observable<*>) -> String
    )
}

class mainworkflow : CompositeWorkflow(
        WorkflowBinding(
                TestWorkflow::class,
                { TestWorkflow() },
                
        )
)