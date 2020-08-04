package com.alan.projetopadrao.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {

    private val URL = "https://5b840ba5db24a100142dcd8c.mockapi.io/api/"

    private fun getRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request = chain.request()
            chain.proceed(request)
        }
        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService : Webservice = getRetrofit().create(Webservice::class.java)
}