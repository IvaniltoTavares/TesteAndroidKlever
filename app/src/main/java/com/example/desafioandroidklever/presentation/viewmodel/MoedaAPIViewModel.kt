package com.example.desafioandroidklever.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.data.remote.repository.MoedasRepositoryImpl
import com.example.desafioandroidklever.presentation.viewmodel.resultado.ResultadoOperacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoedaAPIViewModel @Inject constructor(
    private val moedasRepository: MoedasRepositoryImpl
):ViewModel() {

    private val listaCategorias = MutableLiveData<List<ResultadoBlockchainAPIItem>>()
    val lista: MutableLiveData<List<ResultadoBlockchainAPIItem>>
        get() = listaCategorias

    fun listar (){
        viewModelScope.launch(Dispatchers.IO) {
            val lista = moedasRepository.listar()
            listaCategorias.postValue(lista)
        }
    }
}