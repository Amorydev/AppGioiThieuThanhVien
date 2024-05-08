package com.amory.doanthuctap.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amory.doanthuctap.activities.ChinhSuaActivity
import com.amory.doanthuctap.databinding.LayoutAdminQuanlyBinding
import com.amory.doanthuctap.databinding.LayoutTeamsBinding
import com.amory.doanthuctap.model.Teams
import com.squareup.picasso.Picasso

class RvAdmin(private val list:MutableList<Teams>): RecyclerView.Adapter<RvAdmin.viewHolder>() {
    private lateinit var mcontext: Context
    inner class viewHolder(val binding: LayoutAdminQuanlyBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Teams){
            binding.txtName.text = data.name
            binding.txtMssv.text = data.mssv
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        mcontext = parent.context
        val view = LayoutAdminQuanlyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(list[position])
    }

}