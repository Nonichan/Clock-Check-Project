package com.example.clockcheck.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.clockcheck.entidades.Usuario;

import java.util.ArrayList;

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

    //Este metodo utiliza la clase Usuario en el paquete entidades para encapsular los datos
    public ArrayList<Usuario> mostrarUsuarios(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Usuario usuario = null;
        Cursor cursorUsuarios = null;

        //Sentencia SQL                               (agregar una sentencia WHERE id > 1
        cursorUsuarios = db.rawQuery("SELECT * FROM "+ TABLE_USUARIOS, null);

        //pasamos el cursor al primer resultado de la consulta
        if (cursorUsuarios.moveToFirst()){
            do{                                //usamos la clase usuarios para trabajar con los metodos Set y Get
                usuario = new Usuario();
                usuario.setId(cursorUsuarios.getInt(0));
                usuario.setNombres(cursorUsuarios.getString(1));
                usuario.setApellidos(cursorUsuarios.getString(2));
                usuario.setFechaNacimiento(cursorUsuarios.getString(3));
                usuario.setNumTelefono(cursorUsuarios.getString(4));
                usuario.setCorreoElectronico(cursorUsuarios.getString(5));
                usuario.setContraseña(cursorUsuarios.getString(6));
                listaUsuarios.add(usuario);
            }while(cursorUsuarios.moveToNext());
        }

        cursorUsuarios.close();
        return listaUsuarios; //Metodo que trae en un ArrayList todos los resultados de la consulta
    }
}
