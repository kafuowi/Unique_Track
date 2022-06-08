package org.techtown.unique_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*
import org.techtown.unique_track.model.User

class RegisterActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_join)

        //back 버튼 -> 이전 창(로그인창으로)
        val back_button2=findViewById<Button>(R.id.back_button2)
        back_button2.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }

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
                        //Get current user uid
                        val currentUser = Firebase.auth.currentUser
                        uid = currentUser?.uid
                        val notNullableID : String = uid!!
                        //Store user information in database
                        database = Firebase.database.reference
                        val user = User(username, email)
                        database.child("Owners").child(notNullableID).setValue(user)
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