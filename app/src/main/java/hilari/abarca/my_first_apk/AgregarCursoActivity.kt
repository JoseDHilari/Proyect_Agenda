package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarCursoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_curso)

        val spinnerDay = findViewById<Spinner>(R.id.Day)
        val Days = listOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        val dayAdapter = ArrayAdapter(this, R.layout.spinner_item, Days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter


        findViewById<ImageButton>(R.id.CancelNewCourse).setOnClickListener {
            val intent = Intent(this, HorarioActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.FinishNewCourse).setOnClickListener {
            val intent = Intent(this, HorarioActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.AddNewHour).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}