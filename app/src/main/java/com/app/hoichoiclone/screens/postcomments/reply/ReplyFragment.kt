package com.app.hoichoiclone.screens.postcomments.reply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.hoichoiclone.databinding.FragmentReplyBinding
import com.app.hoichoiclone.utility.bottomsheet.SuperBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplyFragment : SuperBottomSheetFragment() {

    companion object {
        fun newInstance() = ReplyFragment()
    }

    private lateinit var viewModel: ReplyViewModel
    private lateinit var _binding: FragmentReplyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentReplyBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ReplyViewModel::class.java]
    }
}
