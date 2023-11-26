package com.app.hoichoiclone.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.FragmentHomeBinding
import com.app.hoichoiclone.screens.home.modules.all.FragmentAll
import com.app.hoichoiclone.screens.home.modules.movies.MovieFragment
import com.app.hoichoiclone.screens.home.modules.shows.ShowsFragment
import com.app.hoichoiclone.screens.home.modules.watchlist.WatchlistFragment
import com.app.hoichoiclone.utility.Utils.addChildFragment
import com.app.hoichoiclone.utility.customviews.tabs.NavigationOption
import com.app.hoichoiclone.utility.customviews.tabs.TabAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), TabAdapter.TabInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TabAdapter
    private var previousSelect = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // /tabs
        adapter = TabAdapter(requireContext(), this)
        _binding?.navigationRecyclerView?.adapter = adapter
        _binding?.navigationRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        initRecyclerView(requireContext(), "Home")
        // /

        // /load the initial fragment
        val fieldFragment = FragmentAll()
        addChildFragment(fieldFragment, R.id.nav_host_fragment)
        // /////////////

        return root
    }

    private fun initRecyclerView(context: Context, page: String) {
        val navigationOptions = NavigationOption.initNavigationOptions(context, page)
        adapter.submitList(navigationOptions)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: NavigationOption) {
        if (previousSelect != position) {
            adapter.currentItemSelected = position
            adapter.notifyItemChanged(position)
            adapter.notifyItemChanged(previousSelect)

            // load fragment according to tab click
            when (position) {
                0 -> {
                    val fieldFragment = FragmentAll()
                    addChildFragment(fieldFragment, R.id.nav_host_fragment)
                }
                1 -> {
                    val fieldFragment = MovieFragment()
                    addChildFragment(fieldFragment, R.id.nav_host_fragment)
                }
                2 -> {
                    val fieldFragment = ShowsFragment()
                    addChildFragment(fieldFragment, R.id.nav_host_fragment)
                }
                3 -> {
                    val fieldFragment = WatchlistFragment()
                    addChildFragment(fieldFragment, R.id.nav_host_fragment)
                }
            }
        }
        previousSelect = position
    }
}
