package com.app.hoichoiclone.screens.postcomments.reply.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.CommentItemBinding
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import com.app.hoichoiclone.utility.customviews.timeago.TimeAgo

class ReplyAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<ReplyAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserComments>() {

        override fun areItemsTheSame(
            oldItem: UserComments,
            newItem: UserComments
        ): Boolean {
            return oldItem.comment_Id == newItem.comment_Id
        }

        override fun areContentsTheSame(
            oldItem: UserComments,
            newItem: UserComments
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onCommentItemSelected(position: Int, item: UserComments, clickedFlag: Int)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: CommentItemBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<CommentItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.comment_item, parent, false
        )
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<UserComments>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemDataBindingUtil.navigationItem = item
        //  holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)
        holder.itemDataBindingUtil.postTime.text = TimeAgo.getTimeAgo(item.commentTimestamp)
    }
}
