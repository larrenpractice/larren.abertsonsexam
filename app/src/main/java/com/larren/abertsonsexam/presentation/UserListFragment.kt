package com.larren.abertsonsexam.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.databinding.FragmentUserListBinding
import com.larren.abertsonsexam.presentation.base.BaseFragment
import com.larren.abertsonsexam.presentation.base.UserListViewModel
import com.larren.abertsonsexam.presentation.state.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>() {
    private val viewModel: UserListViewModel by viewModels()
    private val collectRandomUserListState = FlowCollector(::onRandomUserListState)
    override fun onViewCreated(binding: FragmentUserListBinding, savedInstanceState: Bundle?) {
        initView()
        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomUserListState.collect(collectRandomUserListState)
            }
        }
    }

    private fun onRandomUserListState(state: ResultState<RandomUserResponse>) {
        when (state) {
            is ResultState.Loading -> {}
            is ResultState.Success -> {
                Log.e("Fragment", "onRandomUserListState state: ${state.data}")
            }

            is ResultState.RemoteFailure -> {}
            else -> {}
        }
    }

    private fun initView() {
        viewModel.getRandomUserList(50)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(inflater, container, false)
}