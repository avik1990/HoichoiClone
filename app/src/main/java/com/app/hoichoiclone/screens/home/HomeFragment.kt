package com.app.hoichoiclone.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.FragmentHomeBinding
import com.app.hoichoiclone.utility.customviews.tabs.NavigationOption
import com.app.hoichoiclone.utility.customviews.tabs.TabAdapter

class HomeFragment : Fragment(), TabAdapter.Interaction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TabAdapter
    private var previousSelect = 0
    lateinit var graph : NavGraph


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // tabs
        adapter = TabAdapter(requireContext(), this)
        _binding?.navigationRecyclerView?.adapter = adapter
        _binding?.navigationRecyclerView?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        initRecyclerView(requireContext(),"Home")
        ///

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        navHostFragment?.let { hostFragment ->
            val navHostFragmentController = hostFragment.navController

            val navInflater = navHostFragmentController.navInflater
            graph = navInflater.inflate(R.navigation.mobile_navigation)

        navHostFragmentController.graph = startFragmentBasedOnProfile(graph,0)
        }

        return root
    }


    private fun startFragmentBasedOnProfile(graph: NavGraph,position: Int): NavGraph {
        if(position==0) {
            graph.setStartDestination(R.id.allFragment)
        }else if(position==1){
            graph.setStartDestination(R.id.movieFragment)
        }else if(position==2){
            graph.setStartDestination(R.id.showFragment)
        }else if(position==3){
            graph.setStartDestination(R.id.watchFragment)
        }

        return graph
    }


    private fun initRecyclerView(context: Context, page: String) {
        val navigationOptions = NavigationOption.initNavigationOptions(context,page)
      //  mBinding?.appBarMain?.screenText?.text = if (isInitialy) "" else navigationOptions.first().text
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

            startFragmentBasedOnProfile(graph,position)
        }
        previousSelect = position
    }
}