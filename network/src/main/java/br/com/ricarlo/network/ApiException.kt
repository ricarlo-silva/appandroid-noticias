package br.com.ricarlo.network

open class ApiException(
    open val statusCode: Int,
    override val message: String?
): RuntimeException(message)