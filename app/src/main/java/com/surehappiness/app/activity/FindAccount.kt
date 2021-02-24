package com.surehappiness.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.surehappiness.app.R
import com.surehappiness.app.activity.findaccount.ViewPagerAdapter
import kotlinx.android.synthetic.main.find_account.*

class FindAccount: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_account)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.adapter = pagerAdapter

        var tabLayout = tab_layout
        tabLayout.setupWithViewPager(view_pager)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }



}