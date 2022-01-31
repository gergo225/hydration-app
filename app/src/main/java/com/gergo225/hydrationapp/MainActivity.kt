package com.gergo225.hydrationapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gergo225.hydrationapp.databinding.ActivityMainBinding
import com.gergo225.hydrationapp.ui.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = MainViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainViewModel = mainViewModel

        setSupportActionBar(binding.customToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitleText.text = destination.label
            if (destination.id == R.id.navigation_settings) {
                binding.settingsButton.visibility = View.GONE
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
                if (destination.id == R.id.navigation_history)
                    binding.settingsButton.visibility = View.GONE
                else
                    binding.settingsButton.visibility = View.VISIBLE
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainViewModel.eventSettings.observe(this) { eventSettings ->
            if (eventSettings) {
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToNavigationSettings())
                mainViewModel.onSettingsEventCompleted()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
    }

}