package com.monzo.room_playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedListAdapter
import androidx.paging.toObservable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_row.view.*

class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    private val databasePopulater = DatabasePopulater()
    private val petStorage by lazy { PetDatabase.getDatabase(this).petStorage() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databasePopulater.populateDb(petStorage)

        val adapter = RowAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        disposables += petStorage.ownersAndPetsWithToys()
            .toObservable(pageSize = 25)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.submitList(list)
            }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    class RowAdapter : PagedListAdapter<OwnerWithPetsAndToys, RowViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            return RowViewHolder(view)
        }

        override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
            holder.bind(getItem(position)!!)
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OwnerWithPetsAndToys>() {
                override fun areItemsTheSame(
                    old: OwnerWithPetsAndToys,
                    new: OwnerWithPetsAndToys
                ): Boolean {
                    return old.owner.ownerId == new.owner.ownerId
                }

                override fun areContentsTheSame(
                    old: OwnerWithPetsAndToys,
                    new: OwnerWithPetsAndToys
                ): Boolean {
                    return old == new
                }
            }
        }
    }

    class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: OwnerWithPetsAndToys) {
            itemView.title.text = "${row.owner.name}${row.owner.ownerId}"
            itemView.subtitle.text = row.petsAndToys.joinToString(", ") { it.pet.name }
        }
    }
}