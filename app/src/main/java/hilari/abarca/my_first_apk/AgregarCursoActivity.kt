package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class AgregarCursoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_curso)

        dbHelper = DatabaseHelper(this)
       //dbHelper.onUpgrade(null,1,2)

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

        // Manejar la selección del Spinner
        spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDay = parent.getItemAtPosition(position).toString()
                // Aquí puedes hacer cualquier cosa adicional que necesites cuando se seleccione un ítem.
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Aquí puedes manejar el caso en el que no se seleccione nada.
            }
        }

        findViewById<ImageButton>(R.id.CancelNewCourse).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.FinishNewCourse).setOnClickListener {
            saveDataToDatabase(idCurso)
            val intent = Intent(this, HorarioActivity::class.java)
            startActivity(intent)
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
            val newDiaId = dbHelper.insertDay(CourseId, selectedDay, startHour, endHour)
            if (newDiaId != -1L) {
                Toast.makeText(this, "Nuevo día guardado exitosamente", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("AgregarCursoActivity", "Error saving data to database", e)
            Toast.makeText(this, "Error al guardar los datos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
