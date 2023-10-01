package com.mc2023.template

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.*
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

var setHour: Int = 0;
var setMinute:Int = 0;
var setHour2: Int = 0;
var setMinute2:Int = 0;
class Fragment1 : Fragment(),View.OnClickListener {
    lateinit var button1 :Button
    lateinit var button2 :Button
    lateinit var startButton :Button
    lateinit var stopButton :Button
    lateinit var timeChosen:TextView
    lateinit var timeChosen2:TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : ViewGroup= inflater.inflate(R.layout.fragment_1, container, false) as ViewGroup
        timeChosen= view.findViewById(R.id.chosenTime)
        timeChosen2= view.findViewById(R.id.chosenTime2)
        button1 = view.findViewById(R.id.btn_choose)
        button2 = view.findViewById(R.id.btn_choose2)
        startButton = view.findViewById(R.id.start_service)
        stopButton = view.findViewById(R.id.stop_service)

        startButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)

        val bRB:BroadcastReceiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                when(intent?.action){
                    Intent.ACTION_BATTERY_LOW->{
                        println("Battery low")
                        activity?.stopService(Intent(activity,MusicService::class.java))
                        Toast.makeText(context,"Battery Low!", Toast.LENGTH_SHORT).show();
                    }
                    Intent.ACTION_BATTERY_OKAY->{
                        println("Battery Okay")
                        activity?.stopService(Intent(activity,MusicService::class.java))
                        Toast.makeText(context,"Battery Okay!", Toast.LENGTH_SHORT).show();
                    }
                }
                when(intent?.action){
                    Intent.ACTION_POWER_CONNECTED->{
                        println("Power is Connected!")
                        activity?.stopService(Intent(activity,MusicService::class.java))
                        Toast.makeText(context,"Power is Connected!", Toast.LENGTH_SHORT).show();

                    }
                    Intent.ACTION_POWER_DISCONNECTED->{
                        println("Power is Disconnected!")
                        Toast.makeText(context,"Power is Disconnected!", Toast.LENGTH_SHORT).show();

                    }
                }
            }


        }
        activity?.registerReceiver(bRB, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        activity?.registerReceiver(bRB, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        activity?.registerReceiver(bRB, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))


        button1.setOnClickListener{
            val c: Calendar = Calendar.getInstance()
            val hour:Int= c.get(Calendar.HOUR_OF_DAY)
            val minutes:Int= c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this.context, { _, hourOfDay, minute ->
                timeChosen.text = "$hourOfDay:$minute"
                setMinute=minute
                setHour=hourOfDay
                },
                hour,
                minutes,
                false
            )
            timePickerDialog.show();
        }

        button2.setOnClickListener{
                    val c: Calendar = Calendar.getInstance()
                    val hour:Int= c.get(Calendar.HOUR_OF_DAY)
                    val minutes:Int= c.get(Calendar.MINUTE)

                    val timePickerDialog = TimePickerDialog(this.context, { _, hourOfDay, minute ->
                        timeChosen2.text = "$hourOfDay:$minute"
                        setMinute2=minute
                        setHour2=hourOfDay
                        },
                        hour,
                        minutes,
                        false
                    )
                    timePickerDialog.show();
                }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id==R.id.start_service){
                Toast.makeText(this.context, "Service Started!",Toast.LENGTH_SHORT).show()
                Log.i(ContentValues.TAG,"Service started.")
                if(setHour==0){
                    setAlarm2()
                }else if(setHour2==0){
                    setAlarm()
                }else{
                    val t1:Int = setHour*60 + setMinute
                    val t2:Int = setHour2*60 + setMinute2
                    if(t1<t2){
                        setAlarm()
                        var x=0;
                        object : CountDownTimer(10000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                x++;
                                println("x: $x")
                            }

                            override fun onFinish() {
                                activity?.stopService(Intent(activity,MusicService::class.java))
                                Toast.makeText(context, "Alarm Ringing Stopped!!!",Toast.LENGTH_SHORT).show()
                                Log.i(ContentValues.TAG,"Alarm Ringing stopped.")
                                setAlarm2()
//                                Thread.sleep(10000)
//                                activity?.stopService(Intent(activity,MusicService::class.java))
                            }
                        }.start()

                    }else{
                        setAlarm2()
                        var x=0;
                        object : CountDownTimer(10000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                x++;
                                println("x: $x")
                            }

                            override fun onFinish() {
                                activity?.stopService(Intent(activity,MusicService::class.java))
                                Toast.makeText(context, "Alarm Ringing Stopped!!!",Toast.LENGTH_SHORT).show()
                                Log.i(ContentValues.TAG,"Alarm Ringing stopped.")
                                setAlarm()
                            }
                        }.start()

                    }

                }
            }
            if(v.id==R.id.stop_service){
                activity?.stopService(Intent(activity,MusicService::class.java))
                Toast.makeText(this.context, "Service Stopped",Toast.LENGTH_SHORT).show()
                Log.i(ContentValues.TAG,"Service stopped.")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarm(){
//        Toast.makeText(this.context, "Service 1 Started",Toast.LENGTH_SHORT).show()
        while(true){
            println("current hour and minute : "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+" " +Calendar.getInstance().get(Calendar.MINUTE))
            println("shivansh hour: $setHour,shivansh minutes: $setMinute")
            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ==setHour && Calendar.getInstance().get(Calendar.MINUTE)==setMinute){
                Toast.makeText(this.context,"Alarm Ringing started!!", Toast.LENGTH_SHORT).show()
                Log.i(ContentValues.TAG,"Alarm Ringing started.")
                activity?.startService(Intent(activity,MusicService:: class.java))
                break;
            }

            Thread.sleep(10000);

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarm2(){
//        Toast.makeText(this.context, "Service 2 Started",Toast.LENGTH_SHORT).show()
        while(true){
            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)==setHour2 && Calendar.getInstance().get(Calendar.MINUTE)==setMinute2){
                Toast.makeText(this.context,"Alarm Ringing started!!", Toast.LENGTH_SHORT).show()
                Log.i(ContentValues.TAG,"Alarm Ringing started.")
                activity?.startService(Intent(activity,MusicService:: class.java))
                break;
            }
            Thread.sleep(10000)
        }
    }
}