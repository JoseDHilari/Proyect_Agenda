package hilari.abarca.my_first_apk.Base_de_datos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import hilari.abarca.my_first_apk.Models.CursosModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

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
//            put("idDia",)
            put("idCurso", idCurso)
            put("Dia", dia)
            put("Hora_Inicio", horaInicio)
            put("Hora_Final", horaFinal)
        }
        return db.insert("Dias", null, diaValues)
    }

    fun ListarCuros():List<CursosModel>{
        val cursos = mutableListOf<CursosModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT A.Hora_Inicio,A.Hora_Final,B.Nombre,A.idCurso FROM $TABLE_DIAS AS A INNER JOIN $TABLE_CURSO AS B ON A.idCurso = B.idCurso", null)

        if (cursor.moveToFirst()) {
            do {
                val Horainicio = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Inicio"))
                val Horafinal = cursor.getString(cursor.getColumnIndexOrThrow("Hora_Final"))
                val NombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                val idCurso = cursor.getInt(cursor.getColumnIndexOrThrow("idCurso"))

                cursos.add(CursosModel(Horainicio,Horafinal,NombreCurso,true,idCurso))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cursos
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



    companion object {
        private const val DATABASE_NAME = "Agenda222.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DIAS = "dias"
        private const val TABLE_CURSO = "curso"


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
