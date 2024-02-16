package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.databinding.ItemGridMovieBinding
import com.example.movieproject.databinding.ItemListMovieBinding
import com.example.movieproject.db.FavoritesData
import com.example.movieproject.utils.Constants.POSTER_BASE_URL

class FavoritesAdapter(
    private val itemClickListener : (FavoritesData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val favList = arrayListOf<FavoritesData>()

    fun updateList(updatedListt : List<FavoritesData>){
        favList.clear()
        favList.addAll(updatedListt)
        notifyDataSetChanged()
    }

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

    override fun getItemCount() =favList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = favList[position]

        if(holder is ListViewHolder){

            holder.binding.nameMovie.text = movie.originalTitle
            holder.binding.releaseDateMovie.text = movie.releaseDate
            holder.binding.ratingMovie.text = movie.voteAverage.toString()
            val imgUrl = POSTER_BASE_URL + movie.posterPath
            Glide.with(holder.binding.imgMovie)
                .load(imgUrl)
                .into(holder.binding.imgMovie)

            holder.binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }

        else if(holder is GridViewHolder){


            holder.binding.nameMovie.text = movie.originalTitle
            holder.binding.ratingMovie.text = movie.voteAverage.toString()
            val imgUrl = POSTER_BASE_URL + movie.posterPath
            Glide.with(holder.binding.imgMovie)
                .load(imgUrl)
                .into(holder.binding.imgMovie)

            holder.binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }


    }

    inner class ListViewHolder(val binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root){

    }
    inner class GridViewHolder(val binding: ItemGridMovieBinding) : RecyclerView.ViewHolder(binding.root){

    }

    companion object{
        const val ITEM_TYPE_LIST = 0
        const val ITEM_TYPE_GRID = 1
    }
}