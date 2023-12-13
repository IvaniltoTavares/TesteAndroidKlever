package com.example.desafioandroidklever.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.desafioandroidklever.R
import com.example.desafioandroidklever.data.database.entity.HistoricoMoeda
import com.example.desafioandroidklever.data.remote.model.ResultadoBlockchainAPIItem
import com.example.desafioandroidklever.databinding.ItemMoedaBinding
import kotlin.random.Random

class MoedaAdapter : Adapter<MoedaAdapter.MoedaViewHolder>(){

    private var listaMoedas = mutableListOf<HistoricoMoeda>()
    private lateinit var onClickDeletar:(HistoricoMoeda)-> Unit
    private lateinit var onClickAtualizar:(HistoricoMoeda)-> Unit

    fun  configurarCliqueDeletar(acaoDeletar:(HistoricoMoeda)-> Unit ){
        onClickDeletar = acaoDeletar
    }
    fun  configurarCliqueAtualizar(acaoAtualizar : (HistoricoMoeda)-> Unit){
        onClickAtualizar = acaoAtualizar
    }

    fun configurarLista(lista: List<HistoricoMoeda>){
        listaMoedas = lista.toMutableList()
        notifyDataSetChanged()
    }

    inner class MoedaViewHolder(itenView:ItemMoedaBinding)
        : ViewHolder(itenView.root){
            private var binding:ItemMoedaBinding
            init {
                binding = itenView
            }
            fun bind( historicoMoeda: HistoricoMoeda ){
                binding.textTitulo.text = historicoMoeda.titulo
                binding.textDescricao.text = historicoMoeda.descricao
                binding.textCategoria.text = "${historicoMoeda.idMoeda} - ${historicoMoeda.precoMoeda}"

                //configurar eventos de click
                binding.cardViewItem.setOnClickListener{
                    onClickAtualizar(historicoMoeda)
                }
                binding.btnRemover.setOnClickListener {
                    onClickDeletar(historicoMoeda)
                }

                //cor cardview
                binding.cardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        coresAleatorias()
                    )
                )
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoedaViewHolder {
        val itenView = ItemMoedaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoedaViewHolder(itenView)
    }

    override fun onBindViewHolder(holder: MoedaViewHolder, position: Int) {
       val moeda = listaMoedas[position]
        holder.bind(moeda)
    }
    override fun getItemCount(): Int {
       return listaMoedas.size
    }
    fun coresAleatorias(): Int{
        val lista = listOf(
            R.color.laranja,R.color.azul,R.color.rosa,R.color.verde,R.color.roxo
        )
        val numerAleatorio = Random.nextInt(lista.size)
        return lista[numerAleatorio]
    }
}