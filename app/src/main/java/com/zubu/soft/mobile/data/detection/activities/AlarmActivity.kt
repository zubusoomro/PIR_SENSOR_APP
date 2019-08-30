package com.zubu.soft.mobile.data.detection.activities

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zubu.soft.mobile.data.detection.R
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*


class AlarmActivity : AppCompatActivity() {
    companion object {
        var isActivityRunning = false
    }

    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
//        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "NRF:detectionwakelock")
//        wakeLock.acquire()
//        this.window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN or
//                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
//                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
//                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
//            (WindowManager.LayoutParams.FLAG_FULLSCREEN or
//                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
//                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
//                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
//        )
        setContentView(R.layout.activity_alarm)
        initialize()
        playAlarm()
    }

    private fun initialize() {
        if (intent.hasExtra("device_name"))
            tv_SensorName.text = intent.getStringExtra("device_name")
        var dObject = Calendar.getInstance().time
        tv_timeStamp.text = java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dObject)

        btn_turnOfAlarm.setOnClickListener {
            mMediaPlayer?.stop()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        isActivityRunning = true
    }

    override fun onStop() {
        super.onStop()
        isActivityRunning = false
        mMediaPlayer?.stop()
        mMediaPlayer = null
    }

    private fun playAlarm() {
        try {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setDataSource(this@AlarmActivity, getMediaUri())
            mMediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mMediaPlayer?.isLooping = true
            mMediaPlayer?.prepareAsync()
            mMediaPlayer?.setOnPreparedListener {
                it.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMediaUri(): Uri {
        var uri: Uri? = null
        var uriString =
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE).getString("tone_uri", null)

        if (uriString != null)
            uri = Uri.parse(uriString)

        if (uri == null)
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (uri == null)
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        if (uri == null)
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        return uri!!
    }

    override fun onPause() {
        super.onPause()
        isActivityRunning = false
    }
}
