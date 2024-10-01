package hilari.abarca.my_first_apk.Models

data class MultimediaModel(
    val idMultimedia: Int,
    val idCurso: Int,
    val nombreArchivo: String,
    val fechaCreacion: String,
    val archivoMultimedia: String
) {
    fun esImagen(): Boolean {
        return archivoMultimedia.endsWith(".jpg") || archivoMultimedia.endsWith(".png")
    }

    fun esVideo(): Boolean {
        return archivoMultimedia.endsWith(".mp4") || archivoMultimedia.endsWith(".avi")
    }
}
