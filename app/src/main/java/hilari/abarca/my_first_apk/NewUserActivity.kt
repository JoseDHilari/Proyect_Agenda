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
                mostrarConfirmacion(username,password)
            } else {
                Toast.makeText(this, "Por favor, ingrese un usuario y una contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun mostrarConfirmacion(user:String , contrasena:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Confirmar Usuario")
        builder.setMessage("¿Estás seguro que deseas registrarse con estos datos?\n LOs datos no se podran modificar una vez creado ?")

        // Botón de confirmación
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Realizamos el guardado de los datos
            val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", user)
            editor.putString("password", contrasena)
            editor.putBoolean("isUserRegistered", true)
            editor.apply()

            Toast.makeText(this, "Usuario registrado exitosamente!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón de cancelación
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Cerrar el cuadro de diálogo sin hacer nada
        }

        // Mostrar el cuadro de diálogo
        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        alertDialog.show()
    }
}
