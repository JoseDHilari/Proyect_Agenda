package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarRecordatorioActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_recordatorio)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = Nombre
        }

        findViewById<ImageButton>(R.id.FinishRecordatorio).setOnClickListener {
            setAlarm(idCurso)
            finish()
        }

        findViewById<ImageButton>(R.id.CancelRecordatorio).setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(idCurso: Int) {
        val datePicker = findViewById<CalendarView>(R.id.RecordatorioDate)
        val timePicker = findViewById<TimePicker>(R.id.RecordatorioHour)
        val activityName = findViewById<EditText>(R.id.AlarmaName).text.toString()
        val activateAlarm = findViewById<Switch>(R.id.BoolAlarma).isChecked

        val calendar = Calendar.getInstance().apply {
            timeInMillis = datePicker.date
            set(Calendar.HOUR_OF_DAY, timePicker.hour)
            set(Calendar.MINUTE, timePicker.minute)
            set(Calendar.SECOND, 0)
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = dateFormat.format(calendar.time)
        val time = timeFormat.format(calendar.time)

        val idAlarma = dbHelper.insertAlarma(idCurso, time, date, activityName, if (activateAlarm) 1 else 0)

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("idAlarma", idAlarma)
            putExtra("activityName", activityName)
            putExtra("time", time)
            putExtra("date", date)
            putExtra("activateAlarm", activateAlarm)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, idAlarma.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this, "Recordatorio establecido", Toast.LENGTH_SHORT).show()
    }
}
