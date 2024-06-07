package com.c241ps093.ezfarm.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackingDataResponse(

    @field:SerializedName("TrackingDataResponse")
    val trackingDataResponse: List<TrackingDataResponseItem?>? = null
) : Parcelable

@Parcelize
data class LangkahLangkahItem(

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("tipe")
    val tipe: String? = null,

    @field:SerializedName("status")
    var status: Boolean? = null
) : Parcelable

@Parcelize
data class TrackingDataResponseItem(

    @field:SerializedName("hari")
    val hari: Int? = null,

    @field:SerializedName("urlGambar")
    val urlGambar: String? = null,

    @field:SerializedName("langkahLangkah")
    val langkahLangkah: List<LangkahLangkahItem?>? = null,

    @field:SerializedName("judul")
    val judul: String? = null
) : Parcelable
