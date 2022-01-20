package com.bilalqwatly.test.presentation.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.R
import com.bilalqwatly.test.presentation.ui.auth.login.LoginActivity
import com.bilalqwatly.test.presentation.ui.main.MainActivity
import com.bilalqwatly.test.utils.NavigationUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var session: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        session = SharedPreferencesManager(this);

        if(session.isAuthenticated) {
            NavigationUtil.goToActivity(this, MainActivity::class.java, true)
        }else{
            NavigationUtil.goToActivity(this, LoginActivity::class.java, true)
        }
    }
}