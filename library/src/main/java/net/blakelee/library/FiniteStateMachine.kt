package net.blakelee.library

import kotlin.reflect.KClass

class FiniteStateMachine(
        initialState: Any,
        vararg events: Events
) {

    var currentState: Any = initialState

    private val entry = mutableMapOf<Any, () -> Unit>()

    private val transition = mutableMapOf<Any, MutableMap<Any, EventComposition<Any>>>()

    init {
        events.forEach { event ->
            when (event) {
                is Events.Entry -> entry[event.state] = event.action
                is Events.Transition<*> -> with(event as Events.Transition<Any>) {
                    if (!transition.containsKey(from)) {
                        transition[from] = mutableMapOf()
                    }

                    transition[from]?.let { type ->
                        type[clazz] = EventComposition(to, onlyIf, doAction)
                    }
                }
            }
        }

        entry[currentState]?.invoke()
    }

    private data class EventComposition<in T>(val to: Any, val onlyIf: (T) -> Boolean, val doAction: (T) -> Unit)

    sealed class Events {
        class Entry(val state: Any, val action: () -> Unit) : Events()
        class Transition<T : Any>(val from: Any, val clazz: KClass<T>, val to: Any) : Events() {
            internal var onlyIf: (T) -> Boolean = { true }
            internal var doAction: (T) -> Unit = {}

            fun onlyIf(action: (T) -> Boolean): Transition<T> {
                this.onlyIf = action
                return this
            }

            fun doAction(action: (T) -> Unit): Transition<T> {
                this.doAction = action
                return this
            }
        }
    }

    fun <T : Any>event(event: T): Boolean {
        transition[currentState]?.let { from ->
            from[event::class]?.let { to ->
                if (to.onlyIf.invoke(event)) {
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
        fun <T : Any>transition(from: Any, clazz: KClass<T>, to: Any) = Events.Transition(from, clazz, to)
    }
}
