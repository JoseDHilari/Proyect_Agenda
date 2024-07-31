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

        try {
            var listaCursos2 = dbHelper.ListarCursos()
            Log.i("Jose",dbHelper.ListarCursos().toString())
            cursosAdaptador.actualizarLista(listaCursos2)

        }
        catch (e:Exception){
            Log.i("Jose",e.toString())
        }

        //cursosAdaptador.actualizarLista(listaCursos)

        findViewById<ImageButton>(R.id.NewCourse).setOnClickListener {
            val intent = Intent(this, AgregarCursoActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.ViewCalendario).setOnClickListener {
            val intent = Intent(this, CalendarioActivity::class.java)
            startActivity(intent)
        }
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
                selectedDateText.text = selectedDate
                // Aquí puedes filtrar los cursos basándote en la fecha seleccionada
                //filterCoursesByDate(selectedDay, selectedMonth + 1, selectedYear)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
    /*
    private fun filterCoursesByDate(day: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        val listaCursos = dbHelper.ListarCursosPorDia(dayOfWeek)
        Log.i("HorarioActivity", "Lista de cursos filtrados: $listaCursos")
        cursosAdaptador.actualizarLista(listaCursos)
    }*/
}
