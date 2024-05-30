package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarArchivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_archivo)

        findViewById<LinearLayout>(R.id.FinishArchivo).setOnClickListener {
            val intent = Intent(this, ArchivosActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.CancelArchivo).setOnClickListener {
            val intent = Intent(this, ArchivosActivity::class.java)
            startActivity(intent)
        }
    }
}