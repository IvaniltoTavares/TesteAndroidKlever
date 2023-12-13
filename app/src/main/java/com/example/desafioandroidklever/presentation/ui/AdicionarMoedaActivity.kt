package com.example.desafioandroidklever.presentation.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.databinding.ActivityAdicionarMoedaBinding
import com.example.desafioandroidklever.presentation.viewmodel.MoedaAPIViewModel
import com.example.desafioandroidklever.presentation.viewmodel.HistoricoMoedaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdicionarMoedaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAdicionarMoedaBinding.inflate(layoutInflater)
    }

    private val historicoMoedaViewModel: HistoricoMoedaViewModel by viewModels()
    private val moedaAPIViewModel: MoedaAPIViewModel by viewModels()
    private lateinit var adapterSpinner:ArrayAdapter<String>
    private var listaCategorias= listOf<ResultadoBlockchainAPIItem>()
    private var moeda: HistoricoMoeda? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarUI()
        inicializarObservables()
        inicializarListeners()
    }

    private fun inicializarUI() {
        with (binding){

            //recuperar anotação caso seja edição
            val bundle = intent.extras
            if(bundle!= null){//editando moeda
                moeda = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable("moeda", HistoricoMoeda::class.java)
                }else{
                    bundle.getParcelable("moeda")
                }

                if (moeda!=null){

                    binding.editTitulo.setText(moeda!!.titulo)
                    binding.editDescricao.setText(moeda!!.descricao)

                }
            }

            adapterSpinner = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf()
            )
            spinnerCategoria.adapter = adapterSpinner
        }
    }

    override fun onStart() {
        super.onStart()
        moedaAPIViewModel.listar()
    }

    private fun inicializarObservables() {

        historicoMoedaViewModel.resultado.observe(this){ resultadoOperacao ->
            if (resultadoOperacao.sucesso){

                /*
                * SE RETORNAR SUCESSO DO OBSERVAVEL TANTO NO METODO SALVAR COMO NO ATUALIZAR ELE JOGA USUARIO AQUI PARA A MAIN ACTIVITY (TELA PRINCIPAL) PORQUE AMBOS METODOS USAM O ESTADO DO OBSERVAVEL NA VIEWMODEL, TANTO O SALVAR COMO O ATUALIZAR
                * */

                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                )
                finish()
                Toast.makeText(this, resultadoOperacao.mensagem, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, resultadoOperacao.mensagem, Toast.LENGTH_SHORT).show()
            }
        }

        //observavel para lista
        moedaAPIViewModel.lista.observe(this){ listaRetorno->

            //configurar lista categoria
            listaCategorias = listaRetorno

            val listaSpinner = mutableListOf("Selecione uma MOEDA")
             listaSpinner.addAll(listaRetorno.map { "${it.symbol} - (${it.last_trade_price})" } )

            adapterSpinner.clear()
            adapterSpinner.addAll(listaSpinner)
            //seleciona item do spiner em caso de edição
            var indiceItemSelecionado = 0

            //atualizar o item ja salvo
            if(moeda!= null){
                val idCategoriaMoeda = moeda!!.idMoeda
                var contador = 0
                listaCategorias.forEach {categoria ->
                    if(categoria.symbol == moeda!!.idMoeda){
                        indiceItemSelecionado = contador+1
                        return@forEach

                    }
                    contador++

                }
            }
            binding.spinnerCategoria.setSelection(indiceItemSelecionado)

            /*listaCategoria.forEach {
                Log.i("debug_minhas", "categoria: ${it.titulo} ")
            }*/


        }
    }

    private fun inicializarListeners() {
        with(binding){

            btnSalvarMoeda.setOnClickListener {

                val posicaoItemSelecionoado = spinnerCategoria.selectedItemPosition
                if(posicaoItemSelecionoado>0){

                    //lista Spinner
                    val itemSelecionado = listaCategorias[posicaoItemSelecionoado-1]

                    val titulo = editTitulo.text.toString()
                    val moedaAPISimbolo = itemSelecionado.symbol
                    val moedaAPIPreco = itemSelecionado.last_trade_price
                    val descricao = editDescricao.text.toString()


                    if (moeda!=null){

                        historicoMoedaViewModel.atualizar(
                            HistoricoMoeda(
                                moeda!!.idHistoricoMoeda,
                                moedaAPISimbolo,
                                moedaAPIPreco,
                                titulo,
                                descricao
                            )
                        )

                    }else{
                        historicoMoedaViewModel.salvar(
                            HistoricoMoeda(
                                0,
                                moedaAPISimbolo,
                                moedaAPIPreco,
                                titulo,
                                descricao
                            )
                        )
                    }

                }else{
                    Toast.makeText(applicationContext, "Selecione uma Categoria", Toast.LENGTH_SHORT).show()

                }


            }
        }
    }
}