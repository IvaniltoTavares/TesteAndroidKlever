package com.example.desafioandroidklever.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda

@Dao
interface MoedaDAO {
    @Insert
    suspend fun salvar(moeda: HistoricoMoeda):Long// -1 caso der errado. ou >0 se der certo

    //retorna numero de linhas afetadas
    @Delete
    suspend fun remover(moeda: HistoricoMoeda):Int

    //retorna numero de linhas afetadas
    @Update
    suspend fun atualizar(moeda: HistoricoMoeda): Int

    @Query("SELECT * FROM historico_moedas")
    suspend fun listar(): List<HistoricoMoeda>

}