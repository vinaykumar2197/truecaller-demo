package com.vinay.truecaller.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitBuilder {

    public const val BASE_URL = "https://truecaller.blog"

//    var gson = GsonBuilder()
//        .setLenient()
//        .create()

    fun getRetrofit() : Retrofit {
        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

    val apiInterface = getRetrofit().create(ApiInterface::class.java)


}