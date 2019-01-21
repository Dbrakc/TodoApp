package com.davidbragadeveloper.todoapp.data.model.mapper

interface Mapper< in M, out T> {

    fun transform (input: M): T
    fun transformList (input: List<M>): List<T>
}