package hilari.abarca.my_first_apk

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class ArchivosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivos)

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
        findViewById<ImageButton>(R.id.NewArchivo).setOnClickListener {
            Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_SHORT).show()
            /*
            val intent = Intent(this, AgregarArchivoActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
            */

        }
    }
}