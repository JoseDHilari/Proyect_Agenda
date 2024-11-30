package hilari.abarca.my_first_apk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarm Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificaciones de alarmas"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

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
