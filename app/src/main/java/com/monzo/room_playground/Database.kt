package com.monzo.room_playground

import androidx.room.*

@Entity
data class Owner(@PrimaryKey val ownerId: Long, val name: String)

@Entity
data class Pet(
    @PrimaryKey val petId: Long,
    val petOwnerId: Long,
    val name: String,
    val cuteness: Int
)

@Entity
data class Toy(
    @PrimaryKey val toyId: Long,
    val petToyId: Long,
    val name: String
)

@Dao
interface PetStorage {

    @Transaction
    @Query("SELECT * FROM Owner")
    fun ownersAndPetsWithToys(): List<OwnerWithPetsAndToys>
}

data class OwnerWithPetsAndToys(
    @Embedded val owner: Owner,
    @Relation(parentColumn = "ownerId", entityColumn = "petOwnerId")
    val petsAndToys: List<PetsWithToys>
)

data class PetsWithToys(
    @Embedded val pet: Pet,
    @Relation(parentColumn = "petId", entityColumn = "petToyId")
    val toys: List<Toy>
)