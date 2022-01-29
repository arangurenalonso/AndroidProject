package com.example.proyectoandroid._4view.productoDetail.Adapter.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.databinding.AdicionalEnsaladaBinding
import com.example.proyectoandroid._0core.BaseConcatHolder
import com.example.proyectoandroid._4view.productoDetail.Adapter.ComplementCheckAdapter

class EnsaladaConcatAdapter(private val complementAdapter: ComplementCheckAdapter) : RecyclerView.Adapter<BaseConcatHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding=
            AdicionalEnsaladaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder){
            is ConcatViewHolder ->holder.bind(complementAdapter)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
    private inner class ConcatViewHolder(val binding:AdicionalEnsaladaBinding)
        : BaseConcatHolder<ComplementCheckAdapter>(binding.root){
        override fun bind(adapter: ComplementCheckAdapter) {
            binding.rvEnsalada.adapter=adapter
        }
    }
}