package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.databinding.ItemGenreListBinding
import com.example.movieproject.response.MovieDetails


class GenresAdapter (private val genresList : List<MovieDetails.Genre?>?) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {

        return GenresViewHolder(
            ItemGenreListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun getItemCount() = genresList!!.size

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = genresList?.get(position)

        if (genre != null) {
            holder.binding.genreText.text = genre.name
        }



    }

    inner class GenresViewHolder(val binding: ItemGenreListBinding) : RecyclerView.ViewHolder(binding.root)


}