package com.solrgraphdemo.task


interface SearchTask<T, R> {
    fun runTask(argument: T): R
}
