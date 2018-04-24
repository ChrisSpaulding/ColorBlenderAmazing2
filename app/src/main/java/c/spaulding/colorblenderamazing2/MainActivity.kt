package c.spaulding.colorblenderamazing2

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
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

        colorView.setBackgroundColor(Color.argb(alpha,redOne,blueOne,greenOne))

        colorMixBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (colorMixBar.progress) {
                    100 -> colorView.setBackgroundColor(Color.argb(alpha, redTwo, greenTwo, blueTwo))
                    0 -> colorView.setBackgroundColor(Color.argb(alpha, redOne, greenOne, blueOne))
                    else -> colorView.setBackgroundColor(Color.argb(alpha,
                            ((redTwo*colorMixBar.progress/100)+(redOne*(100-colorMixBar.progress)/100)),
                            ((greenTwo*colorMixBar.progress/100)+(greenOne*(100-colorMixBar.progress)/100)),
                            ((blueTwo*colorMixBar.progress/100)+(blueOne*(100-colorMixBar.progress)/100))))
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
