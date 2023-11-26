package com.app.hoichoiclone.screens.home.modules.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.FragmentAllBinding
import com.app.hoichoiclone.screens.home.modules.all.adapter.MovieAdapter
import com.app.hoichoiclone.screens.home.modules.all.adapter.PagerAdapter
import com.app.hoichoiclone.screens.home.modules.all.model.Detail
import com.app.hoichoiclone.screens.home.modules.all.model.MoviesDataModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
@AndroidEntryPoint
class FragmentAll : Fragment(), PagerAdapter.Interaction, MovieAdapter.Interaction {

    companion object {
        fun newInstance() = FragmentAll()
    }

    private lateinit var viewModel: FragmentAllViewModel
    private lateinit var _binding: FragmentAllBinding
    private lateinit var adapter: PagerAdapter
    private lateinit var contentadapter: MovieAdapter

    var listPagerData: MutableList<Detail> = ArrayList()
    // var listContentData: MutableMap<String, List<Detail>> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FragmentAllViewModel::class.java]

        val gson = Gson()
        val i: InputStream = requireActivity().assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: MoviesDataModel = gson.fromJson(br, MoviesDataModel::class.java)

        prepareSliderData(dataList)
        prepareMovieContentData(dataList)
    }

    private fun prepareMovieContentData(dataList: MoviesDataModel) {
        // listContentData.clear()

        contentadapter = MovieAdapter(requireContext(), this)
        _binding.contentList.adapter = contentadapter
        _binding.contentList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList.result)
        contentadapter.notifyDataSetChanged()
    }

    private fun prepareSliderData(dataList: MoviesDataModel) {
        listPagerData.clear()
        for (i in 0 until dataList.result.count()) {
            for (j in 0 until dataList.result[i].details.size) {
                listPagerData.add(dataList.result[i].details[j])
            }
        }

        adapter = PagerAdapter(requireContext(), this)
        _binding.movieListSlider.adapter = adapter
        _binding.movieListSlider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(_binding.movieListSlider)
        adapter.submitList(listPagerData)
        adapter.notifyDataSetChanged()
    }

    override fun onItemSelected(position: Int, item: Detail) {
        Toast.makeText(requireContext(), "" + item.title, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.detailFragment)
    }

    override fun onItemSelectedHorizontal(position: Int, item: Detail) {
        Toast.makeText(requireContext(), "" + item.title, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.detailFragment)
    }
}
