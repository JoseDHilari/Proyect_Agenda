package hilari.abarca.my_first_apk

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class MenuActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

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
            val intent = Intent(this, HorarioActivity::class.java)
            startActivity(intent)
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
            val intent = Intent(this, MultimediaActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Archivos).setOnClickListener {
            val intent = Intent(this, ArchivosActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<Button>(R.id.bt_NuevaHora).setOnClickListener {
            val intent = Intent(this, AgregarNuevaHoraActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
    }
}