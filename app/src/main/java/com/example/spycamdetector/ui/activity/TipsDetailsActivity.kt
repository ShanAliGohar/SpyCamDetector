package com.example.spycamdetector.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityBedroomBinding

class TipsDetailsActivity : AppCompatActivity() {

    var binding : ActivityBedroomBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        super.onCreate(savedInstanceState)
        binding = ActivityBedroomBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        var from = intent.getStringExtra("from")
        when(from){
            "bedRoom"->{
                bedroomWork()
            }"bathroom"->{
                bathroomWork()
            }"changingRoom"->{
                changingRoomWork()
            }"outSide"->{
                outSideRoomWork()
            }
        }
        binding?.backIcon?.setOnClickListener {
            val intent = Intent(this, TipsTricks::class.java)
            startActivity(intent)
        }
    }
    private fun outSideRoomWork() {
        binding?.headingtext2?.setText("Outside")
        binding?.imageView21?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.outside_image))
        binding?.constrainTxt1?.setText(R.string.outside)
        binding?.mainTitle?.setText("Outside")


    }
    private fun changingRoomWork() {
        binding?.headingtext2?.setText("Changing Room")
        binding?.imageView21?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.changing_room_image))
        binding?.constrainTxt1?.setText(R.string.changing)
        binding?.mainTitle?.setText("Changing Room")

    }
    private fun bathroomWork() {
        binding?.headingtext2?.setText("Bathroom")
        binding?.imageView21?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.bathroom_image))
        binding?.constrainTxt1?.setText(R.string.bathroom)
        binding?.mainTitle?.setText("Bathroom")
    }
    private fun bedroomWork() {
        binding?.headingtext2?.setText("Bedroom")
        binding?.imageView21?.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.bedroom_image))
        binding?.constrainTxt1?.setText(R.string.bedroom)
        binding?.mainTitle?.setText("Bedroom")
    }
}
