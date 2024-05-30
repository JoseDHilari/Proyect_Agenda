package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el OnClickListener para el botón de navegación
        findViewById<Button>(R.id.Ingresar).setOnClickListener {
            val intent = Intent(this, HorarioActivity::class.java)
            startActivity(intent)
        }

        // Configurar la visibilidad temporal de la contraseña
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonTogglePassword = findViewById<ImageButton>(R.id.buttonTogglePassword)

        buttonTogglePassword.setOnClickListener {
            // Mostrar la contraseña
            editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editTextPassword.setSelection(editTextPassword.text.length) // Mantener el cursor al final del texto

            // Volver a ocultar la contraseña después de un breve periodo
            Handler(Looper.getMainLooper()).postDelayed({
                editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editTextPassword.setSelection(editTextPassword.text.length) // Mantener el cursor al final del texto
            }, 1500) // 1500 milisegundos = 1.5 segundos
        }
    }
}
