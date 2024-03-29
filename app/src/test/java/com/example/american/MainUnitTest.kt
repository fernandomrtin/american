package com.example.american

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import arrow.core.Either
import com.example.american.base.navigation.NavigationCommand
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.domain.models.User
import com.example.american.main.domain.usecase.PostDoLoginUseCase
import com.example.american.main.domain.usecase.RemoveStoreSessionFieldsUseCase
import com.example.american.main.domain.usecase.RetrieveStoreSessionFieldsUseCase
import com.example.american.main.domain.usecase.StoreSessionFieldsUseCase
import com.example.american.main.domain.usecase.ValidateSessionUseCase
import com.example.american.main.model.AmericanClientRepository
import com.example.american.main.ui.models.SessionTokenModel
import com.example.american.main.ui.view.MainFragmentDirections
import com.example.american.main.ui.viewmodel.MainViewModel
import com.example.american.main.ui.viewmodel.PrivateZoneViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MainUnitTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var mainViewModel: MainViewModel
    lateinit var privateZoneViewModel: PrivateZoneViewModel

    private val correctUser = User("fernando", "fernando")
    private val wrongUser = User("error", "pascual")

    private val tokenCode = "0394j02b982buewf98abfs8d"

    private val timeStamp = System.currentTimeMillis()

    @Before
    fun setUp() {
        val repository = mockk<AmericanClientRepository>()
        coEvery {
            repository.postDoLogin(correctUser)
        }.returns(Either.Right(SessionToken(tokenCode)))
        coEvery {
            repository.postDoLogin(wrongUser)
        }.returns(Either.Left(CommonError.NotFound))
        coEvery {
            repository.storeSessionFields(any())
        }.returns(true)
        coEvery {
            repository.postValidateSession(StorageSessionObject(correctUser.username, SessionToken(tokenCode), timeStamp))
        }.returns(Either.Right(true))
        coEvery {
            repository.postValidateSession(StorageSessionObject(wrongUser.username, SessionToken(tokenCode), timeStamp))
        }.returns(Either.Right(false))
        coEvery {
            repository.removeStoreSessionFields()
        }.returns(true)

        val doLoginUseCase = PostDoLoginUseCase(repository)
        val storeSessionFieldsUseCase = StoreSessionFieldsUseCase(repository)
        val retrieveStoreSessionFieldsUseCase = RetrieveStoreSessionFieldsUseCase(repository)
        val validateSessionUseCase = ValidateSessionUseCase(repository)
        val removeStoreSessionFieldsUseCase = RemoveStoreSessionFieldsUseCase(repository)
        mainViewModel = MainViewModel(doLoginUseCase, storeSessionFieldsUseCase, retrieveStoreSessionFieldsUseCase, validateSessionUseCase, removeStoreSessionFieldsUseCase)
        privateZoneViewModel = PrivateZoneViewModel(removeStoreSessionFieldsUseCase)
    }

    @Test
    fun `success if login with correct user is ok`() {
        val observer = mock(Observer::class.java) as Observer<in SessionTokenModel>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            mainViewModel.sessionToken.observeForever(observer)
            runBlocking {
                mainViewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, correctUser, timeStamp).join()
            }
            assertEquals(tokenCode, mainViewModel.sessionToken.value?.tokenCode)
        }
    }

    @Test
    fun `success if login with wrong user is ko`() {
        val observer = mock(Observer::class.java) as Observer<in SessionTokenModel>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            mainViewModel.sessionToken.observeForever(observer)
            runBlocking {
                mainViewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, wrongUser, timeStamp).join()
            }
            assertEquals(true, mainViewModel.errorVisibility.value)
        }
    }
    @Test
    fun `success if correct login orders navigation to private area`() {
        val observer = mock(Observer::class.java) as Observer<in NavigationCommand>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            mainViewModel.navigationCommand.observeForever(observer)
            runBlocking {
                mainViewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, correctUser, timeStamp).join()
            }
            val navigationCommandValue = mainViewModel.navigationCommand.value
            if (navigationCommandValue is NavigationCommand.To) {
                val directions: NavDirections = navigationCommandValue.directions
                assertEquals(MainFragmentDirections.actionMainScreenToPrivateZoneScreen(), directions)
            }
        }
    }

    @Test
    fun `success if removing session token from local orders navigation to login area`() {
        val observer = mock(Observer::class.java) as Observer<in NavigationCommand>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            privateZoneViewModel.navigationCommand.observeForever(observer)
            runBlocking {
                privateZoneViewModel.removeFieldsInLocal(coroutinesTestRule.testDispatcher).join()
            }
            val navigationCommandValue = privateZoneViewModel.navigationCommand.value
            assertTrue(navigationCommandValue is NavigationCommand.Back)
        }
    }
}
