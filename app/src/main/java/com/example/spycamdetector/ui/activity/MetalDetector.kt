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
import com.example.spycamdetector.databinding.ActivityMetalDetectorBinding
import com.example.spycamdetector.sharedPref.SaveData
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.sqrt


class MetalDetector : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    var bindingMetal: ActivityMetalDetectorBinding? = null
    private val binding get() = bindingMetal!!
    var saveData : SaveData? = null
    var mpmetal: MediaPlayer? = null

    var metalDetector: Sensor? = null

    private var magnitude: Double? =0.0
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
        bindingMetal = ActivityMetalDetectorBinding.inflate(layoutInflater)
        setContentView(bindingMetal?.root)
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        DECIMAL_FORMATTER = DecimalFormat("#.000000", symbols)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        metalDetector = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        mpmetal = MediaPlayer.create(this, R.raw.all_music)
        setUpLineChart()

        binding.spyGraph.setTouchEnabled(false)
        binding.spyGraph.setDragEnabled(true)
        binding.spyGraph.setScaleEnabled(true)
        binding.spyGraph.setPinchZoom(false)
        binding.spyGraph.setDrawGridBackground(false)
        binding.spyGraph.setMaxHighlightDistance(200f)
        binding.spyGraph.setViewPortOffsets(0f, 0f, 0f, 0f)

        binding.backIcon.setOnClickListener {
            val intent = Intent(this@MetalDetector, MainActivity::class.java)
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
            ) } else {
            Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setUpLineChart() {
        val dataSet = LineDataSet(ArrayList(), "Magnetic Values")
//        dataSet.setDrawCircles(true) // Change this to true if you want circles on the data points
        dataSet.fillAlpha=255////0-255
        dataSet.fillColor=ContextCompat.getColor(this, R.color.blue) // Change this to true if you want circles on the data points
        dataSet.setDrawCircles(false)
        dataSet.valueTextSize = 0f
        dataSet.lineWidth = 2f
        dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataSet.color = ContextCompat.getColor(this, R.color.lineStroke)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = ContextCompat.getColor(this, R.color.lineFill)
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.setDrawVerticalHighlightIndicator(true)
        dataSet.setDrawHorizontalHighlightIndicator(true)
        binding.spyGraph.axisLeft.setDrawGridLines(true)
        binding.spyGraph.axisRight.setDrawGridLines(true)
        binding.spyGraph.xAxis.setDrawGridLines(true)
        binding.spyGraph.background=ContextCompat.getDrawable(this, R.color.chartBackground)
        binding.spyGraph.setDrawBorders(false)
        binding.spyGraph.isAutoScaleMinMaxEnabled = true
//        val leftAxsis: YAxis = binding.spyGraph.axisLeft
//        leftAxsis.isEnabled = false
//        val rightAxis : YAxis = binding.spyGraph.axisRight
//        rightAxis.isEnabled = false
//        val xAxis : XAxis = binding.spyGraph.xAxis
//        xAxis.isEnabled = false


        val legend: Legend = binding.spyGraph.legend
        legend.isEnabled = false
        binding.spyGraph.description.text = ""
        val lineData = LineData(dataSet)
         binding.spyGraph.data = lineData
         binding.spyGraph.invalidate() // Refresh the chart
    }
    private fun updateLineChart(value: Float) {
        val lineData = binding.spyGraph.data
        if (lineData != null) {
            val dataSet = lineData.getDataSetByIndex(0) as LineDataSet
            val entry = Entry(lineData.entryCount.toFloat(), value)
            lineData.addEntry(entry, 0)
            lineData.notifyDataChanged()
            binding. spyGraph.notifyDataSetChanged()
            binding. spyGraph.setVisibleXRangeMaximum(150f) // Adjust the visible range as needed
            binding. spyGraph.moveViewToX(lineData.entryCount.toFloat())
        }
    }
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
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
            binding.mobileImage.speedTo(degree)
            updateLineChart(tesla.toFloat() ?: 0.0f)

            if (degree >= 40) {
                mpmetal!!.start()
                binding.detectMessageText.text = "Metal Detected"
                binding.detectMessageText.setTextColor(resources.getColor(R.color.lineStroke))
            } else {
                binding.detectMessageText.text = "Nothing  Detected"
                binding.detectMessageText.setTextColor(resources.getColor(R.color.green))
                /* if (mp.isPlaying()){
                    mp.stop();
                    mp.release();
                }*/
            }
        } else {
            alertDialouge()
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
