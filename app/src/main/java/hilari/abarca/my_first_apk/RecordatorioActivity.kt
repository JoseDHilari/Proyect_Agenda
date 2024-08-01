package hilari.abarca.my_first_apk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaRecordatoriosAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper

class RecordatorioActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recordatoriosAdaptador: ListaRecordatoriosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordatorio)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }

        recordatoriosAdaptador = ListaRecordatoriosAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_recordatorios)
        rv.apply {
            adapter = recordatoriosAdaptador
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        actualizarRecordatorios(idCurso)

        findViewById<ImageButton>(R.id.NewRecordatorio).setOnClickListener {
            val intent = Intent(this, AgregarRecordatorioActivity::class.java)
            intent.putExtra("idCurso", idCurso)
            startActivityForResult(intent, REQUEST_CODE_ADD_RECORDATORIO)
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
    }

    private fun actualizarRecordatorios(idCurso: Int) {
        try {
            val listarRecordatorios = dbHelper.ListarRecordatorios(idCurso)
            Log.i("Jose", listarRecordatorios.toString())
            recordatoriosAdaptador.actualizarLista(listarRecordatorios)
        } catch (e: Exception) {
            Log.i("Jose", e.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_RECORDATORIO && resultCode == RESULT_OK) {
            val idCurso = intent.getIntExtra("idCurso", -1)
            actualizarRecordatorios(idCurso)
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_RECORDATORIO = 1
    }
}
