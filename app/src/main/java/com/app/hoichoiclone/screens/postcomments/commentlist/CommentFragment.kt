package com.app.hoichoiclone.screens.postcomments.commentlist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.FragmentCommentBinding
import com.app.hoichoiclone.screens.postcomments.commentlist.adapter.CommentAdapter
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import com.app.hoichoiclone.utility.bottomsheet.SuperBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : SuperBottomSheetFragment(), CommentAdapter.Interaction {

    companion object {
        fun newInstance() = CommentFragment()
    }

    private lateinit var viewModel: CommentViewModel
    private lateinit var _binding: FragmentCommentBinding
    var listUsers: MutableList<Users> = ArrayList()
    var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CommentViewModel::class.java]

        // insert dummy data
        viewModel.insertUsers(pouplateUsers())
        //

        // fetching user data and show in dropdown
        viewModel.allUsers.observe(requireActivity()) { users ->
            users.let {
                val spAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item, it
                )
                _binding.spnUsers.adapter = spAdapter

                _binding.spnUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(requireContext(), it[position].userId.toString(), Toast.LENGTH_SHORT).show()
                        userId = it[position].userId!!
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                    }
                }
            }
        }
        //

        // posting the comment
        _binding.commentView.ivPost.setOnClickListener {
            if (_binding.commentView.etComment.text.toString().trim().isNotEmpty()) {
                var comment = Comment(
                    _binding.commentView.etComment.text.toString().trim(),
                    userId,
                    1,
                    System.currentTimeMillis()
                )
                viewModel.insert(comment)
                _binding.commentView.etComment.text.clear()
            }
        }
        //

        // comment adapter
        val adapter = CommentAdapter(requireContext(), this)
        _binding.recylcerComments.adapter = adapter
        viewModel.allComment.observe(requireActivity()) { comments ->
            comments.let { adapter.submitList(it) }
        }
        //
    }

    private fun pouplateUsers(): List<Users> {
        val user1 = Users("Avik Sutar", "https://robohash.org/hicveldicta.png", System.currentTimeMillis())
        val user2 = Users("Arnab Sutar", "https://robohash.org/doloremquesintcorrupti.png", System.currentTimeMillis())
        val user3 = Users("Sumon Roy", "https://robohash.org/consequunturautconsequatur.png", System.currentTimeMillis())
        val user4 = Users("Shriparna Roy", "https://robohash.org/facilisdignissimosdolore.png", System.currentTimeMillis())
        val user5 = Users("Srijan Roy", "https://robohash.org/adverovelit.png", System.currentTimeMillis())
        val user6 = Users("Souvik Roy", "https://robohash.org/laboriosamfacilisrem.png", System.currentTimeMillis())
        listUsers.clear()
        listUsers.add(user1)
        listUsers.add(user2)
        listUsers.add(user3)
        listUsers.add(user4)
        listUsers.add(user5)
        listUsers.add(user6)
        return listUsers
    }

    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun getStatusBarColor() = Color.RED

    override fun onCommentItemSelected(position: Int, item: UserComments, clickedFlag: Int) {
        Toast.makeText(requireContext(), item.comment_Id.toString(), Toast.LENGTH_SHORT).show()
        if (clickedFlag == 2) {
            val sheet = CommentFragment()
            sheet.show(childFragmentManager, "CommentFragment")
        }
    }
}
