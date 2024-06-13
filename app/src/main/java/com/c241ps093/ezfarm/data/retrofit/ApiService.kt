package com.c241ps093.ezfarm.data.retrofit

import com.c241ps093.ezfarm.data.entity.IdentificationResponse
import com.c241ps093.ezfarm.data.entity.TrackingDataResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @Multipart
    @POST("predict/")
    fun postData(
        @Part file : MultipartBody.Part
    ) : Call<IdentificationResponse>

    @GET("tracking/")
    fun getTrackingData() : Call<TrackingDataResponse>
}