package com.example.fitness

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var btnSignIn: Button
//    private lateinit var imgGoogleSignIn: ImageView

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private lateinit var progressDialog: ProgressDialog
    private val TAG = "Auth"

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var googleSinSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputEmail = binding.usernameEditText
        inputPassword = binding.passwordEditText
        btnSignIn = binding.signIn
//        imgGoogleSignIn = binding.googleLogo
        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        user = auth.currentUser

        btnSignIn.setOnClickListener { performAuth() }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSinSignInClient = GoogleSignIn.getClient(this, gso)

    }

    fun onTextClick(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun onImageClick(view: View) {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
        signInWithGoogle()
    }

    private fun performAuth() {
        val email: String = inputEmail.text.toString()
        val password: String = inputPassword.text.toString()

        if (!email.matches(emailRegex.toRegex())) {
            inputEmail.error = "Enter Valid Email"
        } else if (password.isEmpty() or (password.length < 6)) {
            inputPassword.error = "Password length must be > 5"
        } else {
            progressDialog.setMessage("Please wait, singing in...")
            progressDialog.setTitle("Signing In")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(baseContext, "Signed In Successfully", Toast.LENGTH_SHORT)
                            .show()
                        user = auth.currentUser
                        progressDialog.dismiss()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss()
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Invalid Credentials, Please try again in a minute",
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, LoggedInActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSinSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, "SignIn Failed, Try again later", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        progressDialog.setMessage("Please wait, singing in...")
        progressDialog.setTitle("Signing In")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, LoggedInActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                progressDialog.dismiss()
            } else {
                Toast.makeText(this, "Can't login currently, Try again later", Toast.LENGTH_LONG)
                    .show()
                progressDialog.dismiss()
            }
        }
    }

}