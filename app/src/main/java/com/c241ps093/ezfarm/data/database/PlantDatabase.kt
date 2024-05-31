package com.c241ps093.ezfarm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Plant::class], version = 1)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao() : PlantDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): PlantDatabase {
            if (INSTANCE == null) {
                synchronized(PlantDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PlantDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as PlantDatabase
        }
    }
}