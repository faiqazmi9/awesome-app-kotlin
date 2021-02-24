package com.example.awesomeapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.apache.commons.lang3.StringUtils
import timber.log.Timber

class DetailImageActivity : AppCompatActivity() {
    private var img = ""
    private var title = ""
    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_image)
        val i = intent
        val bundle = i.extras!!
        img = bundle.getString("img", "0")
        title = bundle.getString("title", "")
        url = bundle.getString("url", "")
        val mIv = findViewById<ImageView>(R.id.image)
        val mTvTitle = findViewById<TextView>(R.id.title)
        val mTvDescription = findViewById<TextView>(R.id.description)
        val mIvBack = findViewById<ImageView>(R.id.iv_back)
        if (StringUtils.isNotEmpty(img)) {
            try {
                Glide.with(this).load(img)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(mIv)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        } else {
            try {
                Glide.with(this).load(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(mIv)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
        mTvTitle.text = title
        mTvDescription.text = url
        mIvBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
}