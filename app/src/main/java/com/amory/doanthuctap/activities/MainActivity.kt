package com.amory.doanthuctap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.amory.doanthuctap.R
import com.amory.doanthuctap.adapter.RvTeams
import com.amory.doanthuctap.databinding.ActivityMainBinding
import com.amory.doanthuctap.model.TeamModel
import com.amory.doanthuctap.model.Teams
import com.amory.doanthuctap.model.Utlis
import com.amory.doanthuctap.retrofit.Api
import com.amory.doanthuctap.retrofit.retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getThanhVien()
        onClickChinhSua()
        onclickSignout()
    }



    private fun onclickSignout() {
        binding.btnSignout.setOnClickListener {
            Utlis.user_current = null
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClickChinhSua() {
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this,ChinhSuaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getThanhVien() {
        val service = retrofit.retrofitInstance.create(Api::class.java)
        val call = service.getthanhvien()
        call.enqueue(object : Callback<TeamModel>{
            override fun onResponse(call: Call<TeamModel>, response: Response<TeamModel>) {
                if (response.isSuccessful){

                    val list = response.body()?.result
                    val adapter = RvTeams(list as MutableList<Teams>)
                    /*Toast.makeText(applicationContext,list.toString(),Toast.LENGTH_SHORT).show()*/
                    binding.rvTeams.adapter = adapter
                    binding.rvTeams.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                }
            }

            override fun onFailure(call: Call<TeamModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getThanhVien()
    }

}