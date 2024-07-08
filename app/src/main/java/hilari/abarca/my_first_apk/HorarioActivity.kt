package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaCursosAdapter
import hilari.abarca.my_first_apk.Models.CursosModel

class HorarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

        val CursosAdaptador = ListaCursosAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_Curso)
        rv.apply {
            adapter = CursosAdaptador
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        val ListaCursos : MutableList<CursosModel> = mutableListOf()

        ListaCursos.add(CursosModel("7:00","9:00","Primer Curso",true))
        ListaCursos.add(CursosModel("9:00","11:00","Segundo Curso",true))
        ListaCursos.add(CursosModel("11:00","13:00","Primer Curso",true))
        ListaCursos.add(CursosModel("13:00","15:00","Segundo Curso",true))
        ListaCursos.add(CursosModel("15:00","17:00","Tercer Curso",true))
        ListaCursos.add(CursosModel("17:00","19:00","Primer Curso",true))
        ListaCursos.add(CursosModel("19:00","21:00","Segundo Curso",true))
        ListaCursos.add(CursosModel("21:00","23:00","Tercer Curso",true))

        CursosAdaptador.actualizarLista(ListaCursos)



        // Obtener referencias a los Spinners
        val spinnerDay = findViewById<Spinner>(R.id.HorarioDay)
        val spinnerMonth = findViewById<Spinner>(R.id.HorarioMonth)
        val spinnerYear = findViewById<Spinner>(R.id.HorarioYear)

        // Configurar adaptadores para los Spinners
        val days = (1..31).map { it.toString() }
        val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val years = (2023..2024).map { it.toString() }

        val dayAdapter = ArrayAdapter(this, R.layout.spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter

        val monthAdapter = ArrayAdapter(this, R.layout.spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMonth.adapter = monthAdapter

        val yearAdapter = ArrayAdapter(this, R.layout.spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        findViewById<ImageButton>(R.id.NewCourse).setOnClickListener {
            val intent = Intent(this,AgregarCursoActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.ViewCalendario).setOnClickListener {
            val intent = Intent(this,CalendarioActivity::class.java)
            startActivity(intent)
        }




    }

}
