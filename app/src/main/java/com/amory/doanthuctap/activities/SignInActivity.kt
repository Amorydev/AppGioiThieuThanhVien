package com.amory.doanthuctap.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.amory.doanthuctap.R
import com.amory.doanthuctap.databinding.ActivitySignInBinding
import com.amory.doanthuctap.model.UserModel
import com.amory.doanthuctap.model.Utlis
import com.amory.doanthuctap.retrofit.Api
import com.amory.doanthuctap.retrofit.retrofit
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickDangNhap()
        binding.tvNotAccount.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    public override fun onStart() {
        super.onStart()
    }
    private fun onClickDangNhap() {

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passET.text.toString().trim()
            dangNhap(email, password)
        }

    }
    private fun dangNhap(email: String, password: String) {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && !Patterns.EMAIL_ADDRESS.matcher(
                email
            ).matches()
        ) {
            Toast.makeText(
                applicationContext,
                "Vui lòng nhập đầy đủ email và password",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            val service = retrofit.retrofitInstance.create(Api::class.java)
            val call = service.dangnhaptaikhoan(email, password)
            call.enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        if (response.body()?.result?.isNotEmpty()!!) {
                            Toast.makeText(
                                applicationContext,
                                "Đăng nhập thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            Utlis.user_current = response.body()?.result?.get(0)!!
                               if (response.body()?.result?.get(0)!!.role == 1){
                                   val intent = Intent(this@SignInActivity, AdminActivity::class.java)
                                   startActivity(intent)
                                   finish()
                               }else{
                                   val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                   startActivity(intent)
                                   finish()
                               }
                        }
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Đăng nhập không thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    t.printStackTrace()
                }
            })
        }
    }
}