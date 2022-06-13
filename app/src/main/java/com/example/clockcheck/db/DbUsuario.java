package com.example.clockcheck.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbUsuario extends DbHelper {
    Context context;

    public DbUsuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String apellidoP, String apellidoM, String fechaN, String numTel, String direccion, String correo){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            //values.put("id", "1");
            values.put("nombre", nombre);
            values.put("apellidoP", apellidoP);
            values.put("apellidoM", apellidoM);
            values.put("fechaN", fechaN);
            values.put("numTel", numTel);
            values.put("direccion", direccion);
            values.put("correo", correo);

            id = db.insert("usuario", null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }
}
