package com.example.movieapp.ui.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.adapters.TopRatedAdapter
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.models.Results
import com.example.movieapp.viewmodels.MovieViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var listTopRated: List<Results>
    private var currentMovie: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        setupTopRatedCarousel()
        observeTopRatedCarousel()
        handleButton()
    }

    private fun setupTopRatedCarousel() {
        topRatedAdapter = TopRatedAdapter(emptyList())

        movieViewModel.getTopRatedList("en-US", 1)
    }

    private fun observeTopRatedCarousel() {
        binding.viewPagerTopRated.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }

        movieViewModel.listTopRated.observe(this) { list ->
            listTopRated = list
            topRatedAdapter = TopRatedAdapter(list)
            binding.viewPagerTopRated.adapter = topRatedAdapter
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.viewPagerTopRated.apply {
            setPageTransformer(compositePageTransformer)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentMovie = position
                }
            })
        }
    }

    private fun handleButton() {
        binding.apply {
            buttonPlay.setOnClickListener {
                Intent(this@HomeActivity, DetailActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }
}