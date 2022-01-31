package com.gergo225.hydrationapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gergo225.hydrationapp.databinding.FragmentHomeBinding
import com.gergo225.hydrationapp.repository.database.HydrationDatabase
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dao = HydrationDatabase.getInstance(application).hydrationDatabaseDao
        val userPreferences = UserPreferences(application)
        val homeViewModelFactory = HomeViewModelFactory(dao, userPreferences)
        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeViewModel = homeViewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}