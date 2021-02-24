package com.surehappiness.app.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import com.surehappiness.app.R
import kotlinx.android.synthetic.main.splash.*

class NewSplash: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val splashTitle = "바쁜 일상 속, 소확행 습관들이기"
        val splashTextView = splash_text

        val stringBuilder = SpannableStringBuilder(splashTitle)
        stringBuilder.setSpan(StyleSpan(Typeface.BOLD),9,12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        splashTextView.text = stringBuilder

        Handler().postDelayed(object: Runnable{
            override fun run() {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }

        },2000)
    }
}