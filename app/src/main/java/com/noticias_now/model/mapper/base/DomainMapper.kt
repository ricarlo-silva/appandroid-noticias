package com.noticias_now.model.mapper.base

interface DomainMapper<in T, out Model> {
    fun toDomain(from: T) : Model
    fun toDomain(from: List<T>) : List<Model>
}