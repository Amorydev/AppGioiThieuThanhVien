package com.amory.doanthuctap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.amory.doanthuctap.R
import com.amory.doanthuctap.databinding.ActivitySignUpBinding
import com.amory.doanthuctap.model.UserModel
import com.amory.doanthuctap.retrofit.Api
import com.amory.doanthuctap.retrofit.retrofit
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDangki.setOnClickListener {
            kiemTraDuLieu()
        }

    }
    private fun kiemTraDuLieu() {
        val email = binding.emailEt.text?.trim().toString()
        val password = binding.passET.text?.trim().toString()
        val repassword = binding.confirmPassEt.text?.trim().toString()
        val mssv = binding.mssvEdt.text?.trim().toString()

        if (email.isEmpty() && password.isEmpty()
            && repassword.isEmpty()
            &&!email.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } && email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            postData(
                email,
                password,
                repassword,
                mssv
            )
        }
    }

    private fun postData(
        email: String,
        password: String,
        repassword: String,
        mssv:String
    ) {
            if (password == repassword) {
                val service = retrofit.retrofitInstance.create(Api::class.java)
                val call =
                    service.dangkitaikhoan(email,password,mssv)
                call.enqueue(object : Callback<UserModel> {
                    override fun onResponse(
                        call: Call<UserModel>,
                        response: Response<UserModel>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Đăng kí thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                                val intent =
                                    Intent(this@SignUpActivity, SignInActivity::class.java)
                                startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Đăng kí không thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("amory_gioithieu",t.toString())
                        t.printStackTrace()
                    }
                })
            } else {
                Toast.makeText(this, "Password không trùng khớp", Toast.LENGTH_SHORT).show()
            }
    }
}