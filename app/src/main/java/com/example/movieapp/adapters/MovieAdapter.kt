package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.interfaces.IChooseMovie
import com.example.movieapp.models.Results
import com.squareup.picasso.Picasso

class MovieAdapter(private var listPopular: List<Results>, private val listener: IChooseMovie) :
    RecyclerView.Adapter<MovieAdapter.PopularViewHolder>() {
    inner class PopularViewHolder(val movieItemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listPopular.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = listPopular[position]

        holder.movieItemBinding.apply {
            Picasso.get().load("https://image.tmdb.org/t/p/w342${item.posterPath}")
                .into(imageMovie)
            cardMovie.setOnClickListener {
                item.id?.let { idMovie -> listener.onClickMovie(idMovie) }
            }
        }
    }
}