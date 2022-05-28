package org.techtown.unique_track.Fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_main_profile.*
import kotlinx.android.synthetic.main.fragment_main_profile.view.*
import org.techtown.unique_track.R
import java.text.SimpleDateFormat
import java.util.*


class FragmentMainProfile : Fragment(){
    val PERMISSIONS_REQUEST_CODE = 100
    private var viewProfile : View? = null
    var pickImageFromAlbum = 0
    var fbStorage : FirebaseStorage? = null
    var uriPhoto : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewProfile = inflater.inflate(R.layout.fragment_main_profile, container, false)

        //Initialize Firebase Storage
        fbStorage = FirebaseStorage.getInstance()

        viewProfile!!.xml_frg_prf_btn_upload.setOnClickListener {
            //Open Album
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, pickImageFromAlbum)
        }

        return viewProfile
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                //Path for the selected image
                uriPhoto = data?.data
                xml_frg_prf_img_profile.setImageURI(uriPhoto)

                if(ContextCompat.checkSelfPermission(viewProfile!!.context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    funImageUpload(viewProfile!!)
                }
            }
            else{

            }
        }
    }
    private fun funImageUpload(view : View){

        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "IMAGE_" + timeStamp + "_.png"
        var storageRef = fbStorage?.reference?.child("images")?.child(imgFileName)

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(view.context, "Image Uploaded", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            // Handle any errors
        }
        dataPassListener.onDataPass("https://firebasestorage.googleapis.com/v0/b/unique-track-f112a.appspot.com/o/images%2F"+imgFileName+"?alt=media&token=ca1ee99a-a96e-4074-9bf8-49eda0d701a4")
        /*
        var imgRef =  fbStorage?.reference?.child("images/"+imgFileName)

        imgRef?.downloadUrl?.addOnSuccessListener{

            dataPassListener.onDataPass(it.toString())

            Toast.makeText(view.context, it.toString(), Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            Toast.makeText(view.context, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("tag",it.toString())
            // Handle any errors
        }*/


    }

    interface  OnDataPassListener {
        fun onDataPass(data: String)
    }

    lateinit var dataPassListener: OnDataPassListener

    override fun onAttach(context: Context) {

        super.onAttach(context)

        dataPassListener = context as OnDataPassListener
    }




}