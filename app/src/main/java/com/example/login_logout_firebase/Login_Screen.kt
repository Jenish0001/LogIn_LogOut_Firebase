package com.example.login_logout_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class Login_Screen : AppCompatActivity() {

    private val EMAIL = "email"
    lateinit var loginButton: LoginButton
    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 1
    lateinit var login_email_edt: EditText
    lateinit var login_password_edt: EditText
    lateinit var creat_txt: TextView
    lateinit var login_sign_in_btn: Button
    lateinit var google_login_sign_in_btn: Button
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)


        blindin()
        onclick()
        facebookLogIN2()

    }
//facebook  login =============

    private fun facebookLogIN2() {

        var callbackManager = CallbackManager.Factory.create()
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: LoginResult) {
                firebaseLogin(result.accessToken.token)
            }
        })
    }

    private fun firebaseLogin(toString: String) {

        var cmd = FacebookAuthProvider.getCredential(toString)
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(cmd).addOnSuccessListener { res ->
            var int = Intent(this, Home_Activity::class.java)
            startActivity(int)
            Log.e("TAG", "firefblogin: $res")

        }.addOnFailureListener { error ->
            Log.e("TAG", "firefblogin: ${error.message}")

        }
    }

    private fun onclick() {

        login_sign_in_btn.setOnClickListener {

            signIn(login_email_edt.text.toString(), login_password_edt.text.toString())

        }
        creat_txt.setOnClickListener {

            var i = Intent(this, MainActivity()::class.java)
            startActivity(i)
        }
        google_login_sign_in_btn.setOnClickListener {

            googleLogin()


        }

    }

//     google login ============

    private fun googleLogin() {
// newcode==============

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString((R.string.client)))
            .requestEmail()
            .build()
        var googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)

//        new code================

//        var signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.client))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build()
//            )
//            .build()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
//                old code==========================

                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                var account = result?.signInAccount
                loginwithCredantail(account?.idToken.toString())

//                new code==================

//                try {
//                    var firebaseAuth = FirebaseAuth.getInstance()
//
//                    val oneTapClient = Identity.getSignInClient(this)
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//
//                            loginwithCredantail(idToken)
//
//                            Log.d("TAG", "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d("TAG", "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    // ...
//                }


            }

        }
    }

    private fun loginwithCredantail(idToken: String) {

        val crd = GoogleAuthProvider.getCredential(idToken, null)

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(crd).addOnSuccessListener { res ->
            var i = Intent(this, Home_Activity::class.java)
            startActivity(i)

        }.addOnFailureListener { error ->
            Log.e("TAG", "loginwithCredantail: ${error.message}")
        }

    }

    private fun blindin() {

        login_email_edt = findViewById<EditText>(R.id.login_email_edt)
        login_password_edt = findViewById<EditText>(R.id.login_password_edt)
        login_sign_in_btn = findViewById<Button>(R.id.login_sign_in_btn)
        google_login_sign_in_btn = findViewById<Button>(R.id.google_login_sign_in_btn)
        creat_txt = findViewById<TextView>(R.id.creat_txt)

        loginButton = findViewById<LoginButton>(R.id.login_button)
//        loginButton = findViewById(R.id.login_button)
        loginButton.setReadPermissions(Arrays.asList(EMAIL))


    }

    fun signIn(email: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            login_sign_in_btn.setOnClickListener {

                var i = Intent(this, Home_Activity()::class.java)
                startActivity(i)

            }


        }
            .addOnFailureListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "signin: ${error.message}")
            }

    }

}




