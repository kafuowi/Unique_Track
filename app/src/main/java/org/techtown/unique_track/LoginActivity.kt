package org.techtown.unique_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

var googleSignInClient : GoogleSignInClient? = null

class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    // 구글 로그인 연동에 필요한 변수
    private lateinit var activityLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth



        //LoginActivity에서 join버튼 클릭시 RegisterActivity(Join)로 이동
        val join_button=findViewById<Button>(R.id.joinin_button)
        join_button.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        //로그인 버튼 클릭시
        val login_button = findViewById<Button>(R.id.login_button)
        login_button.setOnClickListener {
            signIn(editTextTextPersonName4.text.toString(),
                editTextTextPassword3.text.toString())
        }

        //google login
        // id, email request
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        /*if(intent.hasExtra("logout")){
            var logout = intent.getBooleanExtra("logout",false)
            if(logout){
                googleSignInClient!!.signOut()
                auth!!.signOut()
            }

        }*/

        activityLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if(result.resultCode ==RESULT_OK){
                val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try{
                    val account=task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account)
                    Log.d("GoogleLogin","firebaseAuthWithGoogle: "+ account.id)
                } catch(e:ApiException){
                    Log.d("GoogleLogin","Google sign in failed: "+e.message)
                }
            }
        }
        val google_login_button = findViewById<Button>(R.id.google_login_button)
        google_login_button.setOnClickListener {
            activityLauncher.launch((googleSignInClient!!.signInIntent))
        }
    }

    // 로그아웃하지 않을 시 자동 로그인 , 회원가입시 바로 로그인 됨
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)

    }

    // 로그인
    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //pw=password
                        reauthenticate(password)
                        Toast.makeText(
                            baseContext, "로그인에 성공 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(
                            baseContext, "로그인에 실패 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // 유저정보 넘겨주고 메인 액티비티 호출
    private fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    //사용자 재인증
    private fun reauthenticate(password:String){
        val user=Firebase.auth.currentUser!!
        user.let{
            val email=user.email

            val credential = EmailAuthProvider
                .getCredential(email.toString(),password)

            user.reauthenticate(credential)
                .addOnCompleteListener{ Toast.makeText(this,"사용자 재인증되었습니다.",Toast.LENGTH_SHORT).show()}
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    // 로그인 성공 시
                    Toast.makeText(this,  "success", Toast.LENGTH_LONG).show()
                    moveMainPage(auth?.currentUser)
                } else {
                    // 로그인 실패 시
                    Toast.makeText(this,  task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    } //firebaseAuthWithGoogle
}