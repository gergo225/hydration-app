package com.gergo225.hydrationapp.ui.update_value

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gergo225.hydrationapp.R

class UpdateValueFragment : Fragment() {

    private lateinit var viewModel: UpdateValueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Implement fragment
        return inflater.inflate(R.layout.fragment_update_value, container, false)
    }
}