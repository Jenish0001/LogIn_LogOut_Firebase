package com.example.login_logout_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Home_Activity : AppCompatActivity() {


    lateinit var login_id: ImageView
    lateinit var txt: TextView
    lateinit var realdata_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        blinding()
        loginclick()

        realdata_btn.setOnClickListener {

            var m1=ModelData("www","jneis","354678","1")

            realdataInsert(m1)

        }
    }

    private fun realdataInsert(m1: ModelData) {

        var firebaseDatabase=FirebaseDatabase.getInstance()
        var databaseReference=firebaseDatabase.reference
        databaseReference.child("Student").push().setValue(m1)


    }

    private fun loginclick() {
        login_id.setOnClickListener {

            var firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            var intent= Intent(this,Login_Screen::class.java)
            startActivity(intent)

        }

    }

    private fun blinding() {

        login_id = findViewById<ImageView>(R.id.login_id)
        txt = findViewById<TextView>(R.id.txt)
        realdata_btn = findViewById<Button>(R.id.realdata_btn)

    }

}