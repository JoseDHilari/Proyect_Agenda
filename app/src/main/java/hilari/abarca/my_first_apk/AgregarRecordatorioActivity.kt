package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.datepicker.MaterialDatePicker

class AgregarRecordatorioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_recordatorio)

        findViewById<ImageButton>(R.id.FinishRecordatorio).setOnClickListener {
            val intent = Intent(this, RecordatorioActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.CancelRecordatorio).setOnClickListener {
            val intent = Intent(this, RecordatorioActivity::class.java)
            startActivity(intent)
        }
    }
}