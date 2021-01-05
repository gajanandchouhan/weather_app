package com.gc.weatherapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gc.weatherapp.R
import com.gc.weatherapp.common.ItemClickListener
import com.gc.weatherapp.data.AddressInfo
import com.gc.weatherapp.databinding.ItemHomeBinding

class HomeAdapter(
    private val list: MutableList<AddressInfo>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(private val itemHomeBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {
        fun bind(addressInfo: AddressInfo) {
            itemHomeBinding.addressInfo = addressInfo
            itemHomeBinding.executePendingBindings()
        }


    }

    private fun removeCity(adapterPosition: Int) {
        list.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHomeBinding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemHomeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { itemClickListener.onItemClick(holder.adapterPosition) }
        holder.itemView.findViewById<ImageView>(R.id.imgClose)
            .setOnClickListener { removeCity(holder.adapterPosition) }
    }

    override fun getItemCount() = list.size
}