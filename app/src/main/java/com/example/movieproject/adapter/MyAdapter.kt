package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.databinding.ItemGridMovieBinding
import com.example.movieproject.databinding.ItemListMovieBinding
import com.example.movieproject.response.MovieList
import com.example.movieproject.utils.Constants

class MyAdapter (
    private val itemClickListener : (MovieList.Result) -> Unit
): PagingDataAdapter<MovieList.Result, RecyclerView.ViewHolder>(differCallback){


    private var VIEW_TYPE = 0


    fun setViewType(viewType: Int){
        VIEW_TYPE = viewType
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(VIEW_TYPE){
            ITEM_TYPE_GRID -> {
                return GridViewHolder(ItemGridMovieBinding.
                inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
            ITEM_TYPE_LIST -> {
                return ListViewHolder(ItemListMovieBinding.
                inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
        }
        return ListViewHolder(ItemListMovieBinding.
        inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if(holder is ListViewHolder){
            holder.bind(getItem(position)!!)
            holder.setIsRecyclable(false)
        }

        else if(holder is GridViewHolder){
            holder.bind(getItem(position)!!)
            holder.setIsRecyclable(false)
        }

    }

    inner class ListViewHolder(val binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie : MovieList.Result){
            binding.nameMovie.text = movie.originalTitle
            binding.releaseDateMovie.text = movie.releaseDate
            binding.ratingMovie.text = movie.voteAverage.toString()
            val imgUrl = Constants.POSTER_BASE_URL + movie.posterPath
            Glide.with(binding.imgMovie)
                .load(imgUrl)
                .into(binding.imgMovie)




            binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }
    }
    inner class GridViewHolder(val binding: ItemGridMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie : MovieList.Result){
            binding.nameMovie.text = movie.originalTitle
            binding.ratingMovie.text = movie.voteAverage.toString()
            val imgUrl = Constants.POSTER_BASE_URL + movie.posterPath
            Glide.with(binding.imgMovie)
                .load(imgUrl)
                .into(binding.imgMovie)


            binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }
    }

    companion object{
        val differCallback = object : DiffUtil.ItemCallback<MovieList.Result>(){
            override fun areItemsTheSame(
                oldItem: MovieList.Result,
                newItem: MovieList.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieList.Result,
                newItem: MovieList.Result
            ): Boolean {
                return oldItem == newItem
            }

        }

        const val ITEM_TYPE_LIST = 0
        const val ITEM_TYPE_GRID = 1

    }

}
