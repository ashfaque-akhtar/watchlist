package com.watchlist.demoApp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.watchlist.demoApp.R
import com.watchlist.demoApp.databinding.ActivityMainBinding
import com.watchlist.demoApp.ui.watchlist.WatchlistActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()
        binding.btnLogin.setOnClickListener {
          if(validateForm()){
              lifecycleScope.launch(Dispatchers.IO){
                  viewModel.storeUserData(binding.emailEt.text.toString().trim())
              }
              startActivity(Intent(this,WatchlistActivity::class.java))
              finish()
          }
        }

    }

    fun validateForm() : Boolean{
        var formStatus  = true
        if(binding.emailEt.text.isNullOrEmpty()){
            binding.email.error = resources.getString(R.string.enter_email)
            formStatus = false
        }else{
            binding.email.error = null
        }
         if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString()).matches()){
            binding.email.error = resources.getString(R.string.enter_valid_email)
             formStatus = false
        }else{
             binding.email.error = null
         }
          if(binding.passwordEt.text.isNullOrEmpty()){
            binding.password.error = resources.getString(R.string.enter_password)
              formStatus = false
        }else{
            binding.password.error = null

        }
        return  formStatus
    }
}