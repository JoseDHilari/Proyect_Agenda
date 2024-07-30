package hilari.abarca.my_first_apk

import android.annotation.SuppressLint
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

    @SuppressLint("MissingInflatedId")
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

        try {
            var listarRecordatorios = dbHelper.ListarRecordatorios(idCurso)
            Log.i("Jose", dbHelper.ListarNotas(idCurso).toString())
            recordatoriosAdaptador.actualizarLista(listarRecordatorios)

        }
        catch (e:Exception){
            Log.i("Jose",e.toString())
        }

        findViewById<ImageButton>(R.id.NewRecordatorio).setOnClickListener {
            val intent = Intent(this, AgregarRecordatorioActivity::class.java)
            intent.putExtra("idCurso",idCurso.toInt())
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }
    }

}