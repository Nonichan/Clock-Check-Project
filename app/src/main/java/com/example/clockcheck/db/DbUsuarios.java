package com.example.clockcheck.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbUsuarios extends DbHelper {
    Context context;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuarios(String nombres, String apellidos, String fechaNacimiento, String numTelelefono, String correoElectronico, String fechaRegistro, String contrasena){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            //values.put("id", "1");
            values.put("nombres", nombres);
            values.put("apellidos", apellidos);
            values.put("fechaNacimiento", fechaNacimiento);
            values.put("numTelefono", numTelelefono);
            values.put("correoElectronico", correoElectronico);
            values.put("fechaRegistro", fechaRegistro);
            values.put("contraseña", contrasena);

            id = db.insert("usuarios", null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }
}
