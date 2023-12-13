package com.example.desafioandroidklever.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "historico_moedas")
@Parcelize
data class HistoricoMoeda(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_historico_moeda")
    val idHistoricoMoeda: Long,

    @ColumnInfo(name = "id_moeda")
    val idMoeda: String,

    @ColumnInfo(name = "preco_moeda")
    val precoMoeda: Double,

    val titulo: String,
    val descricao: String

) : Parcelable
// :java.io.Serializable
