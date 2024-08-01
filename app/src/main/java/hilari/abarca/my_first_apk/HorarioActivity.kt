package hilari.abarca.my_first_apk

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaCursosAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import java.util.Calendar
import java.util.Locale

class HorarioActivity : AppCompatActivity() {

    private lateinit var datePickerButton: Button
    private lateinit var selectedDateText: TextView
    private lateinit var cursosAdaptador: ListaCursosAdapter
    private lateinit var dbHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

        dbHelper = DatabaseHelper(this)
        datePickerButton = findViewById(R.id.datePickerButton)
        selectedDateText = findViewById(R.id.textView)

        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }

        cursosAdaptador = ListaCursosAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_Curso)
        rv.apply {
            adapter = cursosAdaptador
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        // Mostrar la fecha actual y los cursos del día actual
        mostrarCursosDelDiaActual()

        findViewById<ImageButton>(R.id.NewCourse).setOnClickListener {
            val intent = Intent(this, AgregarCursoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_COURSE)
        }

        findViewById<ImageButton>(R.id.ViewCalendario).setOnClickListener {
            val intent = Intent(this, CalendarioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarCursosDelDiaActual() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val currentDate = "$day/${month + 1}/$year"
        val dayOfWeek = getDayOfWeek(year, month, day)
        selectedDateText.text = "$currentDate - $dayOfWeek"

        filterCoursesByDay(dayOfWeek)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                val dayOfWeek = getDayOfWeek(selectedYear, selectedMonth, selectedDay)
                selectedDateText.text = "$selectedDate - $dayOfWeek"

                filterCoursesByDay(dayOfWeek)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun getDayOfWeek(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())?.replaceFirstChar { it.uppercase() } ?: ""
    }

    private fun filterCoursesByDay(dayOfWeek: String) {
        val listaCursos = dbHelper.ListarCursos(dayOfWeek)
        Log.i("HorarioActivity", "Lista de cursos filtrados: $listaCursos")
        cursosAdaptador.actualizarLista(listaCursos)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_COURSE && resultCode == RESULT_OK) {
            // Actualiza la lista de cursos
            mostrarCursosDelDiaActual()
        }
    }
    companion object {
        const val REQUEST_CODE_ADD_COURSE = 1
    }
}
