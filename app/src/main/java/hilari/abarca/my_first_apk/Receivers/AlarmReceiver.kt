package hilari.abarca.my_first_apk

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import hilari.abarca.my_first_apk.Receivers.AlarmSoundManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val idAlarma = intent.getLongExtra("idAlarma", -1)
        val activityName = intent.getStringExtra("activityName")
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        val activateAlarm = intent.getBooleanExtra("activateAlarm", false)

        // Mostrar la notificación
        showNotification(context, activityName, time, date, idAlarma)

        if (activateAlarm) {
            // Reproducir sonido de alarma
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val ringtone = RingtoneManager.getRingtone(context, alarmUri)
            AlarmSoundManager.ringtone = ringtone
            AlarmSoundManager.ringtone?.play()
            Log.d("AlarmReceiver", "Alarm received for: $activityName at $time on $date")
        }
    }

    private fun showNotification(context: Context, activityName: String?, time: String?, date: String?, idAlarma: Long) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = idAlarma.toInt()

        val stopAlarmIntent = Intent(context, StopAlarmReceiver::class.java).apply {
            putExtra("idAlarma", idAlarma)
        }
        val stopAlarmPendingIntent = PendingIntent.getBroadcast(context, 0, stopAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "ALARM_CHANNEL")
            .setSmallIcon(R.drawable.ic_alarma)  // Asegúrate de que este nombre coincide con tu icono
            .setContentTitle("Recordatorio")
            .setContentText("Tienes una actividad: $activityName el $date a las $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(stopAlarmPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
