package com.noticiasnow.model.mapper

import com.noticiasnow.model.TypeModel
import com.noticiasnow.model.mapper.base.DomainMapper
import com.noticiasnow.model.response.TypeResponse

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
