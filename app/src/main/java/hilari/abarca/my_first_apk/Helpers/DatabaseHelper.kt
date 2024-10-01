package hilari.abarca.my_first_apk.Helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import hilari.abarca.my_first_apk.Models.CalendarioModel
import hilari.abarca.my_first_apk.Models.CursosModel
import hilari.abarca.my_first_apk.Models.HoraModel
import hilari.abarca.my_first_apk.Models.MultimediaModel
import hilari.abarca.my_first_apk.Models.NotaModel
import hilari.abarca.my_first_apk.Models.RecordatorioModel

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CURSO)
        db.execSQL(CREATE_TABLE_DIAS)
        db.execSQL(CREATE_TABLE_ALARMA)
        db.execSQL(CREATE_TABLE_NOTAS)
        db.execSQL(CREATE_TABLE_MULTIMEDIA)
        db.execSQL(CREATE_TABLE_ARCHIVOS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val db2 = this.writableDatabase
        db2.execSQL("DROP TABLE IF EXISTS Curso")
        db2.execSQL("DROP TABLE IF EXISTS Dias")
        db2.execSQL("DROP TABLE IF EXISTS Alarma")
        db2.execSQL("DROP TABLE IF EXISTS Notas")
        db2.execSQL("DROP TABLE IF EXISTS Multimedia")
        db2.execSQL("DROP TABLE IF EXISTS Archivos")
        onCreate(db2)
    }

    fun insertCourse(courseName: String): Long {
        val db = this.writableDatabase
        val courseValues = ContentValues().apply {
            put("Nombre", courseName)
        }
        return db.insert("Curso", null, courseValues).also {
            if (it == -1L) {
                Log.e("DatabaseHelper", "Error inserting into Curso table")
            }
        }
    }

    fun insertDay(idCurso: Int, dia: String, horaInicio: String, horaFinal: String): Long {
        val db = writableDatabase
        val diaValues = ContentValues().apply {
            put("idCurso", idCurso)
            put("Dia", dia)
            put("Hora_Inicio", horaInicio)
            put("Hora_Final", horaFinal)
        }
        return db.insert("Dias", null, diaValues)
    }

    fun insertarNota(idCurso: Int, titulo: String, descripcion: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("idCurso", idCurso)
            put("Titulo", titulo)
            put("Descripcion", descripcion)
        }
        return db.insert("Notas", null, values)
    }

    fun insertarArchivo(idCurso: Int, archivo: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("idCurso", idCurso)
        contentValues.put("Archivo", archivo)
        db.insert("Archivos", null, contentValues)
        db.close()
    }

    fun insertarMultimedia(idCurso: Int, nombreArchivo: String, archivoMultimedia: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("idCurso", idCurso)
        contentValues.put("NombreArchivo", nombreArchivo)
        contentValues.put("FechaCreacion", System.currentTimeMillis().toString()) // Fecha actual
        contentValues.put("Archivo_Multimedia", archivoMultimedia)
        db.insert("Multimedia", null, contentValues)
        db.close()
    }


    fun ListarCursos(dia: String): List<CursosModel> {
        val cursos = mutableListOf<CursosModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT A.Hora_Inicio, A.Hora_Final, B.Nombre, A.idCurso FROM $TABLE_DIAS AS A INNER JOIN $TABLE_CURSO AS B ON A.idCurso = B.idCurso WHERE A.Dia = ?",
            arrayOf(dia)
        )

        if (cursor.moveToFirst()) {
            do {
                val horaInicio = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Inicio"))
                val horaFinal = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Final"))
                val nombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                val idCurso = cursor.getInt(cursor.getColumnIndexOrThrow("idCurso"))

                cursos.add(CursosModel(horaInicio, horaFinal, nombreCurso, idCurso))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cursos
    }

    fun ListarNotas(Curso: Int): List<NotaModel> {
        val notas = mutableListOf<NotaModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT Titulo, Descripcion, idNotas, idCurso FROM $TABLE_NOTAS WHERE idCurso = ?", arrayOf(Curso.toString()))

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("Titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))
                val idNota = cursor.getInt(cursor.getColumnIndexOrThrow("idNotas"))
                val idCurso = cursor.getInt(cursor.getColumnIndexOrThrow("idCurso"))

                notas.add(NotaModel(nombre, descripcion, idNota,idCurso))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notas
    }

    fun ListarRecordatorios(Curso: Int): List<RecordatorioModel> {
        val recordatorios = mutableListOf<RecordatorioModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT Nombre_Actividad, Fecha, Hora, Activar FROM $TABLE_ALARMA WHERE idCurso = ?", arrayOf(Curso.toString()))

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre_Actividad"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("Fecha"))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow("Hora"))
                val activar = cursor.getInt(cursor.getColumnIndexOrThrow("Activar"))

                recordatorios.add(RecordatorioModel(nombre, fecha, hora, activar))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return recordatorios
    }

    fun ListarRecordatoriosDelMes(mes: Int, anio: Int): List<CalendarioModel> {
        val recordatorios = mutableListOf<CalendarioModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT strftime('%d', Fecha) as dia, Nombre_Actividad as nombre FROM Alarma WHERE strftime('%m', Fecha) = ? AND strftime('%Y', Fecha) = ?",
            arrayOf(mes.toString().padStart(2, '0'), anio.toString())
        )
        if (cursor.moveToFirst()) {
            do {
                val recordatorio = CalendarioModel(
                    dia = cursor.getInt(cursor.getColumnIndexOrThrow("dia")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                )
                recordatorios.add(recordatorio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return recordatorios
    }

    fun ListarHoras(Curso: Int): List<HoraModel> {
        val horas = mutableListOf<HoraModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT idDia, Dia, Hora_Inicio, Hora_Final FROM $TABLE_DIAS WHERE idCurso = ?", arrayOf(Curso.toString()))


        if (cursor.moveToFirst()) {
            do {
                val idHora = cursor.getInt(cursor.getColumnIndexOrThrow("idDia"))
                val dia = cursor.getString(cursor.getColumnIndexOrThrow("Dia"))
                val horaInicio = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Inicio"))
                val horaFinal = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Final"))

                horas.add(HoraModel(idHora, dia, horaInicio, horaFinal))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return horas
    }

    fun ListarMultimedia(idCurso: Int): List<MultimediaModel> {
        val multimediaList = mutableListOf<MultimediaModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Multimedia WHERE idCurso = ?", arrayOf(idCurso.toString()))

        if (cursor.moveToFirst()) {
            do {
                val idMultimedia = cursor.getInt(cursor.getColumnIndexOrThrow("idMultimedia"))
                val nombreArchivo = cursor.getString(cursor.getColumnIndexOrThrow("NombreArchivo"))
                val fechaCreacion = cursor.getString(cursor.getColumnIndexOrThrow("FechaCreacion"))
                val archivoMultimedia = cursor.getString(cursor.getColumnIndexOrThrow("Archivo_Multimedia"))

                multimediaList.add(MultimediaModel(idMultimedia, idCurso, nombreArchivo, fechaCreacion, archivoMultimedia))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return multimediaList
    }

    fun ObtenerNombreCurso(id: Int): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT Nombre FROM $TABLE_CURSO WHERE idCurso = ?", arrayOf(id.toString()))

        var nombreCurso: String? = null

        if (cursor.moveToFirst()) {
            nombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
        }

        cursor.close()
        return nombreCurso
    }

    fun ObtenerNombreNota(id: Int): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT Titulo FROM $TABLE_NOTAS WHERE idNotas = ?", arrayOf(id.toString()))

        var nombreNota: String? = null

        if (cursor.moveToFirst()) {
            nombreNota = cursor.getString(cursor.getColumnIndexOrThrow("Titulo"))
        }

        cursor.close()
        return nombreNota
    }

    fun ObtenerNota(id: Int): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT Descripcion FROM $TABLE_NOTAS WHERE idNotas = ?", arrayOf(id.toString()))

        var Nota: String? = null

        if (cursor.moveToFirst()) {
            Nota = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))
        }

        cursor.close()
        return Nota
    }

    fun insertAlarma(idCurso: Int, hora: String, fecha: String, nombreActividad: String, activar: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("idCurso", idCurso)
            put("Hora", hora)
            put("Fecha", fecha)
            put("Nombre_Actividad", nombreActividad)
            put("Activar", activar)
        }
        return db.insert("Alarma", null, values)
    }

    fun ActualizarNota(idNota: Int, texto: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("Descripcion", texto)
        }
        db.update("Notas", values, "idNotas = ?", arrayOf(idNota.toString()))
        db.close()
    }

    fun EliminarNota(idNota: Int) {
        val db = this.writableDatabase
        db.delete("Notas", "idNotas = ?", arrayOf(idNota.toString()))
        db.close()
    }

    fun EliminarCurso(idCurso: Int): Int {
        val db = this.writableDatabase
        val result = db.delete("Curso", "idCurso = ?", arrayOf(idCurso.toString()))
        db.close()
        return result
    }

    fun EliminarHora(idHora: Int): Int {
        val db = this.writableDatabase
        val result = db.delete("Dias", "idDia = ?", arrayOf(idHora.toString()))
        db.close()
        return result
    }

    companion object {
        private const val DATABASE_NAME = "Agenda_1.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DIAS = "dias"
        private const val TABLE_CURSO = "curso"
        private const val TABLE_ALARMA = "alarma"
        private const val TABLE_NOTAS = "notas"
        private const val TABLE_MULTIMEDIA = "multimedia"
        private const val TABLE_ARCHIVOS = "archivos"

        private const val CREATE_TABLE_CURSO = """
            CREATE TABLE Curso (
                idCurso INTEGER PRIMARY KEY AUTOINCREMENT,
                Nombre TEXT NOT NULL
            )
        """

        private const val CREATE_TABLE_DIAS = """
            CREATE TABLE Dias (
                idDia INTEGER PRIMARY KEY AUTOINCREMENT,
                idCurso INTEGER NOT NULL,
                Dia TEXT NOT NULL,
                Hora_Inicio TEXT NOT NULL,
                Hora_Final TEXT NOT NULL,
                FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
            )
        """

        private const val CREATE_TABLE_ALARMA = """
            CREATE TABLE Alarma (
                idAlarma INTEGER PRIMARY KEY AUTOINCREMENT,
                idCurso INTEGER NOT NULL,
                Hora TEXT NOT NULL,
                Fecha TEXT NOT NULL,
                Nombre_Actividad TEXT NOT NULL,
                Activar INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
            )
        """

        private const val CREATE_TABLE_NOTAS = """
            CREATE TABLE Notas (
                idNotas INTEGER PRIMARY KEY AUTOINCREMENT,
                idCurso INTEGER NOT NULL,
                Titulo TEXT NOT NULL,
                Descripcion TEXT NOT NULL,
                FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
            )
        """

        private const val CREATE_TABLE_MULTIMEDIA = """
            CREATE TABLE Multimedia (
                idMultimedia INTEGER PRIMARY KEY AUTOINCREMENT,
                idCurso INTEGER NOT NULL,
                NombreArchivo TEXT NOT NULL,
                FechaCreacion TEXT NOT NULL,
                Archivo_Multimedia TEXT NOT NULL,
                FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
            )
        """


        private const val CREATE_TABLE_ARCHIVOS = """
            CREATE TABLE Archivos (
                idArchivos INTEGER PRIMARY KEY AUTOINCREMENT,
                idCurso INTEGER NOT NULL,
                Archivo TEXT NOT NULL,
                FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
            )
        """
    }
}
