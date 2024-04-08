package com.example.spycamdetector.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityWiredCameraBinding
import com.example.spycamdetector.sharedPref.SaveData
import java.net.Inet4Address
import java.net.NetworkInterface
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class WiredCamera : AppCompatActivity(), SensorEventListener {
    var mpmetal: MediaPlayer? = null
    private var metalDetector : Sensor? = null
    private var sensorManager: SensorManager? = null
    var binding: ActivityWiredCameraBinding? = null
    var saveData : SaveData? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        saveData = SaveData(this)
        if (saveData!!.loadDarkModeState()){
            setTheme(R.style.DarkTheme)
        }else{
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityWiredCameraBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        DECIMAL_FORMATTER = DecimalFormat("#.000000", symbols)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        metalDetector = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        mpmetal = MediaPlayer.create(this, R.raw.all_music)
        binding?.backIcon?.setOnClickListener {
            val intent = Intent(this@WiredCamera, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    override fun onResume() {
        super.onResume()
        if(metalDetector != null){
        sensorManager!!.registerListener(
            this,
            sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        ) }else{
            Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == metalDetector ) {
            // SO these values are combine to produce magnetic values
            val degree = Math.round(event.values[0]).toFloat()
            val azimuth = Math.round(event.values[0]).toFloat()
            val pitch = Math.round(event.values[1]).toFloat()
            val roll = Math.round(event.values[2]).toFloat()
            Log.d("TAG", "onSensorChanged: $degree")
            val tesla =
                Math.sqrt((azimuth * azimuth + pitch * pitch + roll * roll).toDouble()).toFloat()
            //txtSpeed.setText();
            val text = String.format("%.0f", tesla)
            binding!!.mobileImage.speedTo(degree)
            if (degree >= 40) {
                mpmetal!!.start()
                binding!!.detectMessageText.text = "Metal Detected"
                binding!!.detectMessageText.setTextColor(resources.getColor(R.color.lineStroke))
            } else {
                binding!!.detectMessageText.text = "Nothing  Detected"
                binding!!.detectMessageText.setTextColor(resources.getColor(R.color.green))

                /* if (mp.isPlaying()){
                    mp.stop();
                    mp.release();
                }*/
            }
        } else {
        Toast.makeText(this,"No sensor",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    companion object {
        var DECIMAL_FORMATTER: DecimalFormat? = null
    }
    private fun alertDialouge() {
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.sensoralert_dialoug, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}


