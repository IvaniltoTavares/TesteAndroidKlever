package com.example.desafioandroidklever.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.desafioandroidklever.R
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.databinding.ActivityMainBinding
import com.example.desafioandroidklever.presentation.MoedaAdapter
import com.example.desafioandroidklever.presentation.viewmodel.HistoricoMoedaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val historicoMoedaViewModel: HistoricoMoedaViewModel by viewModels()
    private lateinit var moedaAdapter: MoedaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciarUI()
        iniciarListeners()
        iniciarObservable()

    }

    private fun iniciarObservable() {
        historicoMoedaViewModel.lista.observe(this) { listaMoedaCategoria ->
            moedaAdapter.configurarLista(listaMoedaCategoria)
        }
        historicoMoedaViewModel.resultado.observe(this) { resultadoOperacao ->

            val mensagem = resultadoOperacao.mensagem
            if (resultadoOperacao.sucesso) {
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        historicoMoedaViewModel.listar()
    }

    private fun iniciarActionBar() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_principal, menu)
                val menuPesquisa = menu.findItem(R.id.item_pesquisa)
                val searchView = menuPesquisa.actionView as SearchView

                searchView.queryHint = "Digite sua pesquisa"

                searchView.setOnCloseListener {
                    println("Usuario Fechou")
                    return@setOnCloseListener true
                }

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        println("submit: ($query)")
                        return true
                    }//faz a pesquisa quando vc clica no botao "pesquisa" no teclado

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            //historicoMoedaViewModel.pesquisar(newText)

                        }
                        return true
                    }//faz a pesquisa quando vc vai digitando

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_sair -> {
                        true
                    }

                    R.id.item_pesquisa -> {
                        true
                    }

                    else -> true
                }

            }

        })
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }*/


    private fun iniciarUI() {
        iniciarActionBar()

        //inicar recyclerview
        moedaAdapter = MoedaAdapter()

        //adciona eventos de click
        moedaAdapter.configurarCliqueDeletar { moeda ->
            historicoMoedaViewModel.remover(moeda)
            historicoMoedaViewModel.listar()
        }
        moedaAdapter.configurarCliqueAtualizar { moeda ->
            val intent = Intent(this, AdicionarMoedaActivity::class.java)
            intent.putExtra("moeda", moeda)
            startActivity(intent)
        }

        binding.rvMoedas.adapter = moedaAdapter
        binding.rvMoedas.layoutManager = StaggeredGridLayoutManager(
            2, LinearLayoutManager.VERTICAL
        )
    }


    private fun iniciarListeners() {

        with(binding) {
            fabAdicionar.setOnClickListener {
                startActivity(
                    Intent(applicationContext, AdicionarMoedaActivity::class.java)
                )
            }
        }

    }
}