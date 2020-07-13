package com.example.american

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.domain.models.User
import com.example.american.main.domain.usecase.PostDoLoginUseCase
import com.example.american.main.domain.usecase.StoreSessionFieldsUseCase
import com.example.american.main.model.AmericanClientRepository
import com.example.american.main.ui.models.SessionTokenModel
import com.example.american.main.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
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

    lateinit var viewModel: MainViewModel

    private val correctUser = User("fernando", "fernando")
    private val wrongUser = User("pascual", "pascual")

    private val tokenCode = "9382ewfklakej329ufb2"

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
            repository.storeSessionFields(StorageSessionObject(correctUser.username, SessionToken(tokenCode)))
        }.returns(true)
        coEvery {
            repository.storeSessionFields(StorageSessionObject(wrongUser.username, SessionToken(tokenCode)))
        }.returns(false)
        val doLoginUseCase = PostDoLoginUseCase(repository)
        val storeSessionFieldsUseCase = StoreSessionFieldsUseCase(repository)
        viewModel = MainViewModel(doLoginUseCase, storeSessionFieldsUseCase)
    }

    @Test
    fun `success if login with correct user is ok`() {
        val observer = mock(Observer::class.java) as Observer<in SessionTokenModel>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.sessionToken.observeForever(observer)
            runBlocking {
                viewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, correctUser).join()
            }
            assertEquals(tokenCode, viewModel.sessionToken.value?.tokenCode)
        }
    }

    @Test
    fun `success if login with wrong user is ko`() {
        val observer = mock(Observer::class.java) as Observer<in SessionTokenModel>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.sessionToken.observeForever(observer)
            runBlocking {
                viewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, wrongUser).join()
            }
            assertEquals(true, viewModel.errorVisibility.value)
        }
    }
}
