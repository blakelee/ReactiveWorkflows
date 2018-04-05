package net.blakelee.archtest

import io.reactivex.Observable

interface Workflow<in I, out R> {
    fun screen(): Observable<WorkflowScreen<*,*>>
    fun abort()
}