package hilari.abarca.my_first_apk

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class AgregarNuevaHoraActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nueva_hora)

        dbHelper = DatabaseHelper(this)

        val courseName = intent.getStringExtra("courseName")
        findViewById<TextView>(R.id.CourseName).text = courseName

        val spinnerDay = findViewById<Spinner>(R.id.Day)
        val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        val dayAdapter = ArrayAdapter(this, R.layout.spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter

        findViewById<ImageButton>(R.id.CancelNewCourse).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.FinishNewCourse).setOnClickListener {
            saveDataToDatabase(courseName!!)
            finish()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveDataToDatabase(courseName: String) {
        val db = dbHelper.writableDatabase

        val spinnerDay = findViewById<Spinner>(R.id.Day)
        val selectedDay = spinnerDay.selectedItem.toString()
        val startHourPicker = findViewById<TimePicker>(R.id.StartHour)
        val endHourPicker = findViewById<TimePicker>(R.id.EndHour)

        val startHour = "${startHourPicker.hour}:${startHourPicker.minute}"
        val endHour = "${endHourPicker.hour}:${endHourPicker.minute}"

        val courseIdCursor = db.query(
            "Curso",
            arrayOf("id"),
            "Nombre = ?",
            arrayOf(courseName),
            null,
            null,
            null
        )

        if (courseIdCursor.moveToFirst()) {
            val courseId = courseIdCursor.getInt(courseIdCursor.getColumnIndexOrThrow("id"))
            val diaValues = ContentValues().apply {
                put("idCurso", courseId)
                put("Dia", selectedDay)
                put("Hora_Inicio", startHour)
                put("Hora_Final", endHour)
            }
            db.insert("Dias", null, diaValues)
        }
        courseIdCursor.close()

    }
}
