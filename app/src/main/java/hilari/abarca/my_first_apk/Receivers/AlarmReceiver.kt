package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val idAlarma = intent?.getIntExtra("idAlarma", -1) ?: -1
        val activityName = intent?.getStringExtra("activityName")
        val time = intent?.getStringExtra("time")
        val date = intent?.getStringExtra("date")
        val activateAlarm = intent?.getBooleanExtra("activateAlarm", false) ?: false

        val notificationIntent = Intent(context, RecordatorioActivity::class.java).apply {
            putExtra("idCurso", idAlarma)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = "recordatorio_channel"
        val channelName = "Recordatorio"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = if (activateAlarm) NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Canal para recordatorios"
            }
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Recordatorio")
            .setContentText("¡Recordatorio: $activityName a las $time el $date!")
            .setPriority(if (activateAlarm) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (activateAlarm) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            notificationBuilder.setSound(alarmSound)
        }

        with(NotificationManagerCompat.from(context)) {
            notify(idAlarma, notificationBuilder.build())
        }
    }
}
