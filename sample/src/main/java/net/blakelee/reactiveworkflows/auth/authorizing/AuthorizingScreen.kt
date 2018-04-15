package net.blakelee.reactiveworkflows.auth.authorizing

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class AuthorizingScreen(
        val title: Observable<String>
) : WorkflowScreen<String, Unit>(KEY, title, Unit) {

    companion object { val KEY = Key(this) }
}