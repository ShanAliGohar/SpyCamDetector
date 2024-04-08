package com.example.spycamdetector.ui.activity

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityFaqsHelpBinding

class FAQsHelp : AppCompatActivity() {
    var binding : ActivityFaqsHelpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        super.onCreate(savedInstanceState)
        binding = ActivityFaqsHelpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.txt1?.visibility = View.GONE
        binding?.txt2?.visibility = View.GONE
        binding?.txt3?.visibility = View.GONE
        binding?.txt4?.visibility = View.GONE

        binding?.backButton?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding?.bedcard?.setOnClickListener {
            if (binding?.txt1?.visibility == View.GONE){
                binding?.txt1?.visibility = View.VISIBLE
                binding?.downOne?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_up_24))
            } else {
                binding?.txt1?.visibility = View.GONE
                binding?.downOne?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_down_24))

            }
        }
        binding?.bedcard2?.setOnClickListener {
            if (binding?.txt2?.visibility == View.GONE){
                binding?.txt2?.visibility = View.VISIBLE
                binding?.downTwo?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_up_24))

            } else {
                binding?.txt2?.visibility = View.GONE
                binding?.downTwo?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_down_24))

            }
        }
        binding?.bedcard3?.setOnClickListener {
            if (binding?.txt3?.visibility == View.GONE){
                binding?.txt3?.visibility = View.VISIBLE
                binding?.downThree?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_up_24))

            } else {
                binding?.txt3?.visibility = View.GONE
                binding?.downThree?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_down_24))

            }
        }
        binding?.bedcard4?.setOnClickListener {
            if (binding?.txt4?.visibility == View.GONE){
                binding?.txt4?.visibility = View.VISIBLE
                binding?.downFour?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_up_24))

            } else {
                binding?.txt4?.visibility = View.GONE
                binding?.downFour?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_keyboard_arrow_down_24))

            }
        }
    }
}