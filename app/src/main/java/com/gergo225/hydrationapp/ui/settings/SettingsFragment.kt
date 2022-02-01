package com.gergo225.hydrationapp.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gergo225.hydrationapp.databinding.FragmentSettingsBinding
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val userPreferences = UserPreferences(application)
        val settingsViewModelFactory = SettingsViewModelFactory(userPreferences)
        settingsViewModel =
            ViewModelProvider(this, settingsViewModelFactory).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.settingsViewModel = settingsViewModel

        settingsViewModel.eventHydrationGoal.observe(viewLifecycleOwner) { goal ->
            if (goal) {
                val settingsOption = SettingsOption.GOAL
                navigateToUpdateValueFragment(settingsOption)
                settingsViewModel.onHydrationGoalCompleted()
            }
        }

        settingsViewModel.eventContainer1.observe(viewLifecycleOwner) { container1 ->
            if (container1) {
                val settingsOption = SettingsOption.CONTAINER1
                navigateToUpdateValueFragment(settingsOption)
                settingsViewModel.onContainer1Completed()
            }
        }
        settingsViewModel.eventContainer2.observe(viewLifecycleOwner) { container2 ->
            if (container2) {
                val settingsOption = SettingsOption.CONTAINER2
                navigateToUpdateValueFragment(settingsOption)
                settingsViewModel.onContainer2Completed()
            }
        }
        settingsViewModel.eventContainer3.observe(viewLifecycleOwner) { container3 ->
            if (container3) {
                val settingsOption = SettingsOption.CONTAINER3
                navigateToUpdateValueFragment(settingsOption)
                settingsViewModel.onContainer3Completed()
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun navigateToUpdateValueFragment(settingsOption: SettingsOption) {
        findNavController().navigate(
            SettingsFragmentDirections.actionNavigationSettingsToUpdateValueFragment(
                settingsOption
            )
        )
    }

}