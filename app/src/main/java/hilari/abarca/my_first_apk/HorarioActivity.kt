package hilari.abarca.my_first_apk

import android.app.DatePickerDialog
import android.app.job.JobService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaCursosAdapter
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper
import hilari.abarca.my_first_apk.Models.CursosModel
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

        val listaCursos: MutableList<CursosModel> = mutableListOf()
/*
        listaCursos.add(CursosModel("7:00", "9:00", "Primer Curso", true))
        listaCursos.add(CursosModel("9:00", "11:00", "Segundo Curso", true))
        listaCursos.add(CursosModel("11:00", "13:00", "Primer Curso", true))
        listaCursos.add(CursosModel("13:00", "15:00", "Segundo Curso", true))
        listaCursos.add(CursosModel("15:00", "17:00", "Tercer Curso", true))
        listaCursos.add(CursosModel("17:00", "19:00", "Primer Curso", true))
        listaCursos.add(CursosModel("19:00", "21:00", "Segundo Curso", true))
        listaCursos.add(CursosModel("21:00", "23:00", "Tercer Curso", true))
*/
        try {
            var listaCursos2 = dbHelper.ListarCuros()
            Log.i("Jose",dbHelper.ListarCuros().toString())
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

    private fun filterCoursesByDate(day: Int, month: Int, year: Int) {
        // Implementa la lógica para filtrar los cursos basándote en la fecha
        // y actualiza la lista de cursos mostrada en el RecyclerView
    }
}
