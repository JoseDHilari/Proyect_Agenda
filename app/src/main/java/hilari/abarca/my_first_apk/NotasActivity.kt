package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NotasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.NewNota).setOnClickListener {
            val intent = Intent(this, AgregarNotasActivity::class.java)
            startActivity(intent)
        }
    }
}