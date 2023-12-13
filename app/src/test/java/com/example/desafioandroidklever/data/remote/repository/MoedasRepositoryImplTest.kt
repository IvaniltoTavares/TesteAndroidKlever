package com.example.desafioandroidklever.data.remote.repository

import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.data.remote.service.MoedasAPIService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith( MockitoJUnitRunner::class )
class MoedasRepositoryImplTest {

    @Mock
    private lateinit var moedasAPIService: MoedasAPIService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun listar_recuperarListaMoedas_retornaLista() = runTest {

        Mockito.`when`( moedasAPIService.recuperarPrecosMoedas() ).thenReturn(
            Response.success(
                listOf(
                    ResultadoBlockchainAPIItem(
                        1200.00, 1190.00, "Bitcoin", 1400.00
                    ),
                    ResultadoBlockchainAPIItem(
                        2200.00, 2190.00, "Etherium", 2400.00
                    ),
                )
            )
        )

        val moedasRepositoryImpl = MoedasRepositoryImpl( moedasAPIService )
        val lista = moedasRepositoryImpl.listar()

        assertThat( lista ).isNotEmpty()

    }

}