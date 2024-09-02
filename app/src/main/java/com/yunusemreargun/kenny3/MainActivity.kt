package com.yunusemreargun.kenny3

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yunusemreargun.kenny3.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())

    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
//ImageArray
        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView10)
        imageArray.add(binding.imageView11)
        imageArray.add(binding.imageView12)



        hideImages()

        //CountDownTimer

        object : CountDownTimer(15555, 1000) {
            override fun onTick(p0: Long) {
                binding.TimeText.text = "Time:${p0 / 1000}"
            }

            override fun onFinish() {
                binding.TimeText.text = "Time is up!"
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                // alertDialog
                val alert = AlertDialog.Builder(this@MainActivity)

                alert.setTitle("Game Over!")
                alert.setMessage("Your Score:  ${score}\n Restart to Game?")
                alert.setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        //restart
                        val intentFromMain = intent
                        finish()
                        startActivity(intentFromMain)

                    })

                alert.setNeutralButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG).show()

                })
                alert.show()
            }


        }.start()
    }

    fun hideImages() {


        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE

                }

                val random = Random
                val randomIndex = random.nextInt(imageArray.size)

                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)

            }

        }

        handler.post(runnable)
    }

    fun increaseScore(view: View) {
        score = score + 1
        binding.ScoreText.text = "Score:${score}"
    }
}




