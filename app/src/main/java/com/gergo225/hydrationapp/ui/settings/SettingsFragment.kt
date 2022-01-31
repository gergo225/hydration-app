package com.gergo225.hydrationapp.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.lifecycleOwner = this

        return binding.root
    }

}