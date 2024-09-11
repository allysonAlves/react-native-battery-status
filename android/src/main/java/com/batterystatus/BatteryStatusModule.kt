package com.batterystatus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class BatteryStatusModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private var batteryLevelReceiver: BroadcastReceiver? = null
  private var listenersCount = 0

  override fun getName(): String {
    return NAME
  }

  // Método para buscar o status da bateria
  @ReactMethod
  fun getBatteryStatusAsync(promise: Promise) {
    val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    val batteryStatus: Intent? = reactApplicationContext.registerReceiver(null, intentFilter)

    val batteryLevel = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val batteryScale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

    if (batteryLevel == -1 || batteryScale == -1) {
      promise.reject("BATTERY_ERROR", "Unable to get battery status")
    } else {
      val batteryPercentage = (batteryLevel / batteryScale.toFloat()) * 100
      promise.resolve(batteryPercentage)
    }
  }

  @ReactMethod
  fun addListener(eventName: String?) {
    if (listenersCount == 0 && batteryLevelReceiver == null) {
      batteryLevelReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
          val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
          val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
          val batteryPercentage = (level / scale.toFloat()) * 100

          reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit("onBatteryStatusChange", batteryPercentage)
        }
      }

      val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
      reactApplicationContext.registerReceiver(batteryLevelReceiver, filter)
    }
    listenersCount++
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    listenersCount -= count
    if (listenersCount == 0 && batteryLevelReceiver != null) {
      reactApplicationContext.unregisterReceiver(batteryLevelReceiver)
      batteryLevelReceiver = null
    }
  }

  companion object {
    const val NAME = "BatteryStatus"
  }
}
