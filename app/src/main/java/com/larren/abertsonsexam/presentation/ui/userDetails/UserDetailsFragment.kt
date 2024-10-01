package com.larren.abertsonsexam.presentation.ui.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.larren.abertsonsexam.databinding.FragmentUserDetailsBinding
import com.larren.abertsonsexam.presentation.base.BaseFragment

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {
    private val arg: UserDetailsFragmentArgs by navArgs()
    override fun onViewCreated(binding: FragmentUserDetailsBinding, savedInstanceState: Bundle?) {
        initViewModel()
    }

    private fun initViewModel() {
        val user = arg.userDetails
        binding.user = user
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserDetailsBinding.inflate(inflater, container, false)
}