package com.larren.abertsonsexam.data.repository

import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.api.service.RandomUsersApi
import com.larren.abertsonsexam.domain.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response as RetrofitResponse

@OptIn(ExperimentalCoroutinesApi::class)
class RandomUserRepositoryImplTest {

    @Mock
    private lateinit var api: RandomUsersApi

    private lateinit var repository: RandomUserRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    private val numberOfUsers = 5

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = RandomUserRepositoryImpl(api)
    }

    @Test
    fun `getRandomUserList returns success when api call is successful`() =
        runTest(testDispatcher) {
            // Mock API Response
            val mockResponse = RandomUserResponse()
            val retrofitResponse = RetrofitResponse.success(mockResponse)

            // Stub the API call
            `when`(api.getRandomUserList(numberOfUsers)).thenReturn(retrofitResponse)

            // Call the repository method
            val result = repository.getRandomUserList(numberOfUsers)

            // Assert that it returns a success response
            assert(result is Response.Success)
            assertEquals(mockResponse, (result as Response.Success).value)

            // Verify that the API was called once
            verify(api, times(1)).getRandomUserList(numberOfUsers)
        }

    @Test
    fun `getRandomUserList returns failure when api call is unsuccessful`() =
        runTest(testDispatcher) {
            // Mock Error Response
            val mockJsonError =
                "{\n  error: \"Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you.\" }"
            val mockMessage =
                "Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you."
            val errorResponseBody =
                ResponseBody.create(null, mockJsonError)
            val retrofitResponse =
                RetrofitResponse.error<RandomUserResponse>(422, errorResponseBody)

            // Stub the API call
            `when`(api.getRandomUserList(numberOfUsers)).thenReturn(retrofitResponse)

            // Call the repository method
            val result = repository.getRandomUserList(numberOfUsers)

            // Assert that it returns a failure response
            assert(result is Response.Failure)
            assertEquals(mockMessage, (result as Response.Failure).errorMessage)

            // Verify that the API was called once
            verify(api, times(1)).getRandomUserList(numberOfUsers)
        }

    @Test
    fun `getRandomUserList returns failure with unknown error when api returns malformed error body`() =
        runTest(testDispatcher) {
            // Mock Error Response with malformed body
            val mockInValidJson = "{ invalid json }"
            val errorResponseBody = ResponseBody.create(null, mockInValidJson)
            val retrofitResponse =
                RetrofitResponse.error<RandomUserResponse>(422, errorResponseBody)

            // Stub the API call
            `when`(api.getRandomUserList(numberOfUsers)).thenReturn(retrofitResponse)

            // Call the repository method
            val result = repository.getRandomUserList(numberOfUsers)

            // Assert that it returns a failure response with "Unknown error"
            assert(result is Response.Failure)
            assertEquals("Unknown error", (result as Response.Failure).errorMessage)

            // Verify that the API was called once
            verify(api, times(1)).getRandomUserList(numberOfUsers)
        }

    @Test
    fun `getRandomUserList returns failure when exception is thrown`() = runTest(testDispatcher) {
        // Stub the API call to throw an exception
        `when`(api.getRandomUserList(numberOfUsers)).thenThrow(RuntimeException("Network error"))

        // Call the repository method
        val result = repository.getRandomUserList(numberOfUsers)

        // Assert that it returns a failure response
        assert(result is Response.Failure)
        assertEquals("Network error", (result as Response.Failure).errorMessage)

        // Verify that the API was called once
        verify(api, times(1)).getRandomUserList(numberOfUsers)
    }
}