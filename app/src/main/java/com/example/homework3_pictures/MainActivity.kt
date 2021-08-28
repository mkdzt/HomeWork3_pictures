package com.example.homework3_pictures

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var etUrl:EditText
    private lateinit var ivPicture:ImageView
    private lateinit var pbImageLoader:ProgressBar
    private lateinit var picasso:Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUrl = findViewById(R.id.et_url)
        ivPicture = findViewById(R.id.iv_picture)
        pbImageLoader = findViewById(R.id.pb_image_loader)
        picasso = Picasso.get()
        showImage()
    }

    private fun isUrlValid(url:String):Boolean = url.isNotEmpty() && url.startsWith("https://")

    private fun loadImage(url:String){
        picasso.load(url).into(ivPicture, object : Callback{
            override fun onSuccess() {
                pbImageLoader.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                pbImageLoader.visibility = View.GONE
                Toast.makeText( applicationContext, "Can't load image", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun showImage() {
        etUrl.setOnKeyListener { view, key, keyEvent ->
            val url = etUrl.text.toString()
            val urlValid = isUrlValid(url)
            if(key == KEYCODE_ENTER && urlValid){
                pbImageLoader.visibility = View.VISIBLE
                loadImage(url)
                hideKeyboard()
            }else if(key == KEYCODE_ENTER && !urlValid){
                Toast.makeText(applicationContext, "Not valid url", Toast.LENGTH_LONG).show()
            }
            urlValid
        }
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view:View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}