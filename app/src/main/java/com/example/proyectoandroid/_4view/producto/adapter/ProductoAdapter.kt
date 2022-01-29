package com.example.proyectoandroid._4view.producto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.databinding.ProductItemBinding
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.ProductoEntity

class ProductoAdapter(
    private var productos: List<ProductoEntity>,
    private val adaptadorProductoInterface: AdaptadorProductoInterface
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface AdaptadorProductoInterface{
        fun onButtomAddToCar(item: ProductoEntity)
    }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = ProductItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = ProductViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(productos[position])
            }
        }
    }

    private inner class ProductViewHolder(val binding: ProductItemBinding) :
        BaseViewHolder<ProductoEntity>(binding.root) {
        override fun bind(item: ProductoEntity) {
            Glide.with(mContext)
                .load(item.img_prod)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            binding.tvTitulo.text = item.nom_prod
            binding.tvDescripcion.text = item.descrip_prod
            binding.tvPrecio.text = "S/. ${item.precio_final_prod}"

            binding.btnAgregar.setOnClickListener {
                adaptadorProductoInterface.onButtomAddToCar(item)
            }
        }

    }
}