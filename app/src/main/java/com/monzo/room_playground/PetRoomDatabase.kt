package com.monzo.room_playground

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [Owner::class, Pet::class, Toy::class], version = 1, exportSchema = false)
abstract class PetRoomDatabase : RoomDatabase() {
    abstract fun dao(): PetStorage

    companion object {
        @Volatile
        private var INSTANCE: PetRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): PetRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PetRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PetRoomDatabase::class.java, "word_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}