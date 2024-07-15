package hilari.abarca.my_first_apk.Models

data class CursosModel(
    val HoraInicio:String,
    val HoraFinal:String,
    val NombreCurso:String,
    val Alarma:Boolean,
    val idCurso:Int = 0
)
