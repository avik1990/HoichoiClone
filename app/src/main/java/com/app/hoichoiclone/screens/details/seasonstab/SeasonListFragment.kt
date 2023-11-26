package com.app.hoichoiclone.screens.details.seasonstab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.hoichoiclone.databinding.FragmentListSeasonsBinding
import com.app.hoichoiclone.screens.details.seasonstab.adapter.SeasonAdapter
import com.app.hoichoiclone.screens.details.seasonstab.model.SeasonModel
import com.app.hoichoiclone.screens.details.seasonstab.model.Video
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
@AndroidEntryPoint
class SeasonListFragment : Fragment(), SeasonAdapter.Interaction {

    private var position: Int = 0
    lateinit var photosAdapter: SeasonAdapter
    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var _binding: FragmentListSeasonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListSeasonsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gson = Gson()
        val i: InputStream = requireActivity().assets.open("seasonlists.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: SeasonModel = gson.fromJson(br, SeasonModel::class.java)

        photosAdapter = SeasonAdapter(this)
        _binding.recyclervwSeason.adapter = photosAdapter
        photosAdapter.setData(dataList.seasons[position].videos)
    }

    companion object {

        const val ARG_POSITION = "argSelectedTab"

        @JvmStatic
        fun newInstance(param: String) =
            SeasonListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POSITION, param)
                }
            }
    }

    override fun onItemSelected(position: Int, item: Video) {
        Toast.makeText(requireContext(), item.description, Toast.LENGTH_SHORT).show()
    }
}
