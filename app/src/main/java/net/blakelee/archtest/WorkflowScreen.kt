package net.blakelee.archtest

import io.reactivex.Observable

abstract class WorkflowScreen<D, out E> protected constructor(
        val key: String,
        val screenData: Observable<D>,
        val eventHandler: E
) {
    var viewMode = ViewMode.Replace
    
    enum class ViewMode {
        Push,
        Replace
    }
}