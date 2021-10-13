package com.vinay.truecaller.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiInterface {

    @GET("/2018/01/22/life-as-an-android-engineer/")
    suspend fun getTruecallerPage(): String?

}