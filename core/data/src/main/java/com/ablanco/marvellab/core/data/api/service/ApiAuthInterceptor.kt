package com.ablanco.marvellab.core.data.api.service

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
class ApiAuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestUrl = chain.request().url().newBuilder()
        requestUrl.addQueryParameter("apikey", apiKey)

        val requestBuilder = chain.request()
            .newBuilder()
            .url(requestUrl.build())
            .addHeader("Referer", "marvellab.com")

        return chain.proceed(requestBuilder.build())
    }
}