package net.blakelee.archtest

import android.util.Log
import kotlin.reflect.KClass

class FiniteStateMachine(
        initialState: Any,
        vararg events: Events
) {

    var currentState: Any = initialState

    private val entry = mutableMapOf<Any, () -> Unit>()

    /** This needs to be map<any, map<any, map<any, event>>> so that we can pass in multiple
     *  different types for the same transition
     */
    private val transition = mutableMapOf<Any, MutableMap<Any, EventComposition>>()

    init {
        events.forEach { event ->
            when (event) {
                is Events.Entry -> entry[event.state] = event.action
                is Events.Transition -> with(event) {

                    if (!transition.containsKey(from)) {
                        transition[from] = mutableMapOf()
                    }

                    transition[from]?.put(clazz, EventComposition(to, onlyIf, doAction))
                }
            }
        }

        entry[currentState]?.invoke()
    }

    private data class EventComposition(val to: Any, val onlyIf: () -> Boolean, val doAction: (Any) -> Unit)

    sealed class Events {
        class Entry(val state: Any, val action: () -> Unit) : Events()
        class Transition(val from: Any, val clazz: KClass<*>, val to: Any) : Events() {
            internal var onlyIf: () -> Boolean = { true }
            internal var doAction: (Any) -> Unit = {}

            fun onlyIf(action: () -> Boolean): Transition {
                this.onlyIf = action
                return this
            }

            fun doAction(action: (Any) -> Unit): Transition {
                this.doAction = action
                return this
            }

        }
    }

    fun <T : Any>event(event: T): Boolean {
        transition[currentState]?.let { from ->
            from[event::class]?.let { to ->
                if (to.onlyIf.invoke()) {
                    currentState = to.to
                    entry[currentState]?.invoke()
                    to.doAction.invoke(event)
                    return true
                }
            }
        }

        return false
    }

    companion object {
        fun onEntry(state: Any, action: () -> Unit) = Events.Entry(state, action)
        fun transition(from: Any, clazz: KClass<*>, to: Any) = Events.Transition(from, clazz, to)
    }
}