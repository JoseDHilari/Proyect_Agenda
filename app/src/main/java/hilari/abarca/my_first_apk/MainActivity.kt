package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Usuario = findViewById<EditText>(R.id.Usuario)
        val Password = findViewById<EditText>(R.id.Password)

        findViewById<Button>(R.id.Ingresar).setOnClickListener {
            if (Usuario.text.toString().trim() == "dh" && Password.text.toString().trim() == "123") {
                val intent = Intent(this, HorarioActivity::class.java)
                startActivity(intent)
            }
            else if (Usuario.text.toString().trim() == "" && Password.text.toString().trim() == "") {
                Toast.makeText(this, "Datos no ingresados", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show()

            }
        }
    }
}
