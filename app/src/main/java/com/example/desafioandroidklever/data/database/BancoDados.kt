package com.example.desafioandroidklever.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.desafioandroidklever.data.database.dao.MoedaDAO
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.helper.Constantes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Named()
@Database(
    entities = [HistoricoMoeda::class],
    version = 1
)
abstract class BancoDados:RoomDatabase() {

    //DAOS
    abstract  val moedaDAO : MoedaDAO

    //Instanciar BD
    companion object{
        fun getInstance(context:Context):BancoDados{
            return Room.databaseBuilder(
                context,
                BancoDados::class.java,
                Constantes.NOME_DB
            ).build()
        }
    }
}