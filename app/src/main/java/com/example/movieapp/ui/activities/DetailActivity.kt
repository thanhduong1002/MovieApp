package com.example.movieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.adapters.FragmentTabAdapter
import com.example.movieapp.databinding.ActivityDetailBinding
import com.example.movieapp.ui.fragments.Tab1Fragment
import com.example.movieapp.ui.fragments.Tab2Fragment
import com.example.movieapp.viewmodels.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabLayout()
        callAPiDetailMovie()

        observeDetailMovie()
    }

    companion object {
        const val movieId = "MovieId"
    }

    private fun observeDetailMovie() {
        movieViewModel.videosMovie.observe(this) { videos ->
            videos?.let {
                binding.apply {
                    viewYoutube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            videos[0].key?.let { videoId -> youTubePlayer.loadVideo(videoId, 0f) }
                        }
//                        override fun onStateChange(
//                            youTubePlayer: YouTubePlayer,
//                            state: PlayerConstants.PlayerState
//                        ) {
//                            // this method is called if video has ended,
//                            super.onStateChange(youTubePlayer, state)
//                        }
                    })
                }
            }
        }
    }

    private fun setupTabLayout() {
        val fragmentList = arrayListOf(Tab1Fragment(), Tab2Fragment())

        binding.apply {
            viewPager.adapter = FragmentTabAdapter(fragmentList, this@DetailActivity.supportFragmentManager, lifecycle)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.apply {
                            text = "Details"
                            icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_detail)
                        }
                    }
                    1 -> {
                        tab.apply {
                            text = "Comment"
                            icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_comment)
                        }
                    }
                }
            }.attach()
        }
    }

    private fun callAPiDetailMovie() {
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        movieViewModel.getVideoById(intent.getIntExtra(movieId, 0), "en-US")
    }
}