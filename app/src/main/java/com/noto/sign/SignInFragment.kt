package com.noto.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.noto.databinding.FragmentSignInBinding
import com.noto.util.snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    private val viewModel by viewModel<SignSharedViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = this@SignInFragment
            viewModel = this@SignInFragment.viewModel
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.loginUser()
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.tvSkip.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionGlobalLibraryListFragment())
        }

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            when {
                error.contains("Email", true) -> binding.tilEmail.error = error
                error.contains("Password", true) -> binding.tilPassword.error = error
                else -> binding.root.snackbar(error)
            }
        })

        return binding.root
    }
}