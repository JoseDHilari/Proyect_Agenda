package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class AgregarNotasActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var idCurso: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_notas)

        dbHelper = DatabaseHelper(this)

        idCurso = intent.getIntExtra("idCurso", -1)
        val nombreCursoTextView = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val nombreCurso = dbHelper.ObtenerNombreCurso(idCurso)
            nombreCursoTextView.text = nombreCurso
        }

        val cancelNotaButton = findViewById<ImageButton>(R.id.CancelNota)
        cancelNotaButton.setOnClickListener {
            finish()
        }

        val finishNotaButton = findViewById<ImageButton>(R.id.FinishNota)
        finishNotaButton.setOnClickListener {
            guardarNota()
            val intent = Intent(this,NotasActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
    }

    private fun guardarNota() {
        val tituloNotaEditText = findViewById<EditText>(R.id.NotaName)
        val descripcionNotaEditText = findViewById<EditText>(R.id.NotaText)

        val titulo = tituloNotaEditText.text.toString().trim()
        val descripcion = descripcionNotaEditText.text.toString().trim()

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            finish()
        }

        try {
            val nuevaNotaId = dbHelper.insertarNota(idCurso, titulo, descripcion)
            if (nuevaNotaId != -1L) {
                Toast.makeText(this, "Nota guardada exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar la nota", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar la nota: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
