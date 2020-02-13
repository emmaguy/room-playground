package com.monzo.room_playground

import androidx.room.*
import io.reactivex.Observable

@Dao
interface PetStorage {

    @Transaction
    @Query("SELECT * FROM DbOwner")
    fun ownersAndPetsWithToys(): Observable<List<OwnerWithPetsAndToys>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwners(owners: List<DbOwner>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPets(owners: List<DbPet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToys(owners: List<DbToy>)

    @Transaction
    fun insertDefault() {
        insertOwners(
            listOf(
                DbOwner(0, "Emma"),
                DbOwner(1, "Fred"),
                DbOwner(2, "Elijah"),
                DbOwner(3, "Sam")
            )
        )
        insertPets(
            listOf(
                DbPet(100, 0, "Tina", 10),
                DbPet(101, 0, "Taco", 10)
            )
        )
        insertToys(
            listOf(
                DbToy(1001, 100, "Tina's hat"),
                DbToy(1002, 101, "Taco's ball")
            )
        )
    }
}