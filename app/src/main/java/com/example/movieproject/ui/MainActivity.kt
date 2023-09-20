package com.example.movieproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.movieproject.R
import com.example.movieproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavView = binding.bottomNavBar
        setupWithNavController(bottomNavView,navController)

        navController.addOnDestinationChangedListener{_,navDes,_->
            if(navDes.id == R.id.detailFragment){
                bottomNavView.visibility = View.GONE
            }else{
                bottomNavView.visibility = View.VISIBLE
            }}

    }

}