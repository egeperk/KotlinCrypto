package com.egeperk.kotlincrypto.service

import com.egeperk.kotlincrypto.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE

    //dd8f2f6813f2d37f2cf6fe5e318e7ee170da985c
    //https://api.nomics.com/v1/
    // prices?key=dd8f2f6813f2d37f2cf6fe5e318e7ee170da985c

    @GET("prices?key=dd8f2f6813f2d37f2cf6fe5e318e7ee170da985c")
    suspend fun getData () : Response<List<CryptoModel>>

    //fun getData() : Call<List<CryptoModel>>

}