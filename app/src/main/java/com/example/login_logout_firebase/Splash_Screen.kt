package com.example.login_logout_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var firebaseAuth=FirebaseAuth.getInstance()
        var user=firebaseAuth.currentUser

Handler().postDelayed(  {

if(user!=null)
{
    var intent=Intent(this,Home_Activity::class.java)
    startActivity(intent)

}
    else
{

    var intent=Intent(this,Login_Screen::class.java)
    startActivity(intent)

}
    finish()
},3000)
    }
}