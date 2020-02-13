package com.monzo.room_playground

import androidx.room.*
import io.reactivex.Observable

@Dao
interface PetStorage {

    @Transaction
    @Query("SELECT * FROM DbOwner")
    fun ownersAndPetsWithToys(): Observable<List<OwnerWithPetsAndToys>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwner(owners: DbOwner)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPets(owners: List<DbPet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToys(owners: List<DbToy>)

    @Transaction
    fun insertDefault(owner: DbOwner, pets: List<DbPet>, toys: List<DbToy>) {
        insertOwner(owner)
        insertPets(pets)
        insertToys(toys)
    }
}