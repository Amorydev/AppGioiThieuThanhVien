package com.amory.doanthuctap.activities

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.amory.doanthuctap.R
import com.amory.doanthuctap.databinding.ActivityAddBinding
import com.amory.doanthuctap.model.TeamModel
import com.amory.doanthuctap.model.Teams
import com.amory.doanthuctap.model.Utlis
import com.amory.doanthuctap.retrofit.Api
import com.amory.doanthuctap.retrofit.retrofit
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    private var mediaPath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addThanhVien()
    }

    private fun addThanhVien() {
        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text!!.trim().toString()
            val mssv = binding.edtMssv.text!!.trim().toString()
            val chucvu = binding.edtChucvu.text!!.trim().toString()
            val link_fb = binding.edtFacebook.text!!.trim().toString()
            val link_insta = binding.edtInsta.text!!.trim().toString()
            val link_git = binding.edtGithub.text!!.trim().toString()
            var image_url = ""
            image_url = if (mediaPath.isEmpty()){
                "https://img.freepik.com/free-photo/androgynous-avatar-non-binary-queer-person_23-2151100248.jpg?t=st=1715161490~exp=1715165090~hmac=bc9eb9a846531202c462fd2833fabd0a0c2eab8e9d6e4698f44c1f646a0f6273&w=740"

            }else{
                mediaPath
            }

            if (name.isEmpty() && mssv.isEmpty()&& chucvu.isEmpty()&& link_fb.isEmpty()&& link_insta.isEmpty()&& link_git.isEmpty()){
                Toast.makeText(this,"Vui lòng nhập đủ dữ liệu",Toast.LENGTH_LONG).show()
            }else{
                val service = retrofit.retrofitInstance.create(Api::class.java)
                val call = service.addthanhvien(name,mssv,chucvu,link_fb,link_insta,link_git,image_url)
                call.enqueue(object : Callback<TeamModel> {
                    override fun onResponse(call: Call<TeamModel>, response: Response<TeamModel>) {
                        if (response.isSuccessful){
                            Toast.makeText(applicationContext,"Thêm thành viên thành công",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AddActivity,AdminActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<TeamModel>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
    }
    private fun getPath(uri: Uri): String {
        val result: String
        val cursor: Cursor? = contentResolver.query(uri, null, null, null)
        if (cursor == null) {
            result = uri.path.toString()
        } else {
            cursor.moveToFirst()
            val index: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(index)
            cursor.close()
        }
        return result
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaPath = data?.dataString.toString()
        uploadMultipleFiles()
        Picasso.get().load(mediaPath).into(binding.imgAdd)
        Log.d("Log", mediaPath)
    }

    private fun uploadMultipleFiles() {
        val uri: Uri = Uri.parse(mediaPath)
        val file = File(getPath(uri))
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val service = retrofit.retrofitInstance.create(Api::class.java)
        val call = service.uploadFile(fileToUpload, requestBody)
        call.enqueue(object : Callback<TeamModel> {
            override fun onResponse(call: Call<TeamModel>, response: Response<TeamModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "thành công", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TeamModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}