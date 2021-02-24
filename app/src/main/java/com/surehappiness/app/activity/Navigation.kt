package com.surehappiness.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.surehappiness.app.R

class Navigation: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_bottom_menu)

        val navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this)
        navigationView.selectedItemId = R.id.home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.success_list -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, SuccessList()).commit()
            }

            R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, NewPurposeList()).commit()
            }

            R.id.ranking -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, RankingFragment()).commit()
            }

            R.id.mypage -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MyPageFragment()).commit()
            }
        }

        return true
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish()
            return
        }
        Toast.makeText(applicationContext, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        lastTimeBackPressed = System.currentTimeMillis()
    }
}