package com.example.clockcheck.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.clockcheck.utilidades.Utilidades;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clockcheck.db";
    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_ASISTENCIA = "asistencia";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario" +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellidoP TEXT NOT NULL," +
                "apellidoM TEXT NOT NULL," +
                "fechaN TEXT," +
                "numTel TEXT," +
                "direccion TEXT," +
                "correo TEXT)");

        db.execSQL("CREATE TABLE asistencia" +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "identidad INTEGER," +
                "fecha TEXT," +
                "hora TEXT," +
                "FOREIGN KEY (identidad) REFERENCES usuario(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASISTENCIA);
        onCreate(db);
    }
}
