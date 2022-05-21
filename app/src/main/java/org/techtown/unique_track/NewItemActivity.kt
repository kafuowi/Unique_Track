package org.techtown.unique_track

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_item.*

class NewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_item)
        val codeintent = getIntent()

        NewNFCcodeView.text = "NFC Not Found"

        if(codeintent.hasExtra("NFCcode")) {
            val NFCcode = codeintent.getStringExtra("NFCcode")


            NewNFCcodeView.text = NFCcode
            Toast.makeText(this, "NFC ID: $NFCcode", Toast.LENGTH_SHORT).show()
        }
        else{
            NewNFCcodeView.text = "NFC Not Found"
        }

    }

}