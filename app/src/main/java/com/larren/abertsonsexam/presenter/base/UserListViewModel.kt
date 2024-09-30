package com.larren.abertsonsexam.presenter.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larren.abertsonsexam.domain.usecases.GetRandomUserList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getRandomUserList: GetRandomUserList
) : ViewModel() {

    fun getRandomUserList(number: Int) {
        viewModelScope.launch {
            val result = getRandomUserList.invoke(number)
            Log.e("UserListViewModel", "result: $result")
        }
    }
}