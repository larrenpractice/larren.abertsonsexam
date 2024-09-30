package com.larren.abertsonsexam.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.larren.abertsonsexam.databinding.FragmentUserListBinding
import com.larren.abertsonsexam.presenter.base.BaseFragment

class UserListFragment : BaseFragment<FragmentUserListBinding>() {
    override fun onViewCreated(binding: FragmentUserListBinding, savedInstanceState: Bundle?) {
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(inflater, container, false)
}