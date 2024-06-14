package com.c241ps093.ezfarm.data.retrofit

import android.util.Log
import com.c241ps093.ezfarm.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getInstance() : ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            //Penambahan token
//            val authInterceptor = Interceptor { chain ->
//                val req = chain.request()
//                val requestHeaders = req.newBuilder()
//                    .addHeader("Authorization", "Bearer ${BuildConfig.REMOTE_API_KEY}")
//                    .build()
//
//                Log.e("HEADER", "${requestHeaders.method} \nHEADERS: ${requestHeaders.headers}")
//
//                chain.proceed(requestHeaders)
//            }

            val client = OkHttpClient
                .Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
//                .addNetworkInterceptor(authInterceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}