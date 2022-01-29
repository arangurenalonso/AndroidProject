package com.example.proyectoandroid._4view.categoria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.databinding.CategoryItemBinding
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity

class CategoryAdapter(
    private var categoriesProducto: List<CategoriaProductoEntity>,
    private val adaptadorCategoryProductoInterface: AdaptadorCategoryProductoInterface
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface AdaptadorCategoryProductoInterface {
        fun onCategoryClick(categoriaProductoEntity: CategoriaProductoEntity)
    }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = CategoryProductoViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return categoriesProducto.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CategoryProductoViewHolder -> {
                holder.bind(categoriesProducto[position])
            }
        }
    }

    private inner class CategoryProductoViewHolder(val binding: CategoryItemBinding) :
        BaseViewHolder<CategoriaProductoEntity>(binding.root) {
        override fun bind(item: CategoriaProductoEntity) {
            Glide.with(mContext)
                .load(item.img_categ)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            binding.textView.text = item.nombre_categ
            binding.root.setOnClickListener {
                adaptadorCategoryProductoInterface.onCategoryClick(item)
            }
        }

    }
}