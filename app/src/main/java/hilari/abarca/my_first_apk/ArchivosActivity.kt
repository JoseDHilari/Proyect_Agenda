package hilari.abarca.my_first_apk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import android.app.Dialog
import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Adapters.ListaArchivosAdapter
import hilari.abarca.my_first_apk.Models.ArchivosModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArchivosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedFileUri: Uri? = null
    private lateinit var archivosAdapter: ListaArchivosAdapter
    private lateinit var recyclerView: RecyclerView

    private fun abrirArchivo(archivo: ArchivosModel) {
        val uri = Uri.parse(archivo.rutaArchivo)
        val intent = Intent(Intent.ACTION_VIEW)

        val extension = archivo.rutaArchivo.substringAfterLast(".")
        val mimeType = when (extension) {
            "pdf" -> "application/pdf"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            else -> "*/*" // Si es otro tipo, será genérico
        }

        // Establecer el tipo de archivo y URI
        intent.setDataAndType(uri, mimeType)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Permitir acceso al URI

        // Verificar si hay una aplicación disponible para abrir el archivo
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No hay una aplicación disponible para abrir este archivo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivos)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewArchivos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
            val archivosList = dbHelper.ListarArchivos(idCurso)
            archivosAdapter = ListaArchivosAdapter(archivosList) { archivo ->
                abrirArchivo(archivo) // Llama a la función para abrir el archivo
            }
            recyclerView.adapter = archivosAdapter
        }

        findViewById<ImageButton>(R.id.back).setOnClickListener {
            finish()
        }

        // Configura el file picker
        filePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                // Obtener el archivo seleccionado (su URI)
                selectedFileUri = result.data?.data
                selectedFileUri?.let { uri ->
                    val filePath = uri.toString() // Convertir URI a String
                    Toast.makeText(this, "Archivo seleccionado: $filePath", Toast.LENGTH_LONG).show()
                    // Guardar el archivo en la base de datos
                    saveFileToDatabase(idCurso, filePath) // Asegúrate de que filePath sea un URI correcto
                }
            } else {
                Toast.makeText(this, "No se seleccionó ningún archivo", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.NewArchivo).setOnClickListener {
            if (idCurso != -1) {
                openFilePicker()
            } else {
                Toast.makeText(this, "Error: No se puede guardar sin un curso válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFilePicker() {
        // Crear un intent para abrir el selector de documentos
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf(
                    "application/pdf",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                )
            )
        }
        filePickerLauncher.launch(intent)
    }

    private fun saveFileToDatabase(idCurso: Int, filePath: String) {
        try {
            // Obtener el nombre del archivo desde la URI
            val uri = Uri.parse(filePath)
            val fileName = getFileNameFromUri(uri)

            // Obtener la fecha de creación del archivo
            val fechaCreacion = getFileCreationDate(uri)

            if (fechaCreacion != null) {
                // Guardar el archivo en la base de datos con la fecha de creación
                dbHelper.insertarArchivo(idCurso, fileName, filePath, fechaCreacion)

                Toast.makeText(this, "Archivo guardado exitosamente", Toast.LENGTH_LONG).show()

                // Actualizar la lista de archivos en el RecyclerView
                val archivosList = dbHelper.ListarArchivos(idCurso)
                archivosAdapter.updateData(archivosList) // Usamos el método updateData
            } else {
                Toast.makeText(this, "No se pudo obtener la fecha de creación del archivo", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el archivo: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getFileCreationDate(uri: Uri): String? {
        val projection = arrayOf(android.provider.MediaStore.MediaColumns.DATE_ADDED)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val dateAddedColumnIndex = it.getColumnIndex(android.provider.MediaStore.MediaColumns.DATE_ADDED)
                val dateAdded = it.getLong(dateAddedColumnIndex)

                // Convertir la fecha en formato legible
                return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(dateAdded * 1000))
            }
        }
        return null
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var fileName = ""
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex("_display_name")
            if (cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex)
            }
        }
        return fileName
    }

    fun showImageDialog(imagePath: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.`dialog_image_zoom`) // Usa el layout creado para el dialog
        val imageView = dialog.findViewById<ImageView>(R.id.imageView)

        // Cargar la imagen en el ImageView
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imageView.setImageBitmap(bitmap)

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.show()
    }
}