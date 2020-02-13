package com.monzo.room_playground

import androidx.room.*

@Entity
data class DbOwner(@PrimaryKey val ownerId: Long, val name: String)

@Entity
data class DbPet(
    @PrimaryKey val petId: Long,
    val petOwnerId: Long,
    val name: String,
    val cuteness: Int
)

@Entity
data class DbToy(
    @PrimaryKey val toyId: Long,
    val petToyId: Long,
    val name: String
)

data class OwnerWithPetsAndToys(
    @Embedded val owner: DbOwner,
    @Relation(parentColumn = "ownerId", entityColumn = "petOwnerId", entity = DbPet::class)
    val petsAndToys: List<PetsWithToys>
)

data class PetsWithToys(
    @Embedded val pet: DbPet,
    @Relation(parentColumn = "petId", entityColumn = "petToyId")
    val toys: List<DbToy>
)