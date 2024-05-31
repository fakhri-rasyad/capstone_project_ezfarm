package com.c241ps093.ezfarm.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity
@Parcelize
data class Plant (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "plantedDate")
    var plantedDate: Date,

    @ColumnInfo(name = "harvestDate")
    var harvestDate: Date,

    @ColumnInfo(name = "growthStatus")
    var growthStatus: String,

    @ColumnInfo(name = "plantType")
    var plantType: String
) : Parcelable