package com.app.hoichoiclone.utility.customviews.tabs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.CustomTabLayoutBinding

class TabAdapter(private val context: Context, private val interaction: TabInteraction) :
    RecyclerView.Adapter<TabAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NavigationOption>() {

        override fun areItemsTheSame(
            oldItem: NavigationOption,
            newItem: NavigationOption
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NavigationOption,
            newItem: NavigationOption
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface TabInteraction {
        fun onItemSelected(position: Int, item: NavigationOption)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: CustomTabLayoutBinding,
        val interaction: TabInteraction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {

        val itemDatabinding = DataBindingUtil.inflate<CustomTabLayoutBinding>(LayoutInflater.from(parent.context), R.layout.custom_tab_layout, parent, false)

        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<NavigationOption>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]
        /**
         * Assigning the variables in to data binding variables for showing the data
         * */
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        if (currentItemSelected == position) {
            holder.itemDataBindingUtil.selectedView.visibility = View.VISIBLE
        } else {
            holder.itemDataBindingUtil.selectedView.visibility = View.INVISIBLE
        }
    }
}
