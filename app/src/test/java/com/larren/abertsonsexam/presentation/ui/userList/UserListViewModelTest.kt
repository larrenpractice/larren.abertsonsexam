package com.larren.abertsonsexam.presentation.ui.userList

import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.usecases.GetRandomUserList
import com.larren.abertsonsexam.domain.util.Response
import com.larren.abertsonsexam.presentation.state.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private val getRandomUserList = mock<GetRandomUserList>()
    private lateinit var viewModel: UserListViewModel

    @Before
    fun setUp() {
        viewModel = UserListViewModel(getRandomUserList)
    }

    @Test
    fun `getRandomUserList success`() = runTest {
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        val number = 5
        val randomUserResponse = RandomUserResponse() // Create a mock response
        whenever(getRandomUserList.invoke(number)).thenReturn(Response.Success(randomUserResponse))

        viewModel.getRandomUserList(number)
        testDispatcher.advanceUntilIdle() // Use advanceUntilIdle directly

        verify(getRandomUserList).invoke(number)
        assertEquals(number, viewModel.queryNumber.value)
        println("value: ${viewModel.randomUserListState.value}")
        assertTrue(viewModel.randomUserListState.value is ResultState.Success)
        assertEquals(
            randomUserResponse,
            (viewModel.randomUserListState.value as ResultState.Success).data
        )
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomUserList failure`() = runTest {
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        val number = 5
        val errorMessage = "Error message"
        whenever(getRandomUserList.invoke(number)).thenReturn(Response.Failure(errorMessage))

        viewModel.getRandomUserList(number)
        testDispatcher.advanceUntilIdle() // Use advanceUntilIdle directly
        verify(getRandomUserList).invoke(number)
        assertEquals(number, viewModel.queryNumber.value)
        assertTrue(viewModel.randomUserListState.value is ResultState.RemoteFailure)
        assertEquals(
            errorMessage,
            (viewModel.randomUserListState.value as ResultState.RemoteFailure).message
        )
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomUserList loading states`() = runBlocking() {
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        val number = 5
        val randomUserResponse = RandomUserResponse() // Create a mock response
        whenever(getRandomUserList.invoke(number)).thenReturn(Response.Success(randomUserResponse))

        viewModel.getRandomUserList(number)

        // Check initial loading state
        assertTrue(viewModel.randomUserListState.value is ResultState.Loading)

        testDispatcher.advanceUntilIdle() // Use advanceUntilIdle
        println("value: ${viewModel.randomUserListState.value}")
        // Check success state after coroutine completion
        assertTrue(viewModel.randomUserListState.value is ResultState.Success)

        Dispatchers.resetMain()
    }
}