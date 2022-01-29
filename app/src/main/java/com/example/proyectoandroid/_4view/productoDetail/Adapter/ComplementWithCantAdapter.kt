package com.example.proyectoandroid._4view.productoDetail.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.ComplementosEntity
import com.example.proyectoandroid.databinding.AddByCantBinding
import com.example.proyectoandroid.databinding.CheckBoxItemBinding

class ComplementWithCantAdapter(
    private var complementos: List<ComplementosEntity>,
    private val adaptadorComplementwithCantInterface: AdaptadorComplementwithCantInterface
) :RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface AdaptadorComplementwithCantInterface{
        fun onBtnAdd(item: ComplementosEntity)
        fun onBtnDecrease(item: ComplementosEntity)

    }

    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = AddByCantBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = AddWithCantViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return complementos.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is AddWithCantViewHolder -> {
                holder.bind(complementos[position])
            }
        }
    }

    private inner class AddWithCantViewHolder(val binding: AddByCantBinding) :
        BaseViewHolder<ComplementosEntity>(binding.root) {
        override fun bind(item: ComplementosEntity) {

            binding.tvNombreDescripcion.text = item.nom_complemento
            if(item.precio_complemento!=0.0){
                binding.tvPrecio.text = "+ S/. ${item.precio_complemento}"
            }

            binding.btnAumentar.setOnClickListener {
                var cantComprar:Int=Integer.parseInt(binding.tvCantComprar.text.toString())
                binding.tvCantComprar.text="${cantComprar+1}"
                binding.btnDisminuir.visibility = View.VISIBLE
                binding.tvCantComprar.visibility = View.VISIBLE
                adaptadorComplementwithCantInterface.onBtnAdd(item)
            }

            binding.btnDisminuir.setOnClickListener {
                var cantComprar:Int=Integer.parseInt(binding.tvCantComprar.text.toString())
                binding.tvCantComprar.text="${cantComprar-1}"
                if(cantComprar-1<=0){
                    binding.btnDisminuir.visibility = View.GONE
                    binding.tvCantComprar.visibility = View.GONE
                }
                adaptadorComplementwithCantInterface.onBtnDecrease(item)
            }

        }

    }
}