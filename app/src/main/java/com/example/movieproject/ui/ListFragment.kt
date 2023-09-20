package com.example.movieproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.R
import com.example.movieproject.adapter.LoadMoreAdapter
import com.example.movieproject.adapter.MyAdapter
import com.example.movieproject.databinding.FragmentListBinding
import com.example.movieproject.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var listBinding: FragmentListBinding
    private val listViewModel by viewModels<ListViewModel>()

    private var listAdapter: MyAdapter? = null
    private var searchAdapter: MyAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var gridLayoutManager: GridLayoutManager? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listBinding = FragmentListBinding.inflate(inflater, container, false)

        listBinding.myToolbar.inflateMenu(R.menu.menu_options)

        return listBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize layout
        //set layouts for both grid and list
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        //set adapter and get click event and navigate to details
        listAdapter = MyAdapter (
            itemClickListener = {
                val direction = ListFragmentDirections.actionListFragmentToDetailFragment(it.id!!).setTitle(it.originalTitle)
                val navController = Navigation.findNavController(requireView())
                navController.navigate(direction)

            }

        )
        //adapter for search results and navigation
        searchAdapter = MyAdapter (
            itemClickListener = {
                val direction = ListFragmentDirections.actionListFragmentToDetailFragment(it.id!!).setTitle(it.originalTitle)
                val navController = Navigation.findNavController(requireView())
                navController.navigate(direction)

            }
        )


        //init adapter
        listBinding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = listAdapter

        }


        listeners()
        listenViewModel()





    }

    @SuppressLint("NotifyDataSetChanged")
    private fun listenViewModel(){


        //listen live data and get list
        viewLifecycleOwner.lifecycleScope.launch {
            if (LIST_TYPE == 0) {
                listViewModel.movieList.collect {
                        listAdapter!!.submitData(it)

                    }
            }

        }



        //listen search result
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.list.collectLatest{
                searchAdapter!!.submitData(it)
                //searchAdapter!!.notifyDataSetChanged()
            }
        }

        //get state and show progress bar when loading
        viewLifecycleOwner.lifecycleScope.launch {
            listAdapter!!.loadStateFlow.collect {
                val state = it.refresh
                listBinding.listProgressBar.isVisible = state is LoadState.Loading
            }
        }
        //load more adapter set
        listBinding.recycler.adapter = listAdapter!!.withLoadStateFooter(
            LoadMoreAdapter {
                listAdapter!!.retry()
            }
        )

    }
    private fun listeners(){

        //searchview click listener
        listBinding.searchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               if (newText.isNullOrBlank()) {
                    listBinding.recycler.adapter = listAdapter

                    isInSearch(0)

                }else{
                    listBinding.recycler.adapter = searchAdapter

                    isInSearch(1)
                    listViewModel.setQuery(newText)
                    searchAdapter!!.notifyDataSetChanged()

                }
                return true
            }

        })


        //switch view
        listBinding.myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                //go to list view
                R.id.goToListView -> {
                    listAdapter!!.setViewType(0)
                    listBinding.recycler.apply {
                        layoutManager = linearLayoutManager
                        //adapter = myAdapter
                    }
                    true
                }
                //go to grid view
                R.id.goToGridView -> {

                    listAdapter!!.setViewType(1)
                    listBinding.recycler.apply {
                        layoutManager = gridLayoutManager
                        //adapter = myAdapter
                    }

                    true
                }

                else -> false
            }

        }


    }
    fun isInSearch(listType: Int){
        LIST_TYPE = listType
    }
    companion object{
        var LIST_TYPE = 0

    }
}
