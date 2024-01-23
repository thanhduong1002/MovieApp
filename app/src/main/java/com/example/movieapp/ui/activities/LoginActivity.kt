package com.example.movieapp.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.utils.KeyboardUtil
import com.example.movieapp.utils.StringUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val LOGIN_SHARED_PREF = "login_shared_pref"
    private val USERNAME = "username"
    private val PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSharedPreferences()

        handleOnClick()
    }

    private fun checkLoginEvent() {
        val username = binding.editTextUserName.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (username.isNotBlank() && password.isNotBlank()) {
            showAndHideHelperText("", "")
            if (username == "thanhduong" && password == "10022001") {
                if (binding.checkBox.isChecked) {
                    saveData(username, password)
                }

                Intent(this, HomeActivity::class.java).run {
                    startActivity(this)
                }
            } else {
                showErrorMessage()
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

    private fun showErrorMessage() {
        Toast.makeText(
            this@LoginActivity,
            "Username or password is wrong",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showAndHideHelperText(helperUsername: String, helperPassword: String) {
        binding.apply {
            textInputUsername.helperText = helperUsername
            textInputPassword.helperText = helperPassword
        }
    }

    private fun saveData(username: String, password: String) {
        binding.apply {
            val sharedPref = getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE)

            sharedPref.edit().apply {
                putString(USERNAME, username)
                putString(PASSWORD, password)
            }.apply()
        }
    }

    private fun checkSharedPreferences() {
        getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE).apply {
            val username = this.getString(USERNAME, "")
            val password = this.getString(PASSWORD, "")

            if (username?.isNotBlank() == true && password?.isNotBlank() == true) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                builder
                    .setMessage("Do you want to use username: $username and password: $password  ?")
                    .setPositiveButton(StringUtil.setColor("OK")) { dialog, _ ->
                        binding.apply {
                            editTextUserName.setText(username)
                            editTextPassword.setText(password)
                        }

                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    private fun handleOnClick() {
        binding.apply {
            buttonLogin.setOnClickListener {
                checkLoginEvent()
            }
            editTextPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        KeyboardUtil.hideKeyboard(this@LoginActivity)

                        true
                    }
                    else -> false
                }
            }
        }
    }
}