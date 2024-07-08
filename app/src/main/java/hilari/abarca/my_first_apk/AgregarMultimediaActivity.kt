package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarMultimediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_multimedia)
        findViewById<ImageButton>(R.id.CancelMultimedia).setOnClickListener {
            val intent = Intent(this, MultimediaActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.FinishMultimedia).setOnClickListener {
            val intent = Intent(this, MultimediaActivity::class.java)
            startActivity(intent)
        }
    }
}