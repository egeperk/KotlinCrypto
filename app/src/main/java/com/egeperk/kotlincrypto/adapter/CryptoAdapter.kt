package com.egeperk.kotlincrypto.adapter

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egeperk.kotlincrypto.R
import com.egeperk.kotlincrypto.adapter.CryptoAdapter.*
import com.egeperk.kotlincrypto.model.CryptoModel
import kotlinx.android.synthetic.main.recycler_row.view.*

class CryptoAdapter (val cryptoList : ArrayList<CryptoModel>, val  listener : Listener) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }


    private val colors : Array<String> = arrayOf("#4f6f57","#4485c6","#f76161","#3f3431","#bed55a","#a8c3bc")

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {

        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6] ))
            itemView.coinText.text = cryptoModel.currency
            itemView.priceText.text = cryptoModel.price

        }
    }

    //bindingsiz oluşturma şekli
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(cryptoList[position],colors,position,listener)

    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}