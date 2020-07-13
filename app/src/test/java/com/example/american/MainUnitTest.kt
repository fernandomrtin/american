package com.example.american

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.User
import com.example.american.main.domain.usecase.PostDoLoginUseCase
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

    private val user = User("fernando", "password")

    private val tokenCode = "9382ewfklakej329ufb2"

    @Before
    fun setUp() {
        val repository = mockk<AmericanClientRepository>()
        coEvery {
            repository.postDoLogin(user)
        }.returns(Either.Right(SessionToken(tokenCode)))
        val doLoginUseCase = PostDoLoginUseCase(repository)
        viewModel = MainViewModel(doLoginUseCase)
    }

    @Test
    fun `success if session token is not empty`() {
        val observer = mock(Observer::class.java) as Observer<in SessionTokenModel>
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.sessionToken.observeForever(observer)
            runBlocking {
                viewModel.doLoginUseCase(coroutinesTestRule.testDispatcher, user).join()
            }
            assertEquals(tokenCode, viewModel.sessionToken.value?.tokenCode)
        }
    }
}
