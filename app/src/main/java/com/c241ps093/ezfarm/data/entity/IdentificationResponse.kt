package com.c241ps093.ezfarm.data.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class IdentificationResponse(

	@field:SerializedName("result_with_penanganan")
	val resultWithPenanganan: ResultWithPenanganan,

	@field:SerializedName("class")
	val jsonMemberClass: String
) : Parcelable

@Parcelize
data class ResultWithPenanganan(

	@field:SerializedName("penyakit")
	val penyakit: String,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String,

	@field:SerializedName("penanganan")
	val penanganan: List<String>,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("pencegahan")
	val pencegahan: List<String>,

	@field:SerializedName("gejala")
	val gejala: List<String>
) : Parcelable
