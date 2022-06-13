package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SeleccionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        Button btn_registrar, btn_registrarme;
        btn_registrar = findViewById(R.id.button_registrar);
        btn_registrarme = findViewById(R.id.button_registrarme);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                        SeleccionActivity.this,
                        RegistroPatronActivity.class
                );
                startActivity(intent);   //Con esta linea ejecutamos el intent
            }
        });

        btn_registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                            SeleccionActivity.this,
                            RegistroEmpleadoActivity.class
                    );
                    startActivity(intent);   //Con esta linea ejecutamos el intent

            }
        });
    }
}