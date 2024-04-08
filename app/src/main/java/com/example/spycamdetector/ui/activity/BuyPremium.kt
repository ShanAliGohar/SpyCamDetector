package com.example.spycamdetector.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityBuyPremiumBinding

class BuyPremium : AppCompatActivity() {
    var binding : ActivityBuyPremiumBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        super.onCreate(savedInstanceState)
        binding = ActivityBuyPremiumBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.radio1?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_fill))
        binding?.constrain1?.setBackgroundResource(R.drawable.src_fill)

        binding?.constrain1?.setOnClickListener{
            binding?.radio1?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_fill))
            binding?.constrain1?.setBackgroundResource(R.drawable.src_fill)

            binding?.radio2?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain2?.setBackgroundResource(R.drawable.stroke_btn_premium)

            binding?.radio3?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain3?.setBackgroundResource(R.drawable.stroke_btn_premium)

        }
        binding?.constrain2?.setOnClickListener{
            binding?.radio2?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_fill))
            binding?.constrain2?.setBackgroundResource(R.drawable.src_fill)

            binding?.radio1?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain1?.setBackgroundResource(R.drawable.stroke_btn_premium)

            binding?.radio3?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain3?.setBackgroundResource(R.drawable.stroke_btn_premium)

        }
        binding?.constrain3?.setOnClickListener{
            binding?.radio3?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_fill))
            binding?.constrain3?.setBackgroundResource(R.drawable.src_fill)

            binding?.radio2?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain2?.setBackgroundResource(R.drawable.stroke_btn_premium)

            binding?.radio1?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.radio_blank))
            binding?.constrain1?.setBackgroundResource(R.drawable.stroke_btn_premium)

        }
        binding?.textView16?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}