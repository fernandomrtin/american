package com.example.american.main.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.american.base.navigation.NavigationCommand
import com.example.american.databinding.PrivateZoneFragmentBinding
import com.example.american.main.ui.viewmodel.PrivateZoneViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PrivateZoneFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PrivateZoneViewModel

    private lateinit var binding: PrivateZoneFragmentBinding

    // ///////////////////////////////////////////////////////////////////////////
    // LifeCycle
    // ///////////////////////////////////////////////////////////////////////////

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PrivateZoneFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get()
        setOnClickListeners()
        setObservers()
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Methods
    // ///////////////////////////////////////////////////////////////////////////

    private fun setOnClickListeners() {
        binding.closeSessionButton.setOnClickListener {
            viewModel.onCloseSessionButtonTapped()
        }
    }

    private fun setObservers() {
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer { command ->
            when (command) {
                is NavigationCommand.Back -> findNavController().navigateUp()
            }
        })
    }
}
