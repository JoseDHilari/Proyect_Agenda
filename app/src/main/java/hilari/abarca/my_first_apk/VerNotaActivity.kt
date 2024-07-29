package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class VerNotaActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_nota)

        dbHelper = DatabaseHelper(this)

        val idNota = intent.getIntExtra("idNota", -1)
        val idCurso = intent.getIntExtra("idCurso", -1)

        val nombreCursoTextView = findViewById<TextView>(R.id.CourseName)
        val notaName = findViewById<TextView>(R.id.NotaName)
        val notaText = findViewById<TextView>(R.id.NotaText)

        if (idCurso != -1){
            val nombreCurso = dbHelper.ObtenerNombreCurso(idCurso)
            nombreCursoTextView.text = nombreCurso
        }
        if (idNota != -1) {
            val NotaTitle = dbHelper.ObtenerNombreNota(idNota)
            notaName.text = NotaTitle
            val Texto = dbHelper.ObtenerNota(idNota)
            notaText.text = Texto
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
    }
}