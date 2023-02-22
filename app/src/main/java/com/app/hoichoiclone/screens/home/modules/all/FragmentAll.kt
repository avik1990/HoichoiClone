package com.app.hoichoiclone.screens.home.modules.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.hoichoiclone.R

class FragmentAll : Fragment() {

    companion object {
        fun newInstance() = FragmentAll()
    }

    private lateinit var viewModel: FragmentAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentAllViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
