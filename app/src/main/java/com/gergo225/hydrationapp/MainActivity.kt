package com.gergo225.hydrationapp

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gergo225.hydrationapp.databinding.ActivityMainBinding
import com.gergo225.hydrationapp.repository.database.DailyHydration
import com.gergo225.hydrationapp.repository.database.HydrationDatabase
import com.gergo225.hydrationapp.ui.home.HomeFragmentDirections
import kotlinx.coroutines.launch
import java.util.*

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

            if (destination.id == R.id.navigation_home)
                binding.settingsButton.visibility = View.VISIBLE
            else
                binding.settingsButton.visibility = View.GONE

            if (destination.id == R.id.navigation_home || destination.id == R.id.navigation_history)
                navView.visibility = View.VISIBLE
            else
                navView.visibility = View.GONE

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

        createSampleData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
    }

    private fun createSampleData() {
        // TODO: Remove this later (only for testing)
        val database = HydrationDatabase.getInstance(application).hydrationDatabaseDao
        database.getLast30().observe(this) { hydrationList ->
            val dataNumber = hydrationList.size
            if (dataNumber in 1..29) {


                val currDateMillis: Long = System.currentTimeMillis()
                val oneDayMillis: Long = 24 * 60 * 60 * 1000

                for (i in 2..45) {
                    val hydration = DailyHydration()
                    hydration.apply {
                        hydrationMl = (1400..2300).random()
                        dayDate = Date(currDateMillis - i * oneDayMillis)
                    }

                    Log.i(
                        "MainActivity",
                        "Hydration created for ${hydration.dayDate}, time: ${hydration.dayDate.time}"
                    )

                    lifecycleScope.launch {
                        database.insert(hydration)
                    }
                }
            }
        }
    }

}