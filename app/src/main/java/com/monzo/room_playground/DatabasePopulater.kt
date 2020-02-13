package com.monzo.room_playground

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DatabasePopulater {
    private var ownerId = 0L
    private var petId = 100L
    private var toyId = 1000L

    @SuppressLint("CheckResult")
    fun populateDb(petStorage: PetStorage) {
        Observable.fromCallable {
            for (i in 1..10) {
                val owner = DbOwner(ownerId = ownerId++, name = "Emma")
                val pet1 = DbPet(
                    petId = petId++,
                    petOwnerId = owner.ownerId,
                    name = "Tina",
                    cuteness = 10
                )
                val pet2 = DbPet(
                    petId = petId++,
                    petOwnerId = owner.ownerId,
                    name = "Taco",
                    cuteness = 10
                )
                val toy1 = DbToy(toyId = toyId++, petToyId = pet1.petId, name = "Hat")
                val toy2 = DbToy(toyId = toyId++, petToyId = pet2.petId, name = "Ball")

                petStorage.insertDefault(
                    owner = owner,
                    pets = listOf(pet1, pet2),
                    toys = listOf(toy1, toy2)
                )
            }
        }
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }
}