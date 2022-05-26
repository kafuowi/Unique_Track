package org.techtown.unique_track

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import org.techtown.unique_track.R.layout.activity_profile

class ProfileActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_profile)
        var productIntent = intent
        var transferButton = findViewById<Button>(R.id.transfer_button)
        var transferIntent = Intent(this, TransferActivity::class.java)
        productIntent.getStringExtra("productName")
        transferButton.setOnClickListener{
            startActivityForResult(transferIntent, 0)

        }
    }
}