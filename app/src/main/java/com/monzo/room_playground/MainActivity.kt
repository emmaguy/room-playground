package com.monzo.room_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = PetRoomDatabase.getDatabase(this)
        val petStorage = database!!.dao()
    }
}