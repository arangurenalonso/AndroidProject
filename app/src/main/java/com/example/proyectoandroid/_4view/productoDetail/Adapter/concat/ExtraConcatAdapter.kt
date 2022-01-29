package com.example.proyectoandroid._4view.productoDetail.Adapter.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.databinding.AdicionalExtraBinding
import com.example.proyectoandroid._0core.BaseConcatHolder
import com.example.proyectoandroid._4view.productoDetail.Adapter.ComplementCheckAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.ComplementWithCantAdapter

class ExtraConcatAdapter(private val complementWithCantAdapter: ComplementWithCantAdapter) : RecyclerView.Adapter<BaseConcatHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding=
            AdicionalExtraBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder){
            is ConcatViewHolder ->holder.bind(complementWithCantAdapter)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
    private inner class ConcatViewHolder(val binding: AdicionalExtraBinding)
        : BaseConcatHolder<ComplementWithCantAdapter>(binding.root){
        override fun bind(adapter: ComplementWithCantAdapter) {
            binding.rvExtra.adapter=adapter
        }
    }
}