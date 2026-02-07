package ru.sicampus.bootcamp2026.data.source.dataSource

import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthLocalDataSource {
    val token: String? get() = _cacheToken
    private var _cacheToken: String? = null

    @OptIn(ExperimentalEncodingApi::class)
    fun setToken(login: String, password: String){
        val decodePhrase = "$login:$password"
        _cacheToken = "Basic ${Base64.encode(decodePhrase.toByteArray())}"
    }

    fun clearToken(){ _cacheToken = null }
}