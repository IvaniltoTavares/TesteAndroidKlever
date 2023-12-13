package com.example.desafioandroidklever.data.remote.service

import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import retrofit2.Response
import retrofit2.http.GET

interface MoedasAPIService {

    @GET("tickers")
    suspend fun recuperarPrecosMoedas() : Response< List<ResultadoBlockchainAPIItem> >

}