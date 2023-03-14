package com.app.hoichoiclone.screens.home.modules.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.hoichoiclone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsFragment()
    }

    private lateinit var viewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
