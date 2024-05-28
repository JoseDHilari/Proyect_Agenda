package hilari.abarca.my_first_apk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HorarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

        // Configurar ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
/*
        // Configurar ImageButton para agregar nuevos cursos (o cualquier acción)
        val newCourseButton = findViewById<ImageButton>(R.id.NewCourse)
        newCourseButton.setOnClickListener {
            // Acción para el botón, por ejemplo, abrir una nueva actividad o dialogo
            // Por ahora, simplemente muestra un mensaje
            println("Nuevo curso añadido!")
        }*/
    }
}
