package com.example.proyectoandroid._4view.productoDetail.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.databinding.CheckBoxItemBinding
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.ComplementosEntity

class ComplementCheckAdapter(
    private var complementos: List<ComplementosEntity>,
    private val adaptadorComplementInterface: AdaptadorComplementInterface
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface AdaptadorComplementInterface{
        fun onCheckCheckBox(item: ComplementosEntity)
        fun onUnCheckCheckBox(item: ComplementosEntity)
    }

    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = CheckBoxItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = CheckBoxViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return complementos.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CheckBoxViewHolder -> {
                holder.bind(complementos[position])
            }
        }
    }

    private inner class CheckBoxViewHolder(val binding: CheckBoxItemBinding) :
        BaseViewHolder<ComplementosEntity>(binding.root) {
        override fun bind(item: ComplementosEntity) {

            binding.tvNombreDescripcion.text = item.nom_complemento
            if(item.precio_complemento!=0.0){
                binding.tvPrecio.text = "+ S/. ${item.precio_complemento}"
            }


            binding.chkBox.setOnCheckedChangeListener {
                    buttonView, isChecked ->
                if (isChecked){

                    adaptadorComplementInterface.onCheckCheckBox(item)

                }else{

                    adaptadorComplementInterface.onUnCheckCheckBox(item)

                }
            }
        }

    }
}