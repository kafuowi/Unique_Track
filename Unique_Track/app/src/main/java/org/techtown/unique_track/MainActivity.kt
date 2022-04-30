package org.techtown.unique_track

import android.content.Intent
import android.app.AlertDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        // 로그아웃
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

        //사용자 정보 확인 창으로
        val user_button =findViewById<Button>(R.id.user_button)
        user_button.setOnClickListener{
            startActivity(Intent(this@MainActivity,UserActivity::class.java))
        }
    }

    fun changePassword(password:String){
        FirebaseAuth.getInstance().currentUser!!.updatePassword(password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()

            }
        }
    }
}