package com.app.hoichoiclone.screens.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hoichoiclone.R
import com.app.hoichoiclone.databinding.FragmentDetailBinding
import com.app.hoichoiclone.screens.details.seasonstab.SeasonListFragment
import com.app.hoichoiclone.screens.details.seasonstab.model.SeasonModel
import com.app.hoichoiclone.screens.postcomments.commentlist.CommentFragment
import com.app.hoichoiclone.utility.Utils.addChildFragment
import com.app.hoichoiclone.utility.customviews.tabs.NavigationOption
import com.app.hoichoiclone.utility.customviews.tabs.TabAdapter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
@AndroidEntryPoint
class DetailFragment : Fragment(), TabAdapter.TabInteraction {

    companion object {
        fun newInstance() = DetailFragment()
        const val HLS_STATIC_URL =
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        // const val HLS_STATIC_URL =
        //   "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
        const val STATE_RESUME_WINDOW = "resumeWindow"
        const val STATE_RESUME_POSITION = "resumePosition"
        const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
        const val STATE_PLAYER_PLAYING = "playerOnPlay"
    }

    // lateinit var adapter: SeasonTabAdapter
    private lateinit var adapter: TabAdapter
    private var previousSelect = 0

    private lateinit var exoFullScreenIcon: ImageView
    private lateinit var exoFullScreenBtn: FrameLayout
    var listTabs: MutableList<NavigationOption> = ArrayList()

    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var viewModel: DetailViewModel
    private lateinit var _binding: FragmentDetailBinding

    private var fullscreenDialog: Dialog? = null
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var isFullscreen = false
    private var isPlayerPlaying = true

    private val mediaItem = MediaItem.Builder()
        .setUri(HLS_STATIC_URL)
        .setMimeType(MimeTypes.APPLICATION_MP4)
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return _binding.root
    }

    private fun initRecyclerView(context: Context, page: String) {

        val gson = Gson()
        val i: InputStream = requireActivity().assets.open("seasonlists.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: SeasonModel = gson.fromJson(br, SeasonModel::class.java)

        listTabs.clear()
        for (i in 0 until dataList.seasons.size) {
            listTabs.add(NavigationOption(id = (i + 1), text = dataList.seasons[i].name))
        }

        adapter.submitList(listTabs)
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        // /tabs
        adapter = TabAdapter(requireContext(), this)
        _binding?.navigationRecyclerView?.adapter = adapter
        _binding?.navigationRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        initRecyclerView(requireContext(), "Home")
        // /

        // /load the initial fragment
        val fieldFragment = SeasonListFragment()
        val bundle = Bundle()
        bundle.putInt(SeasonListFragment.ARG_POSITION, 0)
        fieldFragment.arguments = bundle
        addChildFragment(fieldFragment, R.id.nav_host_fragment)
        // /////////////
       /* _binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                val fieldFragment = SeasonListFragment()
                val bundle = Bundle()
                bundle.putInt(SeasonListFragment.ARG_POSITION, tab.position)
                fieldFragment.arguments = bundle
                addChildFragment(fieldFragment, R.id.nav_host_fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
               // tab.view.setBackgroundColor(Color.TRANSPARENT)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })*/

        exoFullScreenBtn = _binding?.playerView?.findViewById(R.id.exo_fullscreen_button)!!
        exoFullScreenIcon = _binding?.playerView?.findViewById(R.id.exo_fullscreen_icon)!!

        initFullScreenDialog()
        initFullScreenButton()
        initCommentLayout()

        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW)
            playbackPosition = savedInstanceState.getLong(STATE_RESUME_POSITION)
            isFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN)
            isPlayerPlaying = savedInstanceState.getBoolean(STATE_PLAYER_PLAYING)
        }
    }

    private fun initCommentLayout() {
        _binding.layoutComment.cardComment.setOnClickListener {
            val sheet = CommentFragment()
            sheet.show(childFragmentManager, "CommentFragment")
        }
    }

    private fun initPlayer() {
        exoPlayer = SimpleExoPlayer.Builder(requireContext()).build().apply {
            playWhenReady = isPlayerPlaying
            seekTo(currentWindow, playbackPosition)
            setMediaItem(mediaItem, false)
            prepare()
        }
        _binding.playerView.player = exoPlayer

        if (isFullscreen) {
            openFullscreenDialog()
        }
    }

    private fun releasePlayer() {
        isPlayerPlaying = exoPlayer.playWhenReady
        playbackPosition = exoPlayer.currentPosition
        currentWindow = exoPlayer.currentWindowIndex
        exoPlayer.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_RESUME_WINDOW, exoPlayer.currentWindowIndex)
        outState.putLong(STATE_RESUME_POSITION, exoPlayer.currentPosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, isFullscreen)
        outState.putBoolean(STATE_PLAYER_PLAYING, isPlayerPlaying)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
        _binding.playerView.onResume()
    }

    override fun onResume() {
        super.onResume()
        initPlayer()
        _binding.playerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _binding.playerView.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        _binding.playerView.onPause()
        releasePlayer()
    }

    // FULLSCREEN PART

    private fun initFullScreenDialog() {
        fullscreenDialog =
            object : Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
                override fun onBackPressed() {
                    if (isFullscreen) closeFullscreenDialog()
                    super.onBackPressed()
                }
            }
    }

    private fun initFullScreenButton() {
        exoFullScreenBtn.setOnClickListener {
            if (!isFullscreen) {
                openFullscreenDialog()
            } else {
                closeFullscreenDialog()
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun openFullscreenDialog() {
        exoFullScreenIcon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_fullscreen_shrink)
        )
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        (_binding.playerView.parent as ViewGroup).removeView(_binding.playerView)
        fullscreenDialog?.addContentView(
            _binding.playerView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        isFullscreen = true
        fullscreenDialog?.show()
    }

    private fun closeFullscreenDialog() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (_binding.playerView.parent as ViewGroup).removeView(_binding.playerView)
        _binding.mainMediaFrame.addView(_binding.playerView)
        exoFullScreenIcon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_fullscreen_expand)
        )
        isFullscreen = false
        fullscreenDialog?.dismiss()
    }

    override fun onItemSelected(position: Int, item: NavigationOption) {
        if (previousSelect != position) {
            adapter.currentItemSelected = position
            adapter.notifyItemChanged(position)
            adapter.notifyItemChanged(previousSelect)

            val fieldFragment = SeasonListFragment()
            val bundle = Bundle()
            bundle.putInt(SeasonListFragment.ARG_POSITION, position)
            fieldFragment.arguments = bundle
            addChildFragment(fieldFragment, R.id.nav_host_fragment)
        }
        previousSelect = position
    }
}
