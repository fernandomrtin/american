package com.example.american.main.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.american.databinding.MainFragmentBinding
import com.example.american.main.ui.viewmodel.MainViewModel
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
        binding.loginButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainScreenToPrivateZoneScreen()
            navController.navigate(action)
        }
    }
}
