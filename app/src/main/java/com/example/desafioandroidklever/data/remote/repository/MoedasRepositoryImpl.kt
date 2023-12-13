package com.example.desafioandroidklever.data.remote.repository

import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.data.remote.service.MoedasAPIService
import javax.inject.Inject

class MoedasRepositoryImpl @Inject constructor(
    private val moedasAPIService: MoedasAPIService
) {

    suspend fun listar(): List<ResultadoBlockchainAPIItem> {

        val resposta = moedasAPIService.recuperarPrecosMoedas()

        if (resposta.isSuccessful && resposta.body() != null) {
           var listaMoedas = resposta.body()
            if (listaMoedas != null) {
                return listaMoedas
            }
        }
        return emptyList()
    }

}