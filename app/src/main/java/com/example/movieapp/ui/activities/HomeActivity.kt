package com.example.movieapp.ui.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private var positionCurrentMovie: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        setupTopRatedCarousel()
        observeTopRatedCarousel()
        handleButton()
        handleShowHideToolbar()
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
            list?.let {
                listTopRated = list
                topRatedAdapter = TopRatedAdapter(list)
                binding.viewPagerTopRated.adapter = topRatedAdapter
            }
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
                    Log.d("position", "position: $position")
                    positionCurrentMovie = position
                }
            })
        }
    }

    private fun handleButton() {
        binding.apply {
            buttonDetail.setOnClickListener {
                Intent(this@HomeActivity, DetailActivity::class.java)
                    .putExtra(DetailActivity.movieId, listTopRated[positionCurrentMovie].id)
                    .run {
                        startActivity(this)
                    }
            }
        }
    }

    private fun handleShowHideToolbar() {
        var isShow = false
        var scrollRange = -1

        binding.appBar.addOnOffsetChangedListener { _, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = binding.appBar.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                binding.apply {
                    relativeAvatar.visibility = View.INVISIBLE
                    toolbar.visibility = View.VISIBLE
                }
                isShow = true
            } else if (isShow) {
                binding.apply {
                    relativeAvatar.visibility = View.VISIBLE
                    toolbar.visibility = View.INVISIBLE
                }
                isShow = false
            }
        }
    }
}