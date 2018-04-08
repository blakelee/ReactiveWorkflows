package net.blakelee.archtest

import kotlin.reflect.KClass

class FiniteStateMachine(
        initialState: Any,
        vararg events: Events
) {

    var currentState: Any = initialState

    private val entry = mutableMapOf<Any, () -> Unit>()
    private val transition = mutableMapOf<Any, MutableMap<Any, EventComposition>>()

    init {
        events.forEach { event ->
            when (event) {
                is Events.Entry -> entry[event.state] = event.action
                is Events.Transition -> {
                    if (!transition.containsKey(event.from)) {
                        transition[event.from] = mutableMapOf()
                    } else {
                        val map: MutableMap<Any, EventComposition> = transition[event.from]!!
                        map[event.to] = with(event) {
                            EventComposition(clazz, onlyIf, doAction)
                        }
                    }
                }
            }
        }

        entry[currentState]?.invoke()
    }

    private data class EventComposition(val clazz: KClass<*>, val onlyIf: () -> Boolean, val doAction: (KClass<*>) -> Unit)

    sealed class Events {
        class Entry(val state: Any, val action: () -> Unit) : Events()
        class Transition(val from: Any, val clazz: KClass<*>, val to: Any) : Events() {
            internal var onlyIf: () -> Boolean = { true }
            internal var doAction: (KClass<*>) -> Unit = {}

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

    fun next(event: Any) {

    }

    companion object {
        fun onEntry(state: Any, action: () -> Unit) = Events.Entry(state, action)
        fun transition(from: Any, clazz: KClass<*>, to: Any) = Events.Transition(from, clazz, to)
    }

}