package com.example.proyectoandroid._4view.productoDetail.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.databinding.TitleDetailProductItemBinding
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.ProductoEntity


class DetailTitleAdapter(
    private var producto: ProductoEntity
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = TitleDetailProductItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = EncabezadoDetalleProductoViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is EncabezadoDetalleProductoViewHolder -> {
                holder.bind(producto)
            }
        }
    }

    private inner class EncabezadoDetalleProductoViewHolder(val binding: TitleDetailProductItemBinding) :
        BaseViewHolder<ProductoEntity>(binding.root) {
        override fun bind(item: ProductoEntity) {

            Glide.with(mContext)
                .load(item.img_prod)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgTitle)

            binding.tvTitle.text = item.nom_prod
            binding.tvDescripcion.text = item.descrip_prod
            binding.tvPrice.text = "S/. ${item.precio_final_prod}"

        }

    }
}