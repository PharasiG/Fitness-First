package com.example.fitness

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var btnSignUp: Button

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private lateinit var progressDialog: ProgressDialog
    private val TAG = "Auth"

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputEmail = binding.usernameEditTextSignUp
        inputPassword = binding.passwordEditTextSignUp
        inputConfirmPassword = binding.cpasswordEditTextSignUp
        btnSignUp = binding.signUp
        progressDialog = ProgressDialog(this)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        btnSignUp.setOnClickListener { performAuth(); }
    }

    fun onTextClick(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun performAuth() {
        val email: String = inputEmail.text.toString()
        val password: String = inputPassword.text.toString()
        val confirmPassword: String = inputConfirmPassword.text.toString()

        if (!email.matches(emailRegex.toRegex())) {
            inputEmail.error = "Enter Valid Email"
        } else if (password.isEmpty() or (password.length < 6)) {
            inputPassword.error = "Password length must be > 5"
        } else if (password != confirmPassword) {
            inputConfirmPassword.error = "Passwords do not match"
        } else {
            progressDialog.setMessage("Please wait, your account is being created")
            progressDialog.setTitle("Signing Up")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        progressDialog.dismiss()
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(baseContext, "Registration Successful", Toast.LENGTH_SHORT)
                            .show()
                        user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss()
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Registration failed, please try again in a minute",
                            Toast.LENGTH_SHORT,
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

}