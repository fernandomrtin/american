package com.example.american.main.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.american.R
import com.example.american.base.navigation.NavigationCommand
import com.example.american.databinding.MainFragmentBinding
import com.example.american.main.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navController: NavController

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

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
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel = ViewModelProvider(this, viewModelFactory).get()
        viewModel.init()
        setListeners()
        setObservers()
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Methods
    // ///////////////////////////////////////////////////////////////////////////

    private fun setListeners() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.onLoginButtonTapped(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer { command ->
            when (command) {
                is NavigationCommand.To -> navController.navigate(command.directions)
            }
        })
        viewModel.errorVisibility.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                showErrorView()
            }
        })
    }

    private fun showErrorView() {
        val errorSnackbar = Snackbar.make(binding.main, getString(R.string.error_message), Snackbar.LENGTH_LONG)
        errorSnackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.errorColor))
        errorSnackbar.show()
    }
}
