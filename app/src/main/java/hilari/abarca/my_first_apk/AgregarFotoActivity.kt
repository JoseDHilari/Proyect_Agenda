package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarFotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_foto)
        findViewById<ImageButton>(R.id.FinishFoto).setOnClickListener {
            val intent = Intent(this, MultimediaActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.CancelFoto).setOnClickListener {
            val intent = Intent(this, MultimediaActivity::class.java)
            startActivity(intent)
        }
    }
}