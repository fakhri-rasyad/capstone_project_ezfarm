package com.c241ps093.ezfarm.data.entity

import com.google.gson.annotations.SerializedName

data class TrackingDataResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class LangkahItem(

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class Data(

	@field:SerializedName("day")
	val day: List<DayItem?>? = null
)

data class DayItem(

	@field:SerializedName("langkah")
	val langkah: List<LangkahItem?>? = null,

	@field:SerializedName("Aktivitas")
	val aktivitas: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("day")
	val day: String? = null
)
