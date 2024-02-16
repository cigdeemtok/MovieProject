package com.example.movieproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.R
import com.example.movieproject.adapter.FavoritesAdapter
import com.example.movieproject.databinding.FragmentFavoriteBinding
import com.example.movieproject.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteBinding
    private val viewModel by viewModels<DetailViewModel>()

    private var favAdapter : FavoritesAdapter? = null
    private var linearLayoutManager :LinearLayoutManager? = null
    private var gridLayoutManager : GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        binding.toolbarFav.apply {
            inflateMenu(R.menu.menu_options)
            title = "Favorites"
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarFav.apply {
            setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
            setNavigationOnClickListener {
                val direction = FavoriteFragmentDirections.actionFavoriteFragmentToListFragment()
                val navController = Navigation.findNavController(requireView())
                navController.navigate(direction)
            }

        }
        //initialize layout
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        favAdapter = FavoritesAdapter{
            val direction = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.id!!).setTitle(it.originalTitle)
            val navController = Navigation.findNavController(requireView())
            navController.navigate(direction)

        }

        binding.recyclerFav.apply {
            layoutManager = linearLayoutManager
            adapter = favAdapter
        }


        //switch views
        binding.toolbarFav.setOnMenuItemClickListener {
            when (it.itemId) {
                //go to list view
                R.id.goToListView -> {
                    favAdapter!!.setViewType(0)
                    binding.recyclerFav.apply {
                        layoutManager = linearLayoutManager
                        adapter = favAdapter
                    }
                    true
                }
                //go to grid view
                R.id.goToGridView -> {

                    favAdapter!!.setViewType(1)
                    binding.recyclerFav.apply {
                        layoutManager = gridLayoutManager
                        adapter = favAdapter
                    }

                    true
                }
                else -> false
            }

        }



        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                binding.isFavLoading.visibility = VISIBLE
            }
            else{
                binding.isFavLoading.visibility = INVISIBLE
            }
        }
        viewModel.loadAllFav()

        viewModel.favMovie.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.emptyFavText.visibility = View.INVISIBLE
                favAdapter!!.updateList(it)

            }else{
                binding.emptyFavText.visibility = View.VISIBLE
                //update or rc gone++
                favAdapter!!.updateList(emptyList())
            }
        }

    }

}