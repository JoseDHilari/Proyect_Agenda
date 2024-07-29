package hilari.abarca.my_first_apk

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hilari.abarca.my_first_apk.Base_de_datos.DatabaseHelper
import java.io.File

class AgregarArchivoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var pdfListView: ListView
    private lateinit var pdfFiles: List<File>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_archivo)

        dbHelper = DatabaseHelper(this)
        pdfListView = findViewById(R.id.pdfListView)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
        }

        findViewById<ImageButton>(R.id.FinishArchivo).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.CancelArchivo).setOnClickListener {
            finish()
        }

        // Verifica permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            lstFolders("/storage/emulated/0/Download/")
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    fun lstFolders(ruta:String): List<String>{
        val lstFolders : MutableList<String> = arrayListOf()
        try {
            File(ruta).list()?.forEach { lstFolders.add(it) }
        }catch (e: Exception) {
            Log.i("miguel", e.message.toString())
        }
        return lstFolders.toList()
    }
    fun lstFiles(ruta:String, extension: String): List<String>{
        val lstFiles : MutableList<String> = arrayListOf()

        try {
            val directory = File(ruta)

            if (directory.exists() && directory.isDirectory) {

                val files = directory.listFiles()

                if (files != null && files.isNotEmpty()) {
                    for (file in files) {
                        lstFiles.add(file.name)
                    }
                }
            }
        }catch (e: Exception) {
            Log.i("miguel", e.message.toString())
        }
        return lstFiles.toList()
    }
    @SuppressLint("SdCardPath")
    private fun loadPdfFiles() {
        val pdfDirectory = File("/storage/emulated/0/Download/")

        if (pdfDirectory.exists() && pdfDirectory.isDirectory) {
            val files = pdfDirectory.listFiles()
            Log.d("AgregarArchivoActivity", "Archivos encontrados: ${files?.joinToString()}")

            pdfFiles = files?.filter { it.extension.equals("pdf", ignoreCase = true) } ?: emptyList()

            if (pdfFiles.isNotEmpty()) {
                val pdfFileNames = pdfFiles.map { it.name }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pdfFileNames)
                pdfListView.adapter = adapter

                pdfListView.setOnItemClickListener { _, _, position, _ ->
                    val selectedFile = pdfFiles[position]
                    savePdfToDatabase(selectedFile.path)
                }
            } else {
                Toast.makeText(this, "No se encontraron archivos PDF", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "El directorio no existe", Toast.LENGTH_SHORT).show()
        }
    }



    private fun savePdfToDatabase(filePath: String) {
        val idCurso = intent.getIntExtra("idCurso", -1)
        if (idCurso != -1) {
            dbHelper.insertarArchivo(idCurso, filePath)
            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
