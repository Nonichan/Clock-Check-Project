//Este es uno de los archivos mas importantes puesto que aqui es donde se crea la base de datos.
package com.example.clockcheck.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.clockcheck.utilidades.Utilidades;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clockcheck.db";
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_ASISTENCIAS = "asistencias";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Se detalla la estructura de la base de datos en sintaxis SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios" +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombres TEXT NOT NULL," +
                "apellidos TEXT NOT NULL," +
                "fechaNacimiento TEXT," +                // La fecha se define como TEXT debido que
                "numTelefono TEXT," +                    // se tiene que respetar el formato ISO 8601
                "correoElectronico TEXT," +
                "fechaRegistro TEXT," +
                "contraseña TEXT)");                 // La contraseña de cada usuario puede usarse para
                                                     // multiples propositos o puede quedarse fuera del diseño

        db.execSQL("CREATE TABLE asistencias" +"(" +
                "folio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "horaLlegada TEXT," +
                "horaSalida TEXT," +
                "asistencia INTEGER," +             // Tipo de dato INTEGER debido a que se utilizara como boleano 0 o 1
                "calidadAsistencia," +        // Este dato sera calculado antes de insertarse y define que tan temprano o tarde llego a registrar la asistencia
                "FOREIGN KEY (id) REFERENCES usuario(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASISTENCIAS);
        onCreate(db);
    }
}
