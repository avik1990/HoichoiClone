package com.app.hoichoiclone.screens.home.modules.all.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.RowMovielistHorizontalBinding
import com.app.hoichoiclone.screens.home.modules.all.model.Detail

class ChildAdapter(private val children: List<Detail>) :
    RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildAdapter.ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowMovielistHorizontalBinding>(LayoutInflater.from(parent.context), R.layout.row_movielist_horizontal, parent, false)
        return ChildAdapter.ViewHolder(
            itemDatabinding
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]

        holder.itemDataBindingUtil.navigationItem = child

        val url = "https://www.themoviedb.org/t/p/w500" + child.poster_path

        holder.itemDataBindingUtil.movieBanner.load(url) {
            scale(Scale.FILL)
        }
        // holder.itemDataBindingUtil.movieTitle.text = child.title
    }

    class ViewHolder(
        val itemDataBindingUtil: RowMovielistHorizontalBinding,
        // val interaction: SubCategoryMovieAdapter.Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)
}
