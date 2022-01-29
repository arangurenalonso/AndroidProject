package com.example.proyectoandroid._4view.carrito.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import com.example.proyectoandroid.databinding.CarritoItemBinding


class CarritoAdapter(
    private var listOfItemCarrito: List<CarritoEntity>,
    private var carritoJoinComplemento: List<CarritoJoinComplemento>,
    private val adaptadorCarritoInterface: AdaptadorCarritoInterface
) : RecyclerView.Adapter<BaseViewHolder<*>>() {


    interface AdaptadorCarritoInterface {
        fun onClickDisminuir(itemCarrito: CarritoEntity)
        fun onClickAumentar(itemCarrito: CarritoEntity)

    }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = CarritoItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = CarritoViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return listOfItemCarrito.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CarritoViewHolder -> {
                holder.bind(listOfItemCarrito[position])
            }
        }
    }


    private inner class CarritoViewHolder(val binding: CarritoItemBinding) :
        BaseViewHolder<CarritoEntity>(binding.root) {

        override fun bind(itemCarrito: CarritoEntity) {
            colocar_valores(binding,itemCarrito)
            onClickLeerMas(binding)
            binding.btnAumentar.setOnClickListener {
                adaptadorCarritoInterface.onClickAumentar(itemCarrito)
            }
            binding.btnDisminuir.setOnClickListener {
                adaptadorCarritoInterface.onClickDisminuir(itemCarrito)
            }
        }
    }


    @SuppressLint("WrongConstant")
    private fun onClickLeerMas(binding: CarritoItemBinding){

        binding.tvLeerMas.setOnClickListener{
            if(binding.tvLeerMas.text=="leer menos"){
                binding.tvDescripcion.setMaxLines(1)
                binding.tvDescripcion.setEllipsize(TextUtils.TruncateAt.END)
                binding.tvLeerMas.text="leer mas"
                binding.layoutDescripcion.setOrientation(LinearLayoutManager.HORIZONTAL)
            }else{
                binding.tvDescripcion.setMaxLines(Integer.MAX_VALUE)
                binding.tvDescripcion.setEllipsize(null)
                binding.tvLeerMas.text="leer menos"
                binding.layoutDescripcion.setOrientation(LinearLayoutManager.VERTICAL)
            }
        }
    }
    private fun colocar_valores(binding: CarritoItemBinding,itemCarrito: CarritoEntity){
        var costoTotal=0.0
        var texto=""

        val cant_prod_comprar=itemCarrito.cant
        val nom_producto=itemCarrito.nom_prod
        val costo_producto=itemCarrito.precio_final_prod
        Glide.with(mContext)
            .load(itemCarrito.img_prod)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(binding.imgProducto)
        binding.tvNombreProducto.text=nom_producto
        binding.tvCantComprar.text=cant_prod_comprar.toString()
        if(itemCarrito.cant<=1){

            binding.btnDisminuir.text=""
        }else{
            binding.btnDisminuir.setCompoundDrawables(null,null,null,null)
            binding.btnDisminuir.text="-"
        }



        texto+="Detalle Producto\n"
        texto+=" > ${cant_prod_comprar} - ${nom_producto} - S/.${String.format("%.2f", costo_producto).toDouble()} \n"

        val listComplementIteamCarrito=carritoJoinComplemento.filter { it.id_carrito==itemCarrito.id_carrito }
        var costo_complemento=0.0
        texto+="Detalle Complemento\n"
        if(listComplementIteamCarrito.size>0){


            listComplementIteamCarrito.forEachIndexed { index, carritoJoinComplemento ->
                val cantCompl=carritoJoinComplemento.cantidad
                val nomCompl=carritoJoinComplemento.nom_complemento
                val subTotal=carritoJoinComplemento.precio_complemento*carritoJoinComplemento.cantidad
                val textSubTotal=if(subTotal<=0)"" else "- S/.${String.format("%.2f", subTotal).toDouble()}"
                texto+=" > ${cantCompl} - ${nomCompl} ${textSubTotal} \n"
            }

            costo_complemento=listComplementIteamCarrito.sumOf  { it.precio_complemento*it.cantidad }

        }else{
            texto+="No se han agregado complementos"
        }

        costoTotal=(costo_complemento+costo_producto)*itemCarrito.cant

        binding.tvPrecio.text="S/. ${String.format("%.2f", costoTotal).toDouble()}"
        binding.tvDescripcion.text=texto
    }

}