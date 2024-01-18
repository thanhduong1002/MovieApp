package com.example.movieapp.ui.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.adapters.MovieAdapter
import com.example.movieapp.adapters.TopRatedAdapter
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.interfaces.IChooseMovie
import com.example.movieapp.models.Results
import com.example.movieapp.ui.fragments.DetailMovieSheet
import com.example.movieapp.viewmodels.MovieViewModel

class HomeActivity : AppCompatActivity(), IChooseMovie {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var listTopRatedMovie: List<Results>
    private var positionCurrentMovie: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        setupTopRatedCarouselAndRecyclerView()
        observeTopRatedCarouselAndRecyclerView()
        handleButton()
        handleShowHideToolbar()
    }

    private fun setupTopRatedCarouselAndRecyclerView() {
        topRatedAdapter = TopRatedAdapter(emptyList())
        popularAdapter = MovieAdapter(emptyList(), this)
        upcomingAdapter = MovieAdapter(emptyList(), this)

        movieViewModel.getTopRatedList("en-US", 1)
        movieViewModel.getPopularList("en-US", 1)
        movieViewModel.getUpcomingList("en-US", 1)
    }

    private fun observeTopRatedCarouselAndRecyclerView() {
        binding.viewPagerTopRated.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }

        movieViewModel.apply {
            listTopRated.observe(this@HomeActivity) { list ->
                list?.let {
                    listTopRatedMovie = list
                    topRatedAdapter = TopRatedAdapter(list)
                    binding.viewPagerTopRated.adapter = topRatedAdapter
                }
            }

            listPopulars.observe(this@HomeActivity) { listPop ->
                listPop?.let {
                    popularAdapter = MovieAdapter(listPop, this@HomeActivity)
                    binding.recyclerViewPopular.apply {
                        layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = popularAdapter
                    }
                }
            }

            listUpcoming.observe(this@HomeActivity) { listUp ->
                listUp?.let {
                    upcomingAdapter = MovieAdapter(listUp, this@HomeActivity)
                    binding.recyclerViewUpcoming.apply {
                        layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = upcomingAdapter
                    }
                }
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
                    positionCurrentMovie = position
                }
            })
        }
    }

    private fun handleButton() {
        binding.apply {
            buttonDetail.setOnClickListener {
                Intent(this@HomeActivity, DetailActivity::class.java)
                    .putExtra(DetailActivity.movieId, listTopRatedMovie[positionCurrentMovie].id)
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

    override fun onClickMovie(idMovie: Int) {
        val bundle = Bundle().apply {
            putInt(DetailMovieSheet.idMovie, idMovie)
        }

        DetailMovieSheet().apply {
            arguments = bundle
        }.show(supportFragmentManager, "Detail Movie Tag")
    }
}