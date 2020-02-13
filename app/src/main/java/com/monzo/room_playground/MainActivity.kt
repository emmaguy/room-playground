package com.monzo.room_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val petStorage by lazy { PetDatabase.getDatabase(this).petStorage() }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.fromCallable { petStorage.insertDefault() }
            .subscribeOn(Schedulers.io())
            .blockingFirst()

        disposables += petStorage.ownersAndPetsWithToys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                textView.text = list.joinToString(separator = "/n")
            }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}