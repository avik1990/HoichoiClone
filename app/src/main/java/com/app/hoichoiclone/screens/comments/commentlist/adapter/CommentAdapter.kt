package com.app.hoichoiclone.screens.comments.commentlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.CommentItemBinding
import com.app.hoichoiclone.screens.comments.commentlist.model.Comment

class CommentAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<CommentAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {

        override fun areItemsTheSame(
            oldItem: Comment,
            newItem: Comment
        ): Boolean {
            return oldItem.comment_Id == newItem.comment_Id
        }

        override fun areContentsTheSame(
            oldItem: Comment,
            newItem: Comment
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onCommentItemSelected(position: Int, item: Comment)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: CommentItemBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<CommentItemBinding>(LayoutInflater.from(parent.context), R.layout.comment_item, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Comment>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)
    }
}
