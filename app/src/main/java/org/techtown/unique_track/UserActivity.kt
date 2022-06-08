package org.techtown.unique_track

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private val REQ_SELECT_IMG=200
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        auth = Firebase.auth

        //로그인한 사용자 체크
        checkCurrentUser()
        //사용자 정보 확인
        getUserProfile()

        //MainActivity창으로
        val home_button =findViewById<Button>(R.id.home_button)
        home_button.setOnClickListener{
            startActivity(Intent(this@UserActivity,MainActivity::class.java))
        }

        //프로필 사진 수정
        val editPicbutton=findViewById<Button>(R.id.picEdit_button)
        editPicbutton.setOnClickListener {
            openGallery()
        }

        //이름 수정
        val editNamebutton=findViewById<Button>(R.id.nameEdit_button)
        editNamebutton.setOnClickListener {
            var editTextPersonName=EditText(this)
            var alertDialog=AlertDialog.Builder(this)
            alertDialog.setTitle("이름 변경")
                .setMessage(" ")
                .setView(editTextPersonName)
                .setPositiveButton("변경",{dialogInterface, i-> changeUserName(editTextPersonName.text.toString()) })
                .setNegativeButton("취소",{dialogInterface, i-> dialogInterface.dismiss()})
                .show()
        }

        //로그아웃
        val logoutbutton = findViewById<Button>(R.id.logout_button)
        logoutbutton.setOnClickListener {
            // 로그인 화면으로
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("logout",true)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth?.signOut()
            googleSignInClient?.signOut()
        }

        //비밀번호 재설정
        val changepwdbutton = findViewById<Button>(R.id.changepwd_button)
        changepwdbutton.setOnClickListener {
            var editTextNewPassword = EditText(this)
            editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("패스워드 변경")
            alertDialog.setMessage("변경하고 싶은 패스워드를 입력하세요")
            alertDialog.setView(editTextNewPassword)
            alertDialog.setPositiveButton("변경", {dialogInterface, i -> changePassword(editTextNewPassword.text.toString())})
            alertDialog.setNegativeButton("취소", {dialogInterface, i -> dialogInterface.dismiss() })
            alertDialog.show()
        }

        //비밀번호 재설정 이메일 보내기
        val changePWemail=findViewById<Button>(R.id.changePW_email)
        changePWemail.setOnClickListener{
            sendPasswordReset()
        }
    }

    // 현재 로그인한 사용자 가져오기
    private fun checkCurrentUser(){
        val user = Firebase.auth.currentUser
        if(user!=null){
            // User is signed in
        }else{
            //No user is signed in
            //UserActivity창 종료
            finish()
        }
    }

    // 사용자 정보 엑세스, 출력
    private fun getUserProfile(){
        val user= Firebase.auth.currentUser
        user?.let{
            // name, email address
            val name=user.displayName
            val email=user.email
            val photoUrl=user.photoUrl

            // 유저의 이메일이 verified한지 체크
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project
            //MainActivity에서 전역변수로 uid 선언
            uid=user.uid

            //사용자 정보 출력
            val user_photo=findViewById<ImageView>(R.id.user_photo)
            val user_name=findViewById<TextView>(R.id.userName_info)
            val user_email=findViewById<TextView>(R.id.userEmail_info)
            val user_id=findViewById<TextView>(R.id.uid_info)
            val email_verified=findViewById<TextView>(R.id.email_verified)

            Glide.with(this)
                .load(photoUrl)
                .into(user_photo)
            user_name.setText(name)
            user_email.setText(email)
            user_id.setText(uid)
            if(emailVerified){
                email_verified.setText("인증되었습니다.")
            }else{
                email_verified.setText("인증되지않았습니다.")
            }


        }
    }

    //비밀번호 변경
    fun changePassword(password:String){
        FirebaseAuth.getInstance().currentUser!!.updatePassword(password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()

            }
        }
    }

    // 이메일 전송 비밀번호 변경
    fun sendPasswordReset(){
        val user= Firebase.auth.currentUser
        val emailAddress=user!!.email

        Firebase.auth.sendPasswordResetEmail(emailAddress.toString())
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"이메일 전송",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"실패",Toast.LENGTH_SHORT).show()
                }
            }
    }

    //프로필 이름 변경, user창에 출력
    fun changeUserName(name:String){
        //DB에 이름 저장
        val user=Firebase.auth.currentUser
        val profileUpdates= userProfileChangeRequest {
            displayName=name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    //바뀐 이름 바로 user창에 보이게
                    user?.let{
                        val name=user.displayName
                        val user_name=findViewById<TextView>(R.id.userName_info)
                        user_name.setText(name)
                    }
                    Toast.makeText(this,"Profile Image update",Toast.LENGTH_SHORT).show()
                    //Get current user uid
                    val currentUser = Firebase.auth.currentUser
                    uid = currentUser?.uid
                    val notNullableID : String = uid!!
                    //Store user information in database
                    database = Firebase.database.reference
                    val username = user.displayName
                    val email = user.email
                    val user = RegisterActivity.User(username, email)
                    database.child("Owners").child(notNullableID).setValue(user)
                }
            }
    }

    //갤러리에서 사진 - 요청
    private fun openGallery(){
        val intent = Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,REQ_SELECT_IMG)
    }
    //갤러리 사진 - 결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            when(requestCode){
                REQ_SELECT_IMG->{
                    data?:return
                    val uri=data.data as Uri
                    updateProfileImg(uri)   //이미지 DB에 저장
                    setImgUri(uri)          //이미지 화면에 출력
                }
            }
        }else{
            Toast.makeText(this,"사진을 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
        }
    }
    //Uri이용한 이미지 셋
    private fun setImgUri(imgUri:Uri){
        val profileImg=findViewById<ImageView>(R.id.user_photo)
        imgUri.let{
            val bitmap: Bitmap
            if(Build.VERSION.SDK_INT<28){
                bitmap= MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    imgUri
                )
                profileImg.setImageBitmap(bitmap)
            }else{
                val source= ImageDecoder.createSource(this.contentResolver,imgUri)
                bitmap=ImageDecoder.decodeBitmap(source)
                profileImg.setImageBitmap(bitmap)
            }
        }
    }
    //프로필 이미지 DB에 업로드
    private fun updateProfileImg(pic_uri:Uri){
        val user=Firebase.auth.currentUser
        val profileUpdates= userProfileChangeRequest {
            photoUri=pic_uri
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Profile Image update",Toast.LENGTH_SHORT).show()
                }
            }
    }
}