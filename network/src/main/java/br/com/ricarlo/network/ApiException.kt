package br.com.ricarlo.network

import java.lang.RuntimeException

open class ApiException(
    open val statusCode: Int,
    override val message: String?
): RuntimeException(message)