package com.example.spycamdetector.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityTipsTricksBinding

class TipsTricks : AppCompatActivity() {
    var binding : ActivityTipsTricksBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        super.onCreate(savedInstanceState)
        binding = ActivityTipsTricksBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.listOne?.setOnClickListener {
            val intent = Intent(this, TipsDetailsActivity::class.java)
                .putExtra("from","bedRoom")
            startActivity(intent)

        }
        binding?.listTwo?.setOnClickListener {
            val intent = Intent(this, TipsDetailsActivity::class.java)
                .putExtra("from","bathroom")

            startActivity(intent)
        }
        binding?.listThree?.setOnClickListener {
            val intent = Intent(this, TipsDetailsActivity::class.java)
                .putExtra("from","changingRoom")

            startActivity(intent)
        }
        binding?.listFour?.setOnClickListener {
            val intent = Intent(this, TipsDetailsActivity::class.java)
                .putExtra("from","outSide")

            startActivity(intent)
        }
        binding?.back?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}