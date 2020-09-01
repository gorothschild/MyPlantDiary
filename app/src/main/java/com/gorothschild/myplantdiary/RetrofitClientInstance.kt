package com.gorothschild.myplantdiary

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit:Retrofit? = null // this 'satisfies the singleton design pattern
    private val BASE_URL = "https://www.plantplaces.com" // for integration testing was http without s

    var retrofitInstance: Retrofit? = null
        get() {
            if (retrofit ==null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}