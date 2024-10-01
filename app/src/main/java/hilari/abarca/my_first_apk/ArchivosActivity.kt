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


class ArchivosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivos)

        dbHelper = DatabaseHelper(this)

        val idCurso = intent.getIntExtra("idCurso", -1)
        val NombreCurso = findViewById<TextView>(R.id.CourseName)

        if (idCurso != -1) {
            val Nombre = dbHelper.ObtenerNombreCurso(idCurso)
            NombreCurso.text = "$Nombre"
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
                selectedFileUri?.let {
                    val filePath = it.toString() // Convertir URI a String
                    Toast.makeText(this, "Archivo seleccionado: $filePath", Toast.LENGTH_LONG)
                        .show()
                    // Guardar el archivo en la base de datos
                    saveFileToDatabase(idCurso, filePath)
                }
            } else {
                Toast.makeText(this, "No se seleccionó ningún archivo", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.NewArchivo).setOnClickListener {
            if (idCurso != -1) {
                openFilePicker()
            } else {
                Toast.makeText(
                    this,
                    "Error: No se puede guardar sin un curso válido",
                    Toast.LENGTH_SHORT
                ).show()
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
            // Guardar el archivo en la base de datos
            dbHelper.insertarArchivo(idCurso, filePath)
            Toast.makeText(this, "Archivo guardado exitosamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el archivo: ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
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