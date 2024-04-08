package com.example.spycamdetector.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivitySpyCamBinding
import com.example.spycamdetector.sharedPref.SaveData
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.DecimalFormat
import kotlin.math.sqrt

class SpyCam : AppCompatActivity(), SensorEventListener {
    var mpmetal: MediaPlayer? = null
    var saveData : SaveData? = null
    var metalDetector : Sensor? = null
    var bindingSpy : ActivitySpyCamBinding? = null
    private val binding get() = bindingSpy!!
    var sensorManager : SensorManager? = null
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
        bindingSpy = ActivitySpyCamBinding.inflate(layoutInflater)
        setContentView(bindingSpy?.root)
        mpmetal = MediaPlayer.create(this, R.raw.all_music)

        DECIMAL_FORMATTER = DecimalFormat("#.000")
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        metalDetector = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        setUpLineChart()

            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 1000 // You can manage the blinking time with this parameter
            anim.startOffset = 100
            anim.repeatMode = Animation.REVERSE
            anim.repeatCount = Animation.INFINITE
            binding.flowOne.startAnimation(anim)
            binding.flowTwo.startAnimation(anim)
            binding.flowThree.startAnimation(anim)
            binding.flowFour.startAnimation(anim)
            binding.flowFive.startAnimation(anim)


        binding.spyGraph.setTouchEnabled(false)
        binding.spyGraph.setDragEnabled(true)
        binding.spyGraph.setScaleEnabled(true)
        binding.spyGraph.setPinchZoom(false)
        binding.spyGraph.setDrawGridBackground(false)
        binding.spyGraph.setMaxHighlightDistance(200f)
        binding.spyGraph.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.backIcon.setOnClickListener {
            val intent = Intent(this@SpyCam, MainActivity::class.java)
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

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_MAGNETIC_FIELD ){
            val degree = event.values[0]
            val magX = event.values[0]
            val magY = event.values[1]
            val magZ = event.values[2]
            val magnitude = sqrt((magX*magX + magY*magY + magZ+magZ).toDouble())

            updateLineChart(event,magnitude.toFloat())
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
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        var DECIMAL_FORMATTER : DecimalFormat? = null
    }

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
        dataSet.setDrawValues(true)
        dataSet.setDrawVerticalHighlightIndicator(true)
        dataSet.setDrawHorizontalHighlightIndicator(true)
        binding.spyGraph.axisLeft.setDrawGridLines(true)
        binding.spyGraph.axisRight.setDrawGridLines(true)
        binding.spyGraph.xAxis.setDrawGridLines(true)
        binding.spyGraph.background=ContextCompat.getDrawable(this, R.color.chartBackground)
        binding.spyGraph.setDrawBorders(true)
        binding.spyGraph.isAutoScaleMinMaxEnabled = true
        val leftAxsis: YAxis = binding.spyGraph.axisLeft
        leftAxsis.isEnabled = true
        val rightAxis : YAxis = binding.spyGraph.axisRight
        rightAxis.isEnabled = true
        val xAxis : XAxis = binding.spyGraph.xAxis
        xAxis.isEnabled = true


        val legend: Legend = binding.spyGraph.legend
        legend.isEnabled = false
        binding.spyGraph.description.text = ""
        val lineData = LineData(dataSet)
        binding.spyGraph.data = lineData
        binding.spyGraph.invalidate() // Refresh the chart
    }
    private fun updateLineChart(event: SensorEvent, value: Float) {
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
