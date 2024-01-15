package com.example.movieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.TopRatedItemBinding
import com.example.movieapp.models.Results
import com.squareup.picasso.Picasso

class TopRatedAdapter(private var listTopRated: List<Results>) :
    RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {
    inner class TopRatedViewHolder(val topRatedItemBinding: TopRatedItemBinding) :
        RecyclerView.ViewHolder(topRatedItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        return TopRatedViewHolder(
            TopRatedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listTopRated.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val item = listTopRated[position]

        holder.topRatedItemBinding.apply {
            Picasso.get().load("https://image.tmdb.org/t/p/w342${item.posterPath}")
                .into(backgroundImage)
            textDateRelease.text = "Release: ${item.releaseDate}"
        }
    }
}