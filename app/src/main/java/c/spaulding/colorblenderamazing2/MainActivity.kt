package c.spaulding.colorblenderamazing2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val alpha = 255
    private val hundred = 100
    var redOne = 0
    var blueOne = 0
    var greenOne = 0
    var redTwo = 255
    var blueTwo = 255
    var greenTwo = 255

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        colorMixBar.max = hundred

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        setBackgroundWSharedPref(sharedPreferences)
        getNewColorsWithIntents()
        saveColorsInSharedPrefs(sharedPreferences)

        colorView.setBackgroundColor(Color.argb(alpha,redOne,blueOne,greenOne))

        setBackgroundOnSlider()

        colorOne.setOnClickListener{
            openColorPickerApp(12, 1)
        }
        colorTwo.setOnClickListener {
            openColorPickerApp(12,2)
        }
    }

    private fun saveColorsInSharedPrefs(sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.putInt("redOne", 0)
        editor.putInt("blueOne", 0)
        editor.putInt("greenOne", 0)
        editor.putInt("redTwo", 255)
        editor.putInt("blueTwo", 255)
        editor.putInt("greenTwo", 255)
    }

    private fun setBackgroundOnSlider() {
        colorMixBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (colorMixBar.progress) {
                    100 -> colorView.setBackgroundColor(Color.argb(alpha, redTwo, greenTwo, blueTwo))
                    0 -> colorView.setBackgroundColor(Color.argb(alpha, redOne, greenOne, blueOne))
                    else -> colorView.setBackgroundColor(Color.argb(alpha,
                            ((redTwo * colorMixBar.progress / 100) + (redOne * (100 - colorMixBar.progress) / 100)),
                            ((greenTwo * colorMixBar.progress / 100) + (greenOne * (100 - colorMixBar.progress) / 100)),
                            ((blueTwo * colorMixBar.progress / 100) + (blueOne * (100 - colorMixBar.progress) / 100))))
                }
            }
        })
    }

    private fun setBackgroundWSharedPref(sharedPreferences: SharedPreferences) {
        redOne = sharedPreferences.getInt("redOne", 0)
        blueOne = sharedPreferences.getInt("blueOne", 0)
        greenOne = sharedPreferences.getInt("greenOne", 0)
        redTwo = sharedPreferences.getInt("redTwo", 255)
        blueTwo = sharedPreferences.getInt("blueTwo", 255)
        greenTwo = sharedPreferences.getInt("greenTwo", 255)
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        saveColorsInSharedPrefs(sharedPreferences)
    }

    fun openColorPickerApp( requestCode: Int, side : Int){
        val launchColorPicker = packageManager.getLaunchIntentForPackage("c.ebookfrenzy.colorpicker")  as Intent
        if (launchColorPicker != null) {
            launchColorPicker.putExtra("side", side)
            startActivityForResult(launchColorPicker, requestCode)
        } else {
            // Bring user to the market or let them choose an app?
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("market://details?id=" + packageName)
            startActivity(intent);
        }
    }

    fun getNewColorsWithIntents() {
        if(intent == null){
            Log.i("nullIntent", "NO INTENTS PASSED ~HULK")
        }
        else if(intent.getIntExtra("side", 0) == 1){
            redOne = intent.getIntExtra("red", 0)
            blueOne = intent.getIntExtra("blue", 0)
            greenOne = intent.getIntExtra("green", 0)
        }
        else if (intent.getIntExtra("side", 0) == 2){
            redTwo = intent.getIntExtra("red", 0)
            blueTwo = intent.getIntExtra("blue", 0)
            greenTwo = intent.getIntExtra("green", 0)
        }
    }
}
