package org.techtown.unique_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

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
            val uid=user.uid

            //사용자 정보 출력
            val user_info=findViewById<TextView>(R.id.user_info)
            if(name!=null){
                user_info.setText("이름 : "+name+"\n\n이메일 : "+email+"\n\nUID : "+uid)
            }else{
                user_info.setText("이메일 : "+email+"\n\nUID : "+uid)
            }
        }
    }
    //소셜로그인 시 사용자 정보 엑세스
    private fun getProviderData() {
        val user = Firebase.auth.currentUser
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address
                val name = profile.displayName
                val email = profile.email
            }
            //소셜로그인 : 로그인 제공업체가 displayName 제공
        }
    }
}