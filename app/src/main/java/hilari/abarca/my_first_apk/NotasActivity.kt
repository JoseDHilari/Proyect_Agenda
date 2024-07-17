package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaNotasAdapter
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper

class NotasActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var notasAdaptador: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }


        notasAdaptador = ListaNotasAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_notas)
        rv.apply {
            adapter = notasAdaptador
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        try {
            var listarNotas = dbHelper.ListarNotas(idCurso)
            Log.i("Jose", dbHelper.ListarNotas(idCurso).toString())
            notasAdaptador.actualizarLista(listarNotas)

        }
        catch (e:Exception){
            Log.i("Jose",e.toString())
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
        findViewById<ImageButton>(R.id.NewNota).setOnClickListener {
            val intent = Intent(this, AgregarNotasActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
    }
}