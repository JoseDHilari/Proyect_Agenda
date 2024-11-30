package hilari.abarca.my_first_apk.Models

data class ArchivosModel(
    val idArchivo: Int,
    val nombreArchivo: String,
    val fecha: String, // Fecha de cuando se guardó el archivo
    val rutaArchivo: String // Ruta o URI del archivo
)
