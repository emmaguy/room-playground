package com.monzo.room_playground

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = PetRoomDatabase.getDatabase(this)!!
        val petStorage = database.dao()
        database.queryExecutor.execute {
            val list = petStorage.ownersAndPetsWithToys()

            runOnUiThread {
                Toast.makeText(this, list.joinToString(separator = ", "), Toast.LENGTH_SHORT).show()
            }
        }
    }
}