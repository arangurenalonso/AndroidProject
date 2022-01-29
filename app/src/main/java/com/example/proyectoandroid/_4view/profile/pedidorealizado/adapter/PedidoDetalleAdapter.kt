package com.example.proyectoandroid._4view.profile.pedidorealizado.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.databinding.CategoryItemBinding
import com.example.proyectoandroid._0core.BaseViewHolder
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity
import com.example.proyectoandroid.databinding.DetallePedidoRealizadoItemBinding
import com.example.proyectoandroid.databinding.PedidoRealizadoItemBinding

class PedidoDetalleAdapter(
    private var orderDetail: List <OrderDetailEntity>,
    private var orderDetailComplement: ArrayList <OrderDetailComplementoEntity>
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {


    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = DetallePedidoRealizadoItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = pedidoDetalleAdapterViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return orderDetail.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is pedidoDetalleAdapterViewHolder -> {
                holder.bind(orderDetail[position])
            }
        }
    }

    private inner class pedidoDetalleAdapterViewHolder(val binding: DetallePedidoRealizadoItemBinding) :
        BaseViewHolder<OrderDetailEntity>(binding.root) {
        override fun bind(item: OrderDetailEntity) {

            binding.tvCantidadComprada.text = item.cant.toString()
            binding.tvNombreProducto.text = item.nom_prod

            val complemento_producto=orderDetailComplement.filter { it.id_order_producto==item.id_order_producto }
            var sub_total_producto=0.0
            var descripcion=""
            var costo_complemento=0.0
            if(complemento_producto.isNotEmpty()){
                descripcion="Complementos: \n"
                complemento_producto.forEachIndexed {
                        index, complemento ->
                    descripcion+="    ${complemento.cantidad}X  -   ${complemento.nom_complemento} \n"
                }

                costo_complemento=complemento_producto.sumOf{ it.precio_complemento*it.cantidad}

            }else{
                descripcion="No se han agregado complementos"
            }
            sub_total_producto=(costo_complemento + item.precio_final_prod)*item.cant
            binding.tvDescripcion.text = descripcion
            binding.tvSubTotal.text = "S/.${String.format("%.2f", sub_total_producto)}"




        }

    }
}