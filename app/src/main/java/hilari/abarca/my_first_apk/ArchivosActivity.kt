package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArchivosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_archivos)
        findViewById<LinearLayout>(R.id.back).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.NewArchivo).setOnClickListener {
            val intent = Intent(this, AgregarArchivoActivity::class.java)
            startActivity(intent)
        }
    }
}