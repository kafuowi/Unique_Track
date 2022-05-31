package org.techtown.unique_track

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

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

        //로그아웃
        val logoutbutton = findViewById<Button>(R.id.logout_button)
        logoutbutton.setOnClickListener {
            // 로그인 화면으로
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth?.signOut()
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
            alertDialog.setPositiveButton("변경", {dialogInterface, i -> changePassword(editTextNewPassword.text.toString()) })
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

            // 유저의 이메일이 verified한지 체크
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project
            //MainActivity에서 전역변수로 uid 선언
            uid=user.uid

            //사용자 정보 출력
            val user_name=findViewById<TextView>(R.id.userName_info)
            val user_email=findViewById<TextView>(R.id.userEmail_info)
            val user_id=findViewById<TextView>(R.id.uid_info)
            val email_verified=findViewById<TextView>(R.id.email_verified)
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
}