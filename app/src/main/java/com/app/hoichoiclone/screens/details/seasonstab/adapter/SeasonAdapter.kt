package com.app.hoichoiclone.screens.details.seasonstab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.RowSeasonItemBinding
import com.app.hoichoiclone.screens.details.seasonstab.model.Video

class SeasonAdapter(private val interaction: Interaction) : RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {

    lateinit var context: Context
    val videoList: ArrayList<Video> by lazy {
        ArrayList()
    }

    class ViewHolder(
        val itemDataBindingUtil: RowSeasonItemBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    interface Interaction {
        fun onItemSelected(position: Int, item: Video)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowSeasonItemBinding>(LayoutInflater.from(parent.context), R.layout.row_season_item, parent, false)
        return SeasonAdapter.ViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = videoList[position]

        holder.itemDataBindingUtil.navigationItem = child
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position

        holder.itemDataBindingUtil.movieBanner.load(videoList[position].thumb) {
            scale(Scale.FILL)
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun setData(list: List<Video>) {
        videoList.clear()
        videoList.addAll(list)
        notifyDataSetChanged()
    }
}
