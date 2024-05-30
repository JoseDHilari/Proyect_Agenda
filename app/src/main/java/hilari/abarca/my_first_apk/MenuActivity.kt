package hilari.abarca.my_first_apk

import android.os.Bundle
import android.content.Intent
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            val intent = Intent(this,HorarioActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Recordatorios).setOnClickListener {
            val intent = Intent(this, RecordatorioActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Notas).setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Multimedia).setOnClickListener {
            val intent = Intent(this, MultimediaActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.Archivos).setOnClickListener {
            val intent = Intent(this, ArchivosActivity::class.java)
            startActivity(intent)
        }
    }
}