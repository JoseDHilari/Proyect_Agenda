package hilari.abarca.my_first_apk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText = findViewById<EditText>(R.id.Usuario)
        val passwordEditText = findViewById<EditText>(R.id.Password)
        val loginButton = findViewById<Button>(R.id.Ingresar)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
            val registeredUsername = sharedPreferences.getString("username", null)
            val registeredPassword = sharedPreferences.getString("password", null)

            if (username == registeredUsername && password == registeredPassword) {
                Toast.makeText(this, "Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                // Ir a la actividad principal de la aplicación
                val intent = Intent(this, HorarioActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
