package com.larren.abertsonsexam.presentation.ui.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.larren.abertsonsexam.R
import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.data.models.User
import com.larren.abertsonsexam.databinding.FragmentUserListBinding
import com.larren.abertsonsexam.presentation.base.BaseFragment
import com.larren.abertsonsexam.presentation.state.ResultState
import com.larren.abertsonsexam.presentation.util.sanitizedInput
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>() {
    private val viewModel: UserListViewModel by viewModels()
    private val collectRandomUserListState = FlowCollector(::onRandomUserListState)
    private var userListAdapter: UserListAdapter? = null
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
                state.data?.results?.apply {
                    userListAdapter?.updateData(this)
                }
            }

            is ResultState.RemoteFailure -> {}
            else -> {}
        }
    }

    private fun initView() {
        userListAdapter = UserListAdapter(emptyList(), ::onSelectedUser)
        binding.apply {
            rvUserList.layoutManager = LinearLayoutManager(requireContext())
            rvUserList.adapter = userListAdapter
            searchView.setOnQueryTextListener(setOnQueryTextListener)
            val queryNumber = viewModel.queryNumber.value
            updateQueryLabel(queryNumber)
        }
    }

    private val setOnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.sanitizedInput?.let {
                val number = it.toIntOrNull() ?: 0
                viewModel.getRandomUserList(number)
                updateQueryLabel(number)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }

    private fun updateQueryLabel(query: Int?) {
        binding.tvQuery.apply {
            if (query == null) {
                visibility = GONE
            } else {
                visibility = VISIBLE
                text = getString(R.string.result_query, query.toString())
            }
        }
    }

    private fun onSelectedUser(user: User) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(
            userDetails = user
        )
        findNavController().navigate(action)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(inflater, container, false)
}