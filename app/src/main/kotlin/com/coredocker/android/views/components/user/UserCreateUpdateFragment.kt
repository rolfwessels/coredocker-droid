package com.coredocker.android.views.components.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.coredocker.android.R
import com.coredocker.android.databinding.UserCreateUpdateFragmentBinding
import com.coredocker.android.services.Navigate
import com.coredocker.android.util.extensions.hideKeyboard
import org.koin.android.ext.android.inject


class UserCreateUpdateFragment : Fragment() {

    private val _viewModel: UserCreateUpdateFragmentViewModel by inject()
    private val _navigate: Navigate by inject()
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewModel.setArguments(arguments!!)
      val binding = DataBindingUtil.inflate<UserCreateUpdateFragmentBinding>(
            inflater,
            R.layout.user_create_update_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.vm = _viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        _navigate.currentView.observe(this, Observer { n ->
            when (n.location) {
                Navigate.Location.UserFragment -> {
                    navController.popBackStack()
                    view.context.hideKeyboard()
                }
                else -> throw ArrayIndexOutOfBoundsException("Unknown nav location ${n.location}")
            }
        })

    }
}

