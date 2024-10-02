import app.cash.turbine.test
import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.usecases.GetRandomUserList
import com.larren.abertsonsexam.domain.util.Response
import com.larren.abertsonsexam.presentation.state.ResultState
import com.larren.abertsonsexam.presentation.ui.userList.UserListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel

    @Mock
    private lateinit var getRandomUserList: GetRandomUserList

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserListViewModel(
            getRandomUserList = getRandomUserList,
            ioDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )
    }

    @Test
    fun `getRandomUserList emits loading and success states`() = runTest(testDispatcher) {
        val numberOfUsers = 5
        val mockResponse = RandomUserResponse()

        // Mock the behavior of the use case to return a success response
        whenever(getRandomUserList.invoke(numberOfUsers))
            .thenReturn(Response.Success(mockResponse))

        // Start collecting emissions from the StateFlow
        viewModel.randomUserListState.test {
            // Trigger the function
            viewModel.getRandomUserList(numberOfUsers)

            // Move the coroutine forward to capture the default state
            assert(awaitItem() is ResultState.Default)

            // Move the coroutine forward to capture the loading state
            assertEquals(ResultState.Loading(isLoading = true), awaitItem())

            // Move the coroutine forward to capture the loading state
            assertEquals(ResultState.Loading(isLoading = false), awaitItem())

            // Move the coroutine forward to capture the success state
            val successState = awaitItem()
            assertTrue(successState is ResultState.Success)
            assertEquals(mockResponse, successState.data)

            // Ensure that no more emissions are coming
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getRandomUserList emits loading, failure states on failure`() = runTest(testDispatcher) {
        val numberOfUsers = 5
        // Mock the behavior of the use case to return a failure response
        whenever(getRandomUserList.invoke(numberOfUsers)).thenReturn(Response.Failure("Network Error"))

        // Start collecting emissions from the StateFlow
        viewModel.randomUserListState.test {
            // Trigger the function
            viewModel.getRandomUserList(numberOfUsers)

            // Move the coroutine forward to capture the default state
            assert(awaitItem() is ResultState.Default)

            // Move the coroutine forward to capture the loading state
            assertEquals(ResultState.Loading(isLoading = true), awaitItem())

            // Move the coroutine forward to capture the loading state
            assertEquals(ResultState.Loading(isLoading = false), awaitItem())

            // Move the coroutine forward to capture the failure state
            assertEquals(ResultState.RemoteFailure(message = "Network Error"), awaitItem())
        }

        advanceUntilIdle() // Fast forward coroutine execution
    }

    @Test
    fun `getRandomUserList updates queryNumber`() = runTest(testDispatcher) {
        val numberOfUsers = 5

        viewModel.queryNumber.test {
            // Trigger the function
            viewModel.getRandomUserList(numberOfUsers)

            // Default value of queryNumber
            assertEquals(null, awaitItem())

            // Move the coroutine forward to capture the assigned value
            assertEquals(numberOfUsers, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}