package com.app.hoichoiclone.screens.home.modules.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.hoichoiclone.databinding.FragmentAllBinding
import com.app.hoichoiclone.screens.home.modules.all.adapter.MovieAdapter
import com.app.hoichoiclone.screens.home.modules.all.adapter.PagerAdapter
import com.app.hoichoiclone.screens.home.modules.all.model.Detail
import com.app.hoichoiclone.screens.home.modules.all.model.MoviesDataModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FragmentAll : Fragment(), PagerAdapter.Interaction, MovieAdapter.Interaction {

    companion object {
        fun newInstance() = FragmentAll()
    }

    private lateinit var viewModel: FragmentAllViewModel
    private lateinit var _binding: FragmentAllBinding
    private lateinit var adapter: PagerAdapter
    private lateinit var contentadapter: MovieAdapter

    var listPagerData: MutableList<Detail> = ArrayList()
    var listContentData: MutableMap<String,List<Detail>> = HashMap()
    var listSubcategoryData: MutableList<Detail> = ArrayList()
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
        listContentData.clear()
        listSubcategoryData.clear()

        dataList.result.forEachIndexed { index, result ->
            result.details.forEach {
                listSubcategoryData.add(it)
            }
            listContentData[result.title] = listSubcategoryData
        }

        for ((key, value) in listContentData) {
            Log.e("PrintValues1",key)
            for (i in value.indices) {
             Log.e("PrintValues2",value[i].id.toString())
            }
        }

        contentadapter = MovieAdapter(requireContext(), this)
        _binding.contentList.adapter = contentadapter
        _binding.contentList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList.result, listContentData)
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
        // Toast.makeText(requireContext(),item,Toast.LENGTH_SHORT).s
    }
}
