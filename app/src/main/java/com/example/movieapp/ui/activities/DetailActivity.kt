package com.example.movieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.movieapp.R
import com.example.movieapp.adapters.FragmentTabAdapter
import com.example.movieapp.databinding.ActivityDetailBinding
import com.example.movieapp.ui.fragments.Tab1Fragment
import com.example.movieapp.ui.fragments.Tab2Fragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}