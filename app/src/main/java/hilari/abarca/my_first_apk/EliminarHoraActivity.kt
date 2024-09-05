package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaHorasAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class EliminarHoraActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var horasAdaptador:ListaHorasAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_hora)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }

        horasAdaptador = ListaHorasAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_Horas)
        rv.apply {
            adapter = horasAdaptador
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        actualizarHoras(idCurso)

        findViewById<ImageButton>(R.id.back).setOnClickListener{
            finish()
        }
        findViewById<ImageButton>(R.id.CancelNewHora).setOnClickListener{
            finish()
        }
    }

    private fun actualizarHoras(idCurso : Int){
        try{
            val listarHoras = dbHelper.ListarHoras(idCurso)
            Log.i("Jose",listarHoras.toString())
            horasAdaptador.actualizarLista(listarHoras)
        } catch (e:Exception){
            Log.i("Jose",e.toString())
        }
    }
}