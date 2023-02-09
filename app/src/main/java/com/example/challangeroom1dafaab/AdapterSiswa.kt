package com.example.challangeroom1dafaab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.siswa_adapter.view.*

class AdapterSiswa(private val sisaw: ArrayList<tbsiswa>,private val listener : OnAdapterListener):
    RecyclerView.Adapter<AdapterSiswa.ViewHolder>() {

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.siswa_adapter,parent,false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val siswa = sisaw[position]
        holder.view.text_title.text = siswa.nama
        holder.view.text_title.setOnClickListener {
            listener.onClick(siswa)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(siswa)
        }
     //   holder.view.icon_delete.setOnClickListener {
      //      listener.onDelete(siswa)
    //    }

    }
    override fun getItemCount(): Int = sisaw.size

    fun setData(list: List<tbsiswa>) {
        sisaw.clear()
        sisaw.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(siswa: tbsiswa)
        fun onUpdate(siswa: tbsiswa)
      //  fun onDelete(siswa: tbsiswa)
    }
}