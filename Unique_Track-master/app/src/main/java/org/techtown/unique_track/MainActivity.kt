package org.techtown.unique_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//현재 사용자의 uid 전역변수로 선언
var uid:String?=null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //firebase에서 현재 uid 받아옴 (main창에 들어가면 바로 구할 수 있게)
        val user = Firebase.auth.currentUser
        //이메일 회원가입시에 해당하는 uid
        uid=user!!.uid

        //사용자 정보 확인 창으로 이동
        val user_button =findViewById<Button>(R.id.user_button)
        user_button.setOnClickListener{
            startActivity(Intent(this@MainActivity,UserActivity::class.java))
        }

        //제품 목록 창으로 이동
        val list_button = findViewById<Button>(R.id.itemlist_button)
        list_button.setOnClickListener{
            startActivity(Intent(this@MainActivity,ListActivity::class.java))
        }
        //제품 조회
        val check_button = findViewById<Button>(R.id.itemcheck_button)
        check_button.setOnClickListener{
            startActivity(Intent(this@MainActivity,NFCActivity::class.java))
        }
        //제품 등록
        val newItem_button = findViewById<Button>(R.id.itemregister_button)
        newItem_button.setOnClickListener {
            startActivity(Intent(this@MainActivity,NewNFCActivity::class.java))
        }
    }
}