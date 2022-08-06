package com.example.login_logout_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var sign_in_btn: Button
    lateinit var Log_in_btn: Button
    lateinit var email_edt: EditText
    lateinit var password_edt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blinding()

        initclick()

    }

    fun initclick() {

        sign_in_btn.setOnClickListener {

            signin(email_edt.text.toString(), password_edt.text.toString())

        }
        Log_in_btn.setOnClickListener {

            var i = Intent(this, Login_Screen::class.java)
            startActivity(i)
        }
    }

    fun signin(email: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener { error ->
                Log.e("TAG", "signin: ${error.message}")
            }
    }

    fun blinding() {
        sign_in_btn = findViewById<Button>(R.id.sign_in_btn)
        Log_in_btn = findViewById<Button>(R.id.Log_in_btn)
        email_edt = findViewById<EditText>(R.id.email_edt)
        password_edt = findViewById<EditText>(R.id.password_edt)

    }
}