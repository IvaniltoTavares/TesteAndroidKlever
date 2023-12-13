package com.example.desafioandroidklever.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.data.database.repository.MoedaRepository
import com.example.desafioandroidklever.presentation.viewmodel.resultado.ResultadoOperacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoricoMoedaViewModel @Inject constructor(
    private val moedaRepository: MoedaRepository
) : ViewModel() {

    private val resultadoOperacao = MutableLiveData<ResultadoOperacao>()
    val resultado: MutableLiveData<ResultadoOperacao>
        get() = resultadoOperacao

    private val listaMoedas = MutableLiveData<List<HistoricoMoeda>>()
    val lista: MutableLiveData<List<HistoricoMoeda>>
        get() = listaMoedas


    fun salvar(moeda: HistoricoMoeda) {

        viewModelScope.launch(Dispatchers.IO) {
            val resultado = moedaRepository.salvar(moeda)
            resultadoOperacao.postValue(resultado)
        }
    }

    fun atualizar(historicoMoeda: HistoricoMoeda) {

        viewModelScope.launch(Dispatchers.IO) {
            val resultado = moedaRepository.atualizar(historicoMoeda)
            withContext(Dispatchers.Main) {
                resultadoOperacao.postValue(resultado)
            }
        }
    }


    fun listar() {
        viewModelScope.launch(Dispatchers.IO) {
            val lista = moedaRepository.listar()
            withContext(Dispatchers.Main) {
                listaMoedas.postValue(lista)
            }
        }
    }

    fun remover(moeda: HistoricoMoeda) {

        viewModelScope.launch(Dispatchers.IO) {
            val resultado = moedaRepository.remover(moeda)
            withContext(Dispatchers.Main) {
                resultadoOperacao.postValue(resultado)
            }
        }
    }

}