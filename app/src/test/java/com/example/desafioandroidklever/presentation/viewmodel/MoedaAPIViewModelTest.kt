package com.example.desafioandroidklever.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aulatesteapipratico.util.getOrAwaitValue
import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.data.remote.repository.MoedasRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith( MockitoJUnitRunner::class )
class MoedaAPIViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moedasRepositoryImpl: MoedasRepositoryImpl

    private lateinit var moedaAPIViewModel: MoedaAPIViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        moedaAPIViewModel = MoedaAPIViewModel( moedasRepositoryImpl )
    }

    @Test
    fun recuperarListaMoedas_validarLiveData_retornaLiveData() = runTest {

        Mockito.`when`( moedasRepositoryImpl.listar() ).thenReturn(
            listOf(
                ResultadoBlockchainAPIItem(
                    1200.00, 1190.00, "Bitcoin", 1400.00
                ),
                ResultadoBlockchainAPIItem(
                    2200.00, 2190.00, "Etherium", 2400.00
                )
            )
        )

        moedaAPIViewModel.listar()
        val liveData = moedaAPIViewModel.lista.getOrAwaitValue()
        assertThat( liveData ).isNotEmpty()

    }

}