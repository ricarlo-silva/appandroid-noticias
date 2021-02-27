package com.noticias_now.model.mapper

import com.noticias_now.model.TypeModel
import com.noticias_now.model.mapper.base.DomainMapper
import com.noticias_now.model.response.TypeResponse

class TypeMapperImpl : DomainMapper<TypeResponse, TypeModel> {

    override fun toDomain(from: TypeResponse): TypeModel {
        return TypeModel(
            id = from.id,
            name = from.name
        )
    }

    override fun toDomain(from: List<TypeResponse>): List<TypeModel> {
        return from.map { toDomain(it) }
    }
}
