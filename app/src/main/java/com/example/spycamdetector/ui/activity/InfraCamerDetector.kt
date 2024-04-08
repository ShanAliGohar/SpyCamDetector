package com.example.spycamdetector.ui.activity

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spycamdetector.R
import com.example.spycamdetector.adapter.SpyEfffects_Adapter
import com.example.spycamdetector.databinding.ActivityInfraCamerDetectorBinding
import com.example.spycamdetector.sharedPref.SaveData
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.filters.BlackAndWhiteFilter
import com.otaliastudios.cameraview.filters.InvertColorsFilter
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

class InfraCamerDetector : AppCompatActivity() {
    var InfracameraViewnight: CameraView? = null
    var view: View? = null
    var arrayListInfra: ArrayList<String>? = null
    var spyEfffects_adapter: SpyEfffects_Adapter? = null
    var InfraimageVieweffect: ImageView? = null
    var linearLayoutManagerInfra: LinearLayoutManager? = null
    var recyclerView1Infra: RecyclerView? = null
    var imageArrayInfra: ArrayList<Int>? = null
    var bindingInfraMain: ActivityInfraCamerDetectorBinding? = null
    val bindingInfra get() = bindingInfraMain!!
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
        bindingInfraMain = ActivityInfraCamerDetectorBinding.inflate(
            layoutInflater
        )
        setContentView(bindingInfra.root)
        recyclerView1Infra = findViewById(R.id.recyclerVieweffects)
        InfracameraViewnight = findViewById(R.id.cameraViewNight)
        view = findViewById(R.id.cardViewNight)
        InfracameraViewnight?.setLifecycleOwner(this)
        val invertColorsFilter = InvertColorsFilter()
        InfracameraViewnight?.setFilter(invertColorsFilter)
        bindingInfra!!.imageBackInfra.setOnClickListener {
            onBackPressed()
            /* Intent intent=new Intent(InfraCamerDetector.this, MainActivity.class);
                    startActivity(intent);*/
        }
        InfracameraViewnight?.setPlaySounds(true)
        arrayListInfra = ArrayList()
        imageArrayInfra = ArrayList()
        arrayListInfra!!.add("Black and White")
        imageArrayInfra!!.add(R.drawable.infrared_filter_one)
        arrayListInfra!!.add("SepiaFilter")
        imageArrayInfra!!.add(R.drawable.infrared_filter_two)
        arrayListInfra!!.add("InvertColorsFilter")
        imageArrayInfra!!.add(R.drawable.infrared_filter_three)
        view?.setOnClickListener(View.OnClickListener { infraCaptureImageCamera() })

            recyclerView1Infra?.setVisibility(View.INVISIBLE)
            linearLayoutManagerInfra = LinearLayoutManager(applicationContext)
            recyclerView1Infra?.setLayoutManager(linearLayoutManagerInfra)
            spyEfffects_adapter = SpyEfffects_Adapter(
                arrayListInfra!!,
                imageArrayInfra!!,
                applicationContext,
                InfracameraViewnight!!
            )
            recyclerView1Infra?.setLayoutManager(
                LinearLayoutManager(
                    applicationContext,
                    LinearLayoutManager.HORIZONTAL,

                    false
                )
            )
            recyclerView1Infra?.setAdapter(spyEfffects_adapter)

        InfracameraViewnight?.setOnClickListener(View.OnClickListener { })
        infraFocusCamera()
    }

    private fun infraFocusCamera() {
        InfracameraViewnight!!.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
        InfracameraViewnight!!.addCameraListener(object : CameraListener() {
            override fun onAutoFocusStart(point: PointF) {
                recyclerView1Infra!!.visibility = View.GONE
                super.onAutoFocusStart(point)
            }
        })
    }

    private fun infraCaptureImageCamera() {
        InfracameraViewnight!!.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                val data = result.data
                val photo = File(
                    Environment.getExternalStorageDirectory(), Calendar.getInstance().timeInMillis.toString() + "photo.jpg"
                )
                try {
                    val fos = FileOutputStream(photo.path)
                    Toast.makeText(this@InfraCamerDetector, photo.path, Toast.LENGTH_SHORT).show()
                    fos.write(data)
                    fos.close()
                } catch (e: IOException) {
                }
            }
        })
        InfracameraViewnight!!.takePicture()
    }

    override fun onResume() {
        super.onResume()
        InfracameraViewnight!!.open()
    }

    override fun onPause() {
        super.onPause()
        InfracameraViewnight!!.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        InfracameraViewnight!!.destroy()
    }
}