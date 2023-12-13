package com.example.desafioandroidklever.di

import android.content.Context
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import com.example.desafioandroidklever.data.database.dao.MoedaDAO
import com.example.desafioandroidklever.data.database.BancoDados
import com.example.desafioandroidklever.data.database.repository.MoedaRepository
import com.example.desafioandroidklever.data.remote.repository.MoedasRepositoryImpl
import com.example.desafioandroidklever.data.remote.service.MoedasAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideMoedaRepository( moedaDAO: MoedaDAO): MoedaRepository {
        return MoedaRepository(moedaDAO)
    }
    @Provides
    fun provideMoedaDAO(bancoDados: BancoDados): MoedaDAO {
       return bancoDados.moedaDAO
    }
    @Provides
    fun provideBancoDados(@ApplicationContext context: Context):BancoDados{
        return BancoDados.getInstance(context)
    }

    //Dependências de API
    @Provides
    fun provideMoedasAPIService() : MoedasAPIService {
        return Retrofit.Builder()
            .baseUrl("https://api.blockchain.com/v3/exchange/")//
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( MoedasAPIService::class.java )
    }

    @Provides
    fun provideMoedasRepositoryAPI(
        moedasAPIService: MoedasAPIService
    ): MoedasRepositoryImpl {
        return MoedasRepositoryImpl( moedasAPIService )
    }


}