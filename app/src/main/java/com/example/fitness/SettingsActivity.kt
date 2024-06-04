package com.example.fitness

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.util.ACTION
import com.example.fitness.util.DiaryHelper
import com.example.fitness.util.VALIDATION_AMOUNT
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            val value = findViewById<EditText>(R.id.waterAmountEdit).text.toString()

            if (value.isNotEmpty()) {
                DiaryHelper.setTotalWater(this, value.toFloat())
                finish()
            } else {
                Snackbar.make(saveBtn, VALIDATION_AMOUNT, Snackbar.LENGTH_SHORT)
                    .setAction(ACTION, null)
                    .show()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}