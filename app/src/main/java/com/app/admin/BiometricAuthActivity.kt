package com.app.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.admin.databinding.ActivityBiometricAuthBinding
import com.app.admin.databinding.ActivityLoginBinding
import java.util.concurrent.Executor

class BiometricAuthActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var binding: ActivityBiometricAuthBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptinfo: BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBiometricAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object :BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
//                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Authentication Succeed", Toast.LENGTH_SHORT).show()
                    binding.imageView2.setImageResource(R.drawable.finger_print3) // Replace 'new_image' with your image resource name
//                    val intent = Intent(applicationContext, CreateAccountActivity::class.java)
//                    startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                    binding.imageView2.setImageResource(R.drawable.finger_print2)
                }
            })

        promptinfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Login using your biometric credentials")
           .setNegativeButtonText("Back")
            .setConfirmationRequired(false)
            .build()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun biometricAuth(view: View) {
        biometricPrompt.authenticate(promptinfo)
    }
}