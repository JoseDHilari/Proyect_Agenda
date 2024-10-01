package hilari.abarca.my_first_apk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaMultimediaAdapter
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import hilari.abarca.my_first_apk.Models.MultimediaModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MultimediaActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private var selectedFileUri: Uri? = null
    private var currentPhotoPath: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var multimediaAdapter: ListaMultimediaAdapter
    private var multimediaList = mutableListOf<MultimediaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val nombreCurso = findViewById<TextView>(R.id.CourseName)

        // Configurar RecyclerView y Adapter
        multimediaAdapter = ListaMultimediaAdapter(this, multimediaList)
        recyclerView = findViewById(R.id.DesMultimedia)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = multimediaAdapter

        if (idCurso != -1) {
            val nombre = dbHelper.ObtenerNombreCurso(idCurso)
            nombreCurso.text = nombre
            loadMultimediaFromDatabase(idCurso)
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }

        filePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedFileUri = result.data?.data
                selectedFileUri?.let {
                    val filePath = it.toString()
                    val fileName = it.lastPathSegment ?: "Archivo Desconocido"
                    Toast.makeText(this, "Archivo seleccionado: $fileName", Toast.LENGTH_LONG).show()
                    saveFileToDatabase(idCurso, fileName, filePath)
                    loadMultimediaFromDatabase(idCurso)
                }
            } else {
                Toast.makeText(this, "No se seleccionó ningún archivo", Toast.LENGTH_SHORT).show()
            }
        }

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                currentPhotoPath?.let { path ->
                    val fileUri = Uri.parse(path)
                    saveFileToDatabase(idCurso, "Foto/Video", fileUri.toString())
                    Toast.makeText(this, "Foto/Video guardado en la base de datos", Toast.LENGTH_LONG).show()
                    loadMultimediaFromDatabase(idCurso)
                }
            } else {
                Toast.makeText(this, "No se capturó ninguna foto/video", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.TomarFoto).setOnClickListener {
            if (checkPermissions()) {
                openCamera()
            } else {
                requestPermissions()
            }
        }

        findViewById<ImageButton>(R.id.NewMultimedia).setOnClickListener {
            if (idCurso != -1) {
                openFilePicker()
            } else {
                Toast.makeText(this, "Error: No se puede guardar sin un curso válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        }
        filePickerLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Toast.makeText(this, "Error al crear el archivo de imagen", Toast.LENGTH_SHORT).show()
            null
        }
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraLauncher.launch(intent)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefijo */
            ".jpg", /* sufijo */
            storageDir /* directorio */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun saveFileToDatabase(idCurso: Int, nombreArchivo: String, filePath: String) {
        try {
            dbHelper.insertarMultimedia(idCurso, nombreArchivo, filePath)
            Toast.makeText(this, "Archivo guardado exitosamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el archivo: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
    }

    private fun loadMultimediaFromDatabase(idCurso: Int) {
        multimediaList.clear()
        multimediaList.addAll(dbHelper.ListarMultimedia(idCurso))
        multimediaAdapter.notifyDataSetChanged()
    }
}
