package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddHourActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_hour)
        val Spin = findViewById<Spinner>(R.id.Day)
            setupSpinnerDays(Spin)
    }
    fun setupSpinnerDays(spinner: Spinner) {
        val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        val dayAdapter = ArrayAdapter(this, R.layout.spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dayAdapter
    }

    fun setupSpinnerDaysInLayout(view: View) {
        val spinner = view.findViewById<Spinner>(R.id.Day)
        setupSpinnerDays(spinner)
    }
}