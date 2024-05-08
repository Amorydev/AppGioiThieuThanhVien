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
import com.amory.doanthuctap.model.Teams
import com.amory.doanthuctap.databinding.LayoutTeamsBinding
import com.squareup.picasso.Picasso

class RvTeams(private val list:MutableList<Teams>):RecyclerView.Adapter<RvTeams.viewHolder>() {
    private lateinit var mcontext:Context
    inner class viewHolder(val binding:LayoutTeamsBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Teams){
            binding.txtThanhvien.text = "Thành viên ${adapterPosition+1}"
            binding.txtName.text = data.name
            binding.txtMssv.text = data.mssv
            binding.txtChucVu.text = data.chucvu
            Picasso.get().load(data.image_url).into(binding.profileImage)
            Picasso.get().load(data.image_url).into(binding.contrains)

            binding.imgFb.setOnClickListener {
                val linkFa = list[adapterPosition].link_fb
                if (!TextUtils.isEmpty(linkFa)) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkFa))
                    mcontext.startActivity(intent)
                }
            }

            binding.imgInsta.setOnClickListener {
                val linkInsta = list[adapterPosition].link_insta
                if (!TextUtils.isEmpty(linkInsta)) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkInsta))
                    mcontext.startActivity(intent)
                }
            }

            binding.imgGit.setOnClickListener {
                val linkGit = list[adapterPosition].link_git
                if (!TextUtils.isEmpty(linkGit)) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkGit))
                    mcontext.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        mcontext = parent.context
        val view = LayoutTeamsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnLongClickListener{
            val intent = Intent(mcontext,ChinhSuaActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mcontext.startActivity(intent)
            true
        }
    }

}