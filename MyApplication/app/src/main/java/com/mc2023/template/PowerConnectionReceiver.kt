package com.mc2023.template;

import android.content.BroadcastReceiver;
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class PowerConnectionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        TODO("Not yet implemented")
        // Inflate the layout for this fragment
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context?.registerReceiver(null, ifilter)
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

// How are we charging?
        val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
    }
}
