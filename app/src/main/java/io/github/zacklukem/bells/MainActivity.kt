package io.github.zacklukem.bells

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Calendar

class MainActivity : AppCompatActivity(), Runnable {

    private var timeText: TextView? = null
    private var unitText: TextView? = null
    private var updateThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scheduleViewBtn = findViewById<Button>(R.id.scheduleViewBtn)

        scheduleViewBtn.setOnClickListener { startActivity(Intent(this@MainActivity, ScheduleView::class.java)) }

        timeText = findViewById(R.id.timeText)
        unitText = findViewById(R.id.unitText)

        updateThread = Thread(this, "bells_time_thread")
        updateThread!!.start()

    }

    override fun run() {
        try {
            while (!updateThread!!.isInterrupted) {
                Thread.currentThread()
                Thread.sleep(500)
                runOnUiThread {
                    val time = Calendar.getInstance()
                    val hour = time.get(Calendar.HOUR_OF_DAY)
                    val minute = time.get(Calendar.MINUTE)
                    val second = time.get(Calendar.SECOND)
                    val next = schedule.getNext(Schedule.Time(hour, minute))
                    val nextTime = next.first
                    val secTime = hour * 60 * 60 + minute * 60 + second
                    val secNTime = nextTime.hour * 60 * 60 + nextTime.minute * 60
                    var n = secNTime - secTime

                    val day = n / (24 * 3600)

                    n = n % (24 * 3600)
                    val uHour = n / 3600

                    n %= 3600
                    val uMinute = n / 60

                    n %= 60
                    val uSecond = n

                    //                        int uHour = (int) Math.floor((secUTime % (24.0d * 3600.0d)) / 3600.0d);
                    //                        int uMin = (int) Math.floor((secUTime % (24.0d * 3600.0d * 3600.0d)) / 60.0d);
                    //                        int uSec = (int) Math.floor((secUTime % (24.0d * 3600.0d * 3600.0d * 60.0d)) / 60.0d);
                    var text = ""
                    if (uHour != 0) {
                        text += uHour.toString() + "h "
                    }
                    if (uMinute != 0) {
                        text += uMinute.toString() + "m "
                    }
                    if (uSecond != 0) {
                        text += uSecond.toString() + "s"
                    }
                    if (next.second == "Schools out") {
                        timeText!!.text = ""
                    } else {
                        timeText!!.text = text
                    }
                    unitText!!.text = next.second
                }
            }
        } catch (e: InterruptedException) {
        }

    }

    companion object {
        var schedule: Schedule = Schedule.mv_bells
    }

}
