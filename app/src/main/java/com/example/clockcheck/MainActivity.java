package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferencias;
    String nombre , tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button_siguiente;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_siguiente = findViewById(R.id.main_button_siguiente);

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre = preferencias.getString("nombre", "null"); //con esta sentencia mandamos a pedir el dato nombre, si no existe devuelve un null
        tipoUsuario = preferencias.getString("tipoUsuario", "null");

        Toast.makeText(MainActivity.this, nombre+" - "+tipoUsuario, Toast.LENGTH_LONG).show();

        button_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent;
                //con este switch vamos a escoger a que pantalla vamos a ir dependiendo de lo que haya en la variable tipoUsuario
                switch (tipoUsuario){
                    case "usuario":
                        intent = new Intent(MainActivity.this, QR_Registra_Asistencia_Activity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, " Bienvenido " + nombre, Toast.LENGTH_LONG).show();
                        break;

                    case "administrador":
                        intent = new Intent(MainActivity.this, ActivityAdministrador.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, " Bienvenido " + nombre, Toast.LENGTH_LONG).show();
                        break;

                    case "null":
                        intent = new Intent(MainActivity.this, SeleccionActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }


    private boolean checkDataBase (String Database_path){
        SQLiteDatabase checkDB = null;
        checkDB.close();
        try {
            checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){
            Log.e("Error", "No existe la base de datos");
        }
        return checkDB != null;
    }
}