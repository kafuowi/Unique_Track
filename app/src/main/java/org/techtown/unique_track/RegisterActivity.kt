package org.techtown.unique_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*

class RegisterActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_join)

        val join_button = findViewById<Button>(R.id.join_button)
        join_button.setOnClickListener {

            if(editTextTextPassword.text.toString()==editTextTextPassword_check.text.toString()){
                createAccount(editTextID.text.toString(),
                    editTextTextPassword.text.toString(), editTextTextUsername.text.toString())
            }else{
                Toast.makeText(this, "PW와 PW확인 불일치",Toast.LENGTH_SHORT).show()
            }

        }

    }

    // 계정 생성
    private fun createAccount(email: String, password: String,username: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        updateProfile(username)
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    //user의 프로필에 이름 등록
    private fun updateProfile(user_name:String){
        val user=Firebase.auth.currentUser
        val profileUpdates= userProfileChangeRequest {
            displayName= user_name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"name update",Toast.LENGTH_SHORT).show()
                }
            }
    }
}