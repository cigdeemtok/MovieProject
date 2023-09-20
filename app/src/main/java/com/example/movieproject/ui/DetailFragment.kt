package com.example.movieproject.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.adapter.GenresAdapter
import com.example.movieproject.databinding.FragmentDetailBinding
import com.example.movieproject.response.MovieDetails
import com.example.movieproject.utils.Constants
import com.example.movieproject.viewmodel.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var detailBinding : FragmentDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private val args : DetailFragmentArgs by navArgs()

    private var isFavMovie : Boolean = false

    private var genreAdapter : GenresAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(inflater,container,false)
        val menu = detailBinding.toolbarDetail.inflateMenu(R.menu.menu_options)


        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //update toolbar items
        updateToolbar()

        //get movie id
        val movieId = args.id
        val toolTitle = args.title

        detailBinding.toolbarDetail.title = toolTitle

        val favBtn = detailBinding.toolbarDetail.menu.findItem(R.id.addToFav)


        detailBinding.toolbarDetail.apply {
            setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
            setNavigationOnClickListener {
                val direction = DetailFragmentDirections.actionDetailFragmentToListFragment()
                val navController = Navigation.findNavController(requireView())
                navController.navigate(direction)
            }

        }

        detailViewModel.apply {
            loadMovieDetails(movieId)
            refreshDatabase(movieId)
        }

        listenViewModel()



        favBtn.setOnMenuItemClickListener {
            if(isFavMovie){
                //uncheck-remove
                detailViewModel.favOrUnfav(false)
                favBtn.setIcon(android.R.drawable.btn_star_big_off)
            }
            else{
                //check-add
                detailViewModel.favOrUnfav(true)
                favBtn.setIcon(android.R.drawable.btn_star_big_on)
            }
            true
        }




        }

    private fun listenViewModel(){
        //get details and set ui
        detailViewModel.movieDetails.observe(viewLifecycleOwner){
            detailBinding.apply {
                detailNameMovie.text = it?.originalTitle
                detailOverviewMovie.text = it?.overview
                detailDateMovie.text = it?.releaseDate
                detailsRatingMovie.text = it?.voteAverage.toString()
                detailDurationMovie.text = it?.runtime.toString()
                val imgUrl = Constants.POSTER_BASE_URL + it?.posterPath
                Glide.with(detailBinding.detailImgMovie)
                    .load(imgUrl)
                    .into(detailBinding.detailImgMovie)

                genreAdapter = GenresAdapter(it?.genres)
                detailGenreRc.apply {
                    layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    adapter = genreAdapter
                }

            }

        }
        //loading check
        detailViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                detailBinding.detailProgressBar.visibility = View.VISIBLE
            }else{
                detailBinding.detailProgressBar.visibility = View.INVISIBLE
            }
        }
        //toggle for if it is a fav movie or not
        detailViewModel.isFavMovie.observe(viewLifecycleOwner){
            isFavMovie = it
            if(it){
                detailBinding.toolbarDetail.menu.findItem(R.id.addToFav).setIcon(android.R.drawable.btn_star_big_on)
            }else{
                detailBinding.toolbarDetail.menu.findItem(R.id.addToFav).setIcon(android.R.drawable.btn_star_big_off)
            }
        }

    }

    private fun updateToolbar(){
        val list = detailBinding.toolbarDetail.menu.findItem(R.id.goToListView)
        val grid = detailBinding.toolbarDetail.menu.findItem(R.id.goToGridView)
        val fav = detailBinding.toolbarDetail.menu.findItem(R.id.addToFav)

        list.isVisible = false
        grid.isVisible = false
        fav.isVisible = true

    }


    }
