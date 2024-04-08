package com.example.spycamdetector.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityInfraredCameraBinding
import com.example.spycamdetector.sharedPref.SaveData
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.filters.BlackAndWhiteFilter
import com.otaliastudios.cameraview.filters.InvertColorsFilter
import com.otaliastudios.cameraview.filters.SepiaFilter
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

class InfraredCamera : AppCompatActivity() {


    var bindingInfrared: ActivityInfraredCameraBinding? = null
    private val binding get() = bindingInfrared!!
    var saveData : SaveData? = null
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
        bindingInfrared = ActivityInfraredCameraBinding.inflate(layoutInflater)
        setContentView(bindingInfrared!!.root)

        listeners()
        binding.backIcon.setOnClickListener {
            val intent = Intent(this@InfraredCamera, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        }
    private fun infraCaptureImageCamera() {
        binding.infraredCamera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                val data = result.data
                val photo = File(
                    Environment.getExternalStorageDirectory(), Calendar.getInstance().timeInMillis.toString() + "photo.jpg"
                )
                try {
                    val fos = FileOutputStream(photo.path)
                    Toast.makeText(this@InfraredCamera, photo.path, Toast.LENGTH_SHORT).show()
                    fos.write(data)
                    fos.close()
                } catch (e: IOException) {
                }
            }
        })
        binding.infraredCamera.takePicture()
    }
    private fun infraFocusCamera() {
        binding.infraredCamera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
        binding.infraredCamera.addCameraListener(object : CameraListener() {
            override fun onAutoFocusStart(point: PointF) {
                super.onAutoFocusStart(point)
            }
        })
    }
    private fun listeners() {
        with(binding) {
            filterOne.setOnClickListener {
                val blackAndWhiteFilter = BlackAndWhiteFilter()
                binding.infraredCamera.filter = blackAndWhiteFilter
            }
            filterTwo.setOnClickListener {
                val sepiaFilter = SepiaFilter()
                binding.infraredCamera.filter = sepiaFilter
            }
            filterThree.setOnClickListener {
                val invertFilter = InvertColorsFilter()
                binding.infraredCamera.filter = invertFilter

            }
            infraredCamera.setOnClickListener {
                infraFocusCamera()
            }
            camBtn.setOnClickListener {
                infraCaptureImageCamera()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        binding.infraredCamera.open()
    }

    override fun onPause() {
        super.onPause()
        binding.infraredCamera.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.infraredCamera.destroy()
    }

}
