package net.blakelee.reactiveworkflows.test

import io.reactivex.Observable

class TestRunner {
    sealed class Command {
        data class FirstInfo(val info: String) : Command()
        data class SecondInfo(val info: String) : Command()
        object End : Command()
    }

//    fun asTransformer(): Observable.Transformer<Command, TestRunner> {
//
//    }
}