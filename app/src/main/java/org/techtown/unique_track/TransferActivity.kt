package org.techtown.unique_track

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.techtown.unique_track.R.layout.activity_transfer

class TransferActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_transfer)
        var editText = EditText(this)
        var builder = AlertDialog.Builder(this)
        val transfer_Button = findViewById<Button>(R.id.itemTransfer_Button)
        var resultUID : String

        builder.setTitle("Transfer").setMessage("양도 받을 사람의 UID 입력").setView(editText)
        builder.setPositiveButton("확인", DialogInterface.OnClickListener {dialog, which ->  createAlert(editText.text.toString());finishActivity(0) })
        builder.setNegativeButton("취소", null)
        transfer_Button.setOnClickListener{
            builder.show()
        }
    }
    fun createAlert(string: String){

    }

}