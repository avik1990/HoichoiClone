package com.app.hoichoiclone.screens.home.modules.all.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.RowMovielistHeaderBinding
import com.app.hoichoiclone.screens.home.modules.all.model.Detail
import com.app.hoichoiclone.screens.home.modules.all.model.Result

class MovieAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<MovieAdapter.NavigationOptionViewHolder>(), ChildAdapter.Interaction {

    var currentItemSelected: Int = 0
    private val viewPool = RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onItemSelectedHorizontal(position: Int, item: Detail)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowMovielistHeaderBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowMovielistHeaderBinding>(LayoutInflater.from(parent.context), R.layout.row_movielist_header, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Result>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        holder.itemDataBindingUtil.listSubCatItem.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.listSubCatItem.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ChildAdapter(item.details, this@MovieAdapter)
            holder.itemDataBindingUtil.listSubCatItem.setRecycledViewPool(viewPool)
        }
    }

    override fun onItemSelected(position: Int, item: Detail) {
        interaction.onItemSelectedHorizontal(position, item)
    }
}
