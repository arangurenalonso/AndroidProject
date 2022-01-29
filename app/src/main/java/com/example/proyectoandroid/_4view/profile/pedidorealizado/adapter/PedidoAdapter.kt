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
import com.example.proyectoandroid._1data.model.order.OrderEntity
import com.example.proyectoandroid.databinding.PedidoRealizadoItemBinding

class PedidoAdapter(
    private var listaOrdenes: List<OrderEntity>,
    private val adaptadorOrderInterface: AdaptadorOrderInterface
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface AdaptadorOrderInterface {
        fun onOrderClick(orderEntity: OrderEntity)
    }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        mContext = parent.context
        val itemBinding = PedidoRealizadoItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = OrderViewHolder(itemBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return listaOrdenes.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is OrderViewHolder -> {
                holder.bind(listaOrdenes[position])
            }
        }
    }

    private inner class OrderViewHolder(val binding: PedidoRealizadoItemBinding) :
        BaseViewHolder<OrderEntity>(binding.root) {
        override fun bind(item: OrderEntity) {
            binding.tvFechaPedido.text = "${item.date_join}"
            binding.tvOrderUUID.text = "${item.orderUUID}"
            binding.tvDireccion.text = "${item.direccion}"

            binding.tvTotal.text = "S/.${String.format("%.2f", item.total_orden)}"



            binding.root.setOnClickListener {
                adaptadorOrderInterface.onOrderClick(item)
            }
        }

    }
}