package org.techtown.unique_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        //로그인한 사용자 체크
        checkCurrentUser()
        //사용자 정보 확인
        getUserProfile()

        //MainActivity창으로
        val home_button =findViewById<Button>(R.id.home_button)
        home_button.setOnClickListener{
            startActivity(Intent(this@UserActivity,MainActivity::class.java))
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
            //val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project
            //MainActivity에서 전역변수로 uid 선언
            uid=user.uid

            //사용자 정보 출력
            val user_name=findViewById<TextView>(R.id.userName_info)
            val user_email=findViewById<TextView>(R.id.userEmail_info)
            val user_id=findViewById<TextView>(R.id.uid_info)
            val login_method=findViewById<TextView>(R.id.login_method)
            user_email.setText(email)
            user_id.setText(uid)
            if(name!=null){
                user_name.setText(name)
                login_method.setText("Google Login")
            }else{
                user_name.setText(" ")
                login_method.setText("Email Login")
            }
        }
    }
}