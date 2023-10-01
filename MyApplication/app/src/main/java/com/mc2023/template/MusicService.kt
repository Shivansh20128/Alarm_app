package com.mc2023.template

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MusicService: Service() {
    private lateinit var musicPlayer:MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate(){
        musicPlayer = MediaPlayer.create(applicationContext,R.raw.animals)
        musicPlayer.isLooping=false
    }

//    override fun onStart(intent: Intent?, startId: Int) {
//        Toast.makeText(this@MusicService, "State of activity MainActivity changed from OnCreate/onRestart to onStart", Toast.LENGTH_SHORT).show()
//        musicPlayer.start()
//        Log.i(TAG,"State of activity MainActivity changed from OnCreate/onRestart to onStart")
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Toast.makeText(this@MusicService, "State of activity MainActivity changed from OnCreate/onRestart to onStart", Toast.LENGTH_SHORT).show()
        musicPlayer.start()
        Log.i(TAG,"State of activity MainActivity changed from OnCreate/onRestart to onStart")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        musicPlayer.stop()
        Log.i(TAG,"State of Activity MainActivity changed from onStop to onDestroy")
//        Toast.makeText(this@MusicService, "State of Activity MainActivity changed from onStop to onDestroy", Toast.LENGTH_SHORT).show()
    }


}