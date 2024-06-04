package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HorarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

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
        findViewById<ScrollView>(R.id.HorarioView).setOnClickListener {
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
    }

}
