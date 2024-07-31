package hilari.abarca.my_first_apk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val usernameEditText = findViewById<EditText>(R.id.Usuario)
        val passwordEditText = findViewById<EditText>(R.id.Password)
        val registerButton = findViewById<Button>(R.id.Ingresar)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putBoolean("isUserRegistered", true)
                editor.apply()

                Toast.makeText(this, "Usuario registrado exitosamente!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, ingrese un usuario y una contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
