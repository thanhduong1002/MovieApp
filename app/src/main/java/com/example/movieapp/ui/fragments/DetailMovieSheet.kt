package com.example.movieapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.databinding.FragmentDetailMovieSheetBinding
import com.example.movieapp.viewmodels.MovieViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class DetailMovieSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailMovieSheetBinding
    private var movieId = 0
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieSheetBinding.inflate(inflater, container, false)

        movieId = arguments?.getInt(idMovie, -1)!!
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieViewModel.getDetailMovie(movieId, "en-US")

        binding.apply {
            buttonClose.setOnClickListener {
                dismiss()
            }
        }

        observeDetailMovie()
    }

    companion object {
        const val idMovie = "IdMovie"
    }

    private fun checkAdult(adult: Boolean?): String {
        return if (adult == true) "18+"
        else "12+"
    }

    private fun observeDetailMovie() {
        movieViewModel.detailMovie.observe(this) { detail ->
            detail?.let {
                binding.apply {
                    Picasso.get().load("https://image.tmdb.org/t/p/w342${it.posterPath}")
                        .into(imageMovie)
                    nameMovie.text = it.title
                    yearRelease.text = "${it.releaseDate?.substring(0,4)}   ${checkAdult(it.adult)}   ${it.voteAverage} points"
                    overviewMovie.text = it.overview
                }
            }
        }
    }
}