package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class VerNotaActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_nota)

        dbHelper = DatabaseHelper(this)

        val idNota = intent.getIntExtra("idNota", -1)
        val idCurso = intent.getIntExtra("idCurso", -1)

        val nombreCursoTextView = findViewById<TextView>(R.id.CourseName)
        val notaName = findViewById<TextView>(R.id.NotaName)
        val notaText = findViewById<EditText>(R.id.NotaText)

        if (idCurso != -1) {
            val nombreCurso = dbHelper.ObtenerNombreCurso(idCurso)
            nombreCursoTextView.text = nombreCurso
        }
        if (idNota != -1) {
            val NotaTitle = dbHelper.ObtenerNombreNota(idNota)
            notaName.text = NotaTitle
            val Texto = dbHelper.ObtenerNota(idNota)
            notaText.setText(Texto)
        }

        findViewById<Button>(R.id.GuardarNota).setOnClickListener {
            if (idNota != -1) {
                val updatedText = notaText.text.toString()
                dbHelper.ActualizarNota(idNota, updatedText)
                finish()
            }
        }

        findViewById<Button>(R.id.EliminarNota).setOnClickListener {
            if (idNota != -1) {
                dbHelper.EliminarNota(idNota)
                finish()
            }
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
    }
}