package net.blakelee.archtest.test

import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.archtest.Workflow
import net.blakelee.archtest.WorkflowScreen
import net.blakelee.archtest.test.first.FirstScreen
import net.blakelee.archtest.test.second.SecondScreen

class TestWorkflow : Workflow<Unit, Unit>,
    FirstScreen.Events, SecondScreen.Events {

    private lateinit var testViewFactory: TestViewFactory
    private lateinit var container: ViewGroup

    private val currentScreen = BehaviorSubject.create<String>()

    private val firstMessage = BehaviorSubject.create<String>()
    private val secondMessage = BehaviorSubject.create<String>()


    fun create(container: ViewGroup) {
        this.container = container
        testViewFactory = TestViewFactory()
        testViewFactory.create(this, container)
        currentScreen.onNext(FirstScreen.KEY)
        currentScreen.onNext(SecondScreen.KEY)
    }

    override fun screen(): Observable<WorkflowScreen<*, *>> =
            currentScreen.map {
                when(it) {
                    FirstScreen.KEY -> FirstScreen(firstMessage, this)
                    SecondScreen.KEY -> SecondScreen(secondMessage, this)
                    else -> throw IllegalArgumentException("Unknown key $it")
                }
            }

    override fun firstEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun secondTest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun abort() {}
}