package hilari.abarca.my_first_apk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaNotasAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

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

        actualizarNotas(idCurso)

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
        findViewById<ImageButton>(R.id.NewNota).setOnClickListener {
            val intent = Intent(this, AgregarNotasActivity::class.java)
            intent.putExtra("idCurso", idCurso)
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTA)
        }
    }

    private fun actualizarNotas(idCurso: Int) {
        try {
            val listarNotas = dbHelper.ListarNotas(idCurso)
            Log.i("Jose", listarNotas.toString())
            notasAdaptador.actualizarLista(listarNotas)
        } catch (e: Exception) {
            Log.i("Jose", e.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_NOTA && resultCode == Activity.RESULT_OK) {
            val idCurso = intent.getIntExtra("idCurso", -1)
            actualizarNotas(idCurso)
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_NOTA = 1
    }
}
