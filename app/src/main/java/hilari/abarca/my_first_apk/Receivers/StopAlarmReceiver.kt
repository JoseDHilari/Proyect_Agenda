package hilari.abarca.my_first_apk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import hilari.abarca.my_first_apk.Receivers.AlarmSoundManager

class StopAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Detener el sonido de la alarma
        AlarmSoundManager.stopRingtone()
        Log.d("StopAlarmReceiver", "Alarm stopped")
    }
}
