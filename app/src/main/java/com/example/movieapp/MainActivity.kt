package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonLogin.setOnClickListener {
                checkLoginEvent()
            }
        }
    }

    private fun checkLoginEvent() {
        val username = binding.editTextUserName.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (username.isNotBlank() && password.isNotBlank()) {
            showAndHideHelperText("", "")
            if (username == "thanhduong" && password == "10022001") {
                showMessage("Login successfully")
            } else {
                showMessage("Username or password is wrong")
            }
        } else {
            if (username.isNotBlank()) {
                showAndHideHelperText("", "Required*")
            } else if (password.isNotBlank()) {
                showAndHideHelperText("Required*", "")
            } else {
                showAndHideHelperText("Required*", "Required*")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this@MainActivity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showAndHideHelperText(helperUsername: String, helperPassword: String) {
        binding.apply {
            textInputUsername.helperText = helperUsername
            textInputPassword.helperText = helperPassword
        }
    }
}