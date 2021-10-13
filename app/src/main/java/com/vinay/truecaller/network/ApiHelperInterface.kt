package com.vinay.truecaller.network

import android.net.Uri

class ApiHelperInterface (private val apiInterface: ApiInterface)  : ApiHelper{


    override suspend fun getTruecallerPage(): String? {
        return  apiInterface.getTruecallerPage()
    }


}