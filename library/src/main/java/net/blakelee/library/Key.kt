package net.blakelee.library

data class Key(val value: String) {
    constructor(name: Any) : this(name.toString())
}