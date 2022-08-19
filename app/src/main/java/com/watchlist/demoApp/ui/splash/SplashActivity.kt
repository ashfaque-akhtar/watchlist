package com.watchlist.demoApp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperations
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperationsImpl
import com.indusnet.watchlist.watchlist.repository.WatchListRepositoryImpl
import com.indusnet.watchlist.watchlist.repository.WatchlistRepository
import com.watchlist.demoApp.R
import com.watchlist.demoApp.application.WatchListApplication
import com.watchlist.demoApp.databinding.ActivitySplashBinding
import com.watchlist.demoApp.ui.login.LoginActivity
import com.watchlist.demoApp.ui.watchlist.WatchlistActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding


    private val viewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        supportActionBar?.hide()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)

            var loginStatus: Boolean =
                CoroutineScope(Dispatchers.IO).async { viewModel.getLoginStatus() }.await()
            if (loginStatus) {
                startActivity(Intent(this@SplashActivity, WatchlistActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }
}