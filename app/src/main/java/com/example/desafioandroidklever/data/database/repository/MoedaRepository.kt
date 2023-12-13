package com.example.desafioandroidklever.data.database.repository

import android.util.Log
import com.example.desafioandroidklever.data.database.dao.MoedaDAO
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.presentation.viewmodel.resultado.ResultadoOperacao
import javax.inject.Inject

class MoedaRepository @Inject constructor(
    private val moedaDAO: MoedaDAO
) {

    suspend fun listar(): List<HistoricoMoeda> {
        return try {
            moedaDAO.listar()
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }

    suspend fun salvar(moeda: HistoricoMoeda): ResultadoOperacao {
        try {
            val retornoOperacao = moedaDAO.salvar(moeda)
            return if (retornoOperacao > 0) {
                ResultadoOperacao(
                    true,
                    "Sucesso ao salvar MOEDA"
                )
            } else {
                ResultadoOperacao(
                    false,
                    "Erro ao salvar MOEDA"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ResultadoOperacao(false, "Erro inesperado ao salvar")
    }

    suspend fun atualizar(moeda: HistoricoMoeda): ResultadoOperacao {
        try {
            val retorno = moedaDAO.atualizar(moeda)
            return if (retorno > 0) {//sucesso
                ResultadoOperacao(
                    true,
                    "Sucesso ao Atualizar MOEDA"
                )
            } else {
                return ResultadoOperacao(
                    false,
                    "Erro ao Atualizar MOEDA"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("ATUALIZAR", "atualizar: ${e.message}")
        }
        return ResultadoOperacao(false, "Erro inesperado ao atualizar")
    }

        suspend fun remover(moeda: HistoricoMoeda): ResultadoOperacao {
            try {
                val quantidadeLinhasRemovidas = moedaDAO.remover(moeda)
                return if (quantidadeLinhasRemovidas > 0) {//sucesso
                    ResultadoOperacao(
                        true,
                        "Sucesso ao Deletar MOEDA"
                    )
                } else {
                    return ResultadoOperacao(
                        false,
                        "Erro ao Deletar MOEDA"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("ATUALIZAR", "atualizar: ${e.message}")
            }
            return ResultadoOperacao(false, "Erro inesperado ao Deletar")

        }
    }