package com.app.hoichoiclone.screens.home.modules.shows

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.hoichoiclone.R

class ShowsFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsFragment()
    }

    private lateinit var viewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}