package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class MenuActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
        findViewById<LinearLayout>(R.id.Recordatorios).setOnClickListener {
            val intent = Intent(this, RecordatorioActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Notas).setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Multimedia).setOnClickListener {
            Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MultimediaActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Archivos).setOnClickListener {
            Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ArchivosActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<Button>(R.id.bt_NuevaHora).setOnClickListener {
            val intent = Intent(this, AgregarNuevaHoraActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<Button>(R.id.bt_EliminarCurso).setOnClickListener {
            mostrarConfirmacionEliminarCurso(idCurso)
        }
        findViewById<Button>(R.id.bt_EliminarHora).setOnClickListener {
            val intent = Intent(this, EliminarHoraActivity::class.java)
            intent.putExtra("idCurso", idCurso.toInt())
            startActivity(intent)
        }
    }
    private fun mostrarConfirmacionEliminarCurso(idCurso: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este curso?")

        // Botón de confirmación
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar curso de la base de datos
            val result = dbHelper.EliminarCurso(idCurso)
            if (result > 0) {
                Toast.makeText(this, "Curso eliminado", Toast.LENGTH_SHORT).show()
                finish() // Cerrar la actividad
            } else {
                Toast.makeText(this, "Error al eliminar el curso", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        // Botón de cancelación
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Cerrar el cuadro de diálogo sin hacer nada
        }

        // Mostrar el cuadro de diálogo
        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        alertDialog.show()
    }

}