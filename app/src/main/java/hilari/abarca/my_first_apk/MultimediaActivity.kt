package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MultimediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multimedia)
        findViewById<ImageButton>(R.id.back).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.NewMultimedia).setOnClickListener {
            val intent = Intent(this, AgregarMultimediaActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.TomarFoto).setOnClickListener {
            val intent = Intent(this, AgregarFotoActivity::class.java)
            startActivity(intent)
        }
    }
}