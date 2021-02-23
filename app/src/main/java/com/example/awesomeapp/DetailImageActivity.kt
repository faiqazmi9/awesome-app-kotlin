package com.example.awesomeapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailImageActivity : AppCompatActivity() {
    private var img = 0
    private var title = ""
    private var desc = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_image)
        val i = intent
        val bundle = i.extras!!
        img = bundle.getInt("img", 0)
        title = bundle.getString("title", "")
        desc = bundle.getString("desc", "")
        val mIv = findViewById<ImageView>(R.id.image)
        val mTvTitle = findViewById<TextView>(R.id.title)
        val mTvDescription = findViewById<TextView>(R.id.description)
        val mIvBack = findViewById<ImageView>(R.id.iv_back)
        mIv.setImageResource(img)
        mTvTitle.text = title
        mTvDescription.text = desc
        mIvBack.setOnClickListener { v: View? ->
            finish()
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
}