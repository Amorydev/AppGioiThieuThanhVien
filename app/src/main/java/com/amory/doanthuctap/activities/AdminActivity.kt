package com.amory.doanthuctap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.amory.doanthuctap.R
import com.amory.doanthuctap.adapter.RvAdmin
import com.amory.doanthuctap.adapter.RvTeams
import com.amory.doanthuctap.databinding.ActivityAdminBinding
import com.amory.doanthuctap.model.TeamModel
import com.amory.doanthuctap.model.Teams
import com.amory.doanthuctap.model.Utlis
import com.amory.doanthuctap.retrofit.Api
import com.amory.doanthuctap.retrofit.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getThanhVien()
        onClickAdd()
        onclickSignout()
    }

    private fun onclickSignout() {
        binding.btnSignout.setOnClickListener {
            Utlis.user_current = null
            val intent = Intent(this, SignInActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun onClickAdd() {
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getThanhVien() {
        val service = retrofit.retrofitInstance.create(Api::class.java)
        val call = service.getthanhvien()
        call.enqueue(object : Callback<TeamModel> {
            override fun onResponse(call: Call<TeamModel>, response: Response<TeamModel>) {
                if (response.isSuccessful) {

                    val list = response.body()?.result
                    val adapter = RvAdmin(list as MutableList<Teams>)
                    /*Toast.makeText(applicationContext,list.toString(),Toast.LENGTH_SHORT).show()*/
                    binding.rvTeams.adapter = adapter
                    binding.rvTeams.layoutManager = LinearLayoutManager(
                        this@AdminActivity,
                        LinearLayoutManager.VERTICAL, false
                    )
                }
            }

            override fun onFailure(call: Call<TeamModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}