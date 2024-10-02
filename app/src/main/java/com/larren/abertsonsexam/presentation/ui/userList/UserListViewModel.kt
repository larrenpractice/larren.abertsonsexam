package com.larren.abertsonsexam.presentation.ui.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.di.IoDispatcher
import com.larren.abertsonsexam.di.MainDispatcher
import com.larren.abertsonsexam.domain.usecases.GetRandomUserList
import com.larren.abertsonsexam.domain.util.Response
import com.larren.abertsonsexam.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getRandomUserList: GetRandomUserList,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _randomUserListState =
        MutableStateFlow<ResultState<RandomUserResponse>>(ResultState.Default())
    val randomUserListState = _randomUserListState.asStateFlow()

    private val _queryNumber = MutableStateFlow<Int?>(null)
    val queryNumber = _queryNumber.asStateFlow()

    fun getRandomUserList(number: Int) {
        _queryNumber.value = number
        _randomUserListState.value = ResultState.Loading(isLoading = true)
        viewModelScope.launch(ioDispatcher) {
            val result = when (val response = getRandomUserList.invoke(number)) {
                is Response.Success -> ResultState.Success(response.value)
                else -> {
                    val message = if (response is Response.Failure) {
                        response.errorMessage
                    } else "Error Thing"
                    ResultState.RemoteFailure(message = message)
                }
            }
            withContext(mainDispatcher) {
                _randomUserListState.value = ResultState.Loading(isLoading = false)
                _randomUserListState.value = result
            }
        }
    }
}