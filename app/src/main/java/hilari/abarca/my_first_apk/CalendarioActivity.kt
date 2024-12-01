package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaCalendarioAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import java.util.*

class CalendarioActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListaCalendarioAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var calendarView: CalendarView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        dbHelper = DatabaseHelper(this)

        val backBtn = findViewById<ImageButton>(R.id.back)
        backBtn.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.rv_Calendario)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListaCalendarioAdapter()
        recyclerView.adapter = adapter

        calendarView = findViewById(R.id.Calendario)
        calendarView.setOnDateChangeListener { _, year, month, _ ->
            // month is 0-based, so we need to add 1
            cargarRecordatorios(month + 1, year)
            marcarFechasConRecordatorios(month + 1, year)
        }

        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1 // Enero es 0
        val anio = calendar.get(Calendar.YEAR)

        cargarRecordatorios(mes, anio)
        marcarFechasConRecordatorios(mes, anio)
    }

    private fun cargarRecordatorios(mes: Int, anio: Int) {
        val recordatorios = dbHelper.ListarRecordatoriosDelMes(mes, anio)
        adapter.actualizarLista(recordatorios)
    }

    private fun marcarFechasConRecordatorios(mes: Int, anio: Int) {
        val fechas = dbHelper.ListarRecordatoriosDelMes(mes, anio).map {
            String.format("%04d-%02d-%02d", anio, mes, it.dia)
        }.toSet()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            if (fechas.contains(fechaSeleccionada)) {
                // Log para verificar que se detectan fechas con recordatorios
                Log.d("CalendarView", "Recordatorio en fecha: $fechaSeleccionada")
            }
        }
    }

}
