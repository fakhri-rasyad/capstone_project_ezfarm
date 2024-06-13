package com.c241ps093.ezfarm.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PlantTodo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todoId")
    val todoId : Int = 0,
    @ColumnInfo(name = "plantId")
    val plantId : String,
    @ColumnInfo(name = "todoDay")
    val toDoDay : Int,
    @ColumnInfo(name = "desc")
    val desc : String,
    @ColumnInfo(name = "status")
    var status : Boolean
) : Parcelable