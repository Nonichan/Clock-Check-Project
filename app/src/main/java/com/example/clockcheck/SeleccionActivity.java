package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clockcheck.db.DbHelper;

public class SeleccionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        Button btn_registrar, btn_registrarme;
        btn_registrar = findViewById(R.id.button_registrar);
        btn_registrarme = findViewById(R.id.button_registrarme);

        //Boton para crear una nueva base de datos
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(SeleccionActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //si se creo correctamente la base de datos nos movemos a otro activity
                if (db != null){
                    Toast.makeText(SeleccionActivity.this, "NUEVA BASE DE DATOS", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                            SeleccionActivity.this,
                            RegistroNuevoAdministradorActivity.class
                    );
                    startActivity(intent);   //Con esta linea ejecutamos el intent
                } else {
                    Toast.makeText(SeleccionActivity.this, "ERROR AL CREAR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                            SeleccionActivity.this,
                            RegistroNuevoUsuarioActivity.class
                    );
                    startActivity(intent);   //Con esta linea ejecutamos el intent

            }
        });
    }
}