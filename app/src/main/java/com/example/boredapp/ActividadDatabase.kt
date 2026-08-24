package com.example.boredapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Actividad::class], version = 1, exportSchema = false)
abstract class ActividadDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao

    companion object {
        @Volatile
        private var INSTANCE: ActividadDatabase? = null

        fun getDatabase(context: Context): ActividadDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActividadDatabase::class.java,
                    "boredapp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}