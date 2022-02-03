package com.gergo225.hydrationapp.ui.update_value

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gergo225.hydrationapp.databinding.FragmentUpdateValueBinding
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class UpdateValueFragment : Fragment() {

    private lateinit var _binding: FragmentUpdateValueBinding
    private lateinit var updateValueViewModel: UpdateValueViewModel

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val arguments = UpdateValueFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val userPreferences = UserPreferences(application)
        val viewModelFactory =
            UpdateValueViewModelFactory(arguments.settingsOption, userPreferences)
        updateValueViewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateValueViewModel::class.java)

        _binding = FragmentUpdateValueBinding.inflate(inflater, container, false)

        binding.newValueEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                updateValueViewModel.onSubmitValue(Integer.parseInt(textView.text.toString()))

            true
        }

        updateValueViewModel.eventFinish.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                updateValueViewModel.onFinishEventCompleted()
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }
}