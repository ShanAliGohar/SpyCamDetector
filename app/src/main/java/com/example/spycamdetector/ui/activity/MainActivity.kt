package com.example.spycamdetector.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.spycamdetector.R
import com.example.spycamdetector.databinding.ActivityMainBinding
import com.example.spycamdetector.sharedPref.SaveData
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private var sensorManager: SensorManager? = null

    var saveData: SaveData? = null
    var starOne: ImageView? = null
    var starTwo: ImageView? = null
    var starThree: ImageView? = null
    var starFour: ImageView? = null
    var starFive: ImageView? = null
    var close: ImageView? = null
    var constrainYes: ConstraintLayout? = null
    var constrainCancel: ConstraintLayout? = null
    var yes: TextView? = null
    var no: TextView? = null
    var drawer: NavigationBarView? = null
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var hiddenCamera: ImageView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.darkMood)
        saveData = SaveData(this)
        if (saveData!!.loadDarkModeState()) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // hiddenCamera = findViewById(R.id.hiddenCamera)
        navigateToNext()
        if (saveData!!.loadDarkModeState()) {
            binding.switch2.isChecked = true
        }
        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveData!!.setDarkMoodState(true)
                restartApplication()
            } else {
                saveData!!.setDarkMoodState(false)
                restartApplication()
            }
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        }

        binding.rateUs.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            spyRateLayout()
        }


        binding.buy.setOnClickListener {
            val intent = Intent(this@MainActivity, BuyPremium::class.java)
            startActivity(intent)
        }

        binding.childCardTwo.setOnClickListener {
            val intent = Intent(this@MainActivity, FAQsHelp::class.java)
            startActivity(intent)
        }

        binding.childCardTwo.setOnClickListener {
            val intent = Intent(this@MainActivity, FAQsHelp::class.java)
            startActivity(intent)
        }

        binding.share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, check out this amazing app!")

            val title = "Share via"
            val chooser = Intent.createChooser(shareIntent, title)
            startActivity(chooser)
        }
        binding.tipsTricks.setOnClickListener {
            val intent = Intent(this, TipsTricks::class.java)
            startActivity(intent)
        }
        binding.childCardOne.setOnClickListener {
            val intent = Intent(this, TipsTricks::class.java)
            startActivity(intent)
        }

    }

    fun restartApplication() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun navigateToNext() {
        with(binding) {
            hiddenCamera.setOnClickListener {
                    val intent = Intent(this@MainActivity, HiddenCamera::class.java)
                    startActivity(intent)
            }
            infraredCameraIcon.setOnClickListener {
                    val intent = Intent(this@MainActivity, InfraredCamera::class.java)
                    startActivity(intent)
                }
                wiredCameraDetector.setOnClickListener {
                        val intent = Intent(this@MainActivity, WiredCamera::class.java)
                        startActivity(intent)
                    }
                spyCameraDetector.setOnClickListener {
                    val intent = Intent(this@MainActivity, SpyCam::class.java)
                    startActivity(intent)
                }
                matelCameraDetector.setOnClickListener {
                        val intent = Intent(this@MainActivity, MetalDetector::class.java)
                        startActivity(intent)
                }
                objectDetector.setOnClickListener {
                    val intent = Intent(this@MainActivity, ObjectDetector::class.java)
                    startActivity(intent)
                }
                menuIcon.setOnClickListener {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START)
                    }
                }
                premiumStarIcon.setOnClickListener {
                    val intent = Intent(this@MainActivity, BuyPremium::class.java)
                    startActivity(intent)
                }

            }
        }
        @SuppressLint("MissingInflatedId")
        fun spyRateLayout() {
            val dialogBuilder = AlertDialog.Builder(this)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.spylayout, null)
            dialogBuilder.setView(dialogView)
            val dialog = dialogBuilder.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            starOne = dialogView.findViewById<ImageView>(R.id.starOne)
            starTwo = dialogView.findViewById<ImageView>(R.id.starTwo)
            starThree = dialogView.findViewById<ImageView>(R.id.starThree)
            starFour = dialogView.findViewById<ImageView>(R.id.starFour)
            starFive = dialogView.findViewById<ImageView>(R.id.starFive)
            close = dialogView.findViewById<ImageView>(R.id.closeBtn)

            starOne?.setOnClickListener {
                starOne?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starTwo?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                starThree?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.rating_blank
                    )
                )
                starFour?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                starFive?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                dialog.dismiss()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show()

            }
            starTwo?.setOnClickListener {
                starOne?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starTwo?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starThree?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.rating_blank
                    )
                )
                starFour?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                starFive?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                dialog.dismiss()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show()

            }
            starThree?.setOnClickListener {
                starOne?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starTwo?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starThree?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starFour?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                starFive?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                dialog.dismiss()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show()

            }
            starFour?.setOnClickListener {
                starOne?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starTwo?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starThree?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starFour?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starFive?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_blank))
                dialog.dismiss()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show()
            }
            starFive?.setOnClickListener {
                starOne?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starTwo?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starThree?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starFour?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                starFive?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rating_fill))
                dialog.dismiss()
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + "com.spy.hidden.camera.detector")
                    )
                )
            }
            close?.setOnClickListener {
                dialog.dismiss()
            }
        }

        override fun onBackPressed() {
            val dialogBuilder = AlertDialog.Builder(this)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.exit_dialouge, null)
            dialogBuilder.setView(dialogView)
            val dialog = dialogBuilder.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            constrainYes = dialogView.findViewById(R.id.btnYes)
            constrainCancel = dialogView.findViewById(R.id.btnNo)
            yes = dialogView.findViewById(R.id.yesTxt)
            no = dialogView.findViewById(R.id.cancelTxt)

            constrainYes?.setOnClickListener {
                constrainYes?.setBackgroundResource(R.drawable.exit_yes_fill)
                yes?.setTextColor(getColor(R.color.white))

                constrainCancel?.setBackgroundResource(R.drawable.exit_no_blank)
                no?.setTextColor(getColor(R.color.exitBlue))

                finishAffinity()
            }
            constrainCancel?.setOnClickListener {
                constrainCancel?.setBackgroundResource(R.drawable.exit_no_fill)
                no?.setTextColor(getColor(R.color.white))

                constrainYes?.setBackgroundResource(R.drawable.exit_yes_blank)
                yes?.setTextColor(getColor(R.color.exitBlue))

                dialog.dismiss()
            }
        }

}

