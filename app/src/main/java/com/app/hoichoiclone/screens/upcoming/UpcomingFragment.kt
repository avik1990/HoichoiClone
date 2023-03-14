package com.app.hoichoiclone.screens.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.hoichoiclone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    companion object {
        fun newInstance() = UpcomingFragment()
    }

    private lateinit var viewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
