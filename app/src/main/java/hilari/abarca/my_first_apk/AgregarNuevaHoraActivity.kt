package hilari.abarca.my_first_apk

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class AgregarNuevaHoraActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nueva_hora)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }


        val spinnerDay = findViewById<Spinner>(R.id.Day)
        val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        val dayAdapter = ArrayAdapter(this, R.layout.spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter

        findViewById<ImageButton>(R.id.CancelNewCourse).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.FinishNewCourse).setOnClickListener {
            saveDataToDatabase(idCurso)
            finish()
        }
    }

    private fun saveDataToDatabase(idCurso:Int) {
        val CourseId = idCurso
        val spinnerDay = findViewById<Spinner>(R.id.Day)
        val selectedDay = spinnerDay.selectedItem.toString()
        val startHourPicker = findViewById<TimePicker>(R.id.StartHour)
        val endHourPicker = findViewById<TimePicker>(R.id.EndHour)
        val startHour = "${startHourPicker.hour}:${startHourPicker.minute}"
        val endHour = "${endHourPicker.hour}:${endHourPicker.minute}"

        try {
            if (selectedDay.isBlank()) throw IllegalArgumentException("Debe seleccionar un día")

            val newDiaId = dbHelper.insertDay(CourseId, selectedDay, startHour, endHour)
            if (newDiaId != -1L) {
                Toast.makeText(this, "Nuevo dia guardado exitosamente", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Log.e("AgregarCursoActivity", "Error saving data to database", e)
            Toast.makeText(this, "Error al guardar los datos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
