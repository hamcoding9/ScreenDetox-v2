package com.hamcoding.screendetox.ui.signin

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.BuildConfig
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.data.firebase.entity.User
import com.hamcoding.screendetox.databinding.ActivitySignInBinding
import com.hamcoding.screendetox.ui.HomeActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        createSignInClient()
        binding.buttonSignIn.setOnClickListener {
            displaySignIn()
        }
    }

    private val oneTapResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    Log.d("구글", "one tab 로그인 성공")
                                    registerUserInfo()
                                    moveToHomeScreen()
                                } else {
                                    Log.w(
                                        "SignInActivity",
                                        "signInWithCredential:failure",
                                        task.exception
                                    )
                                }
                            }
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }

    private fun displaySignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResultLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("Google", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.d("Google", e.localizedMessage)
                displayLegacySignIn()
            }
    }

    private val legacyResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val credential = task.getResult(ApiException::class.java)
            val idToken = credential.idToken
            val email = credential.email
            when {
                idToken != null -> {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d("구글", "legacy 로그인 성공")
                                val user = auth.currentUser
                                registerUserInfo()
                                moveToHomeScreen()
                            } else {
                                Log.w(
                                    "SignInActivity",
                                    "signInWithCredential:failure",
                                    task.exception
                                )
                            }
                        }
                }
            }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private fun displayLegacySignIn() {
        gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        legacyResultLauncher.launch(signInIntent)
    }

    private fun createSignInClient() {
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()
    }

    private fun moveToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun registerUserInfo() {
        val userRef = Firebase.database.reference.child("users")
        val uid = auth.currentUser?.uid!!
        val email = auth.currentUser?.email!!.toString()
        val user = User(email, null, emptyMap())
        userRef.child(uid).setValue(user)
    }

}