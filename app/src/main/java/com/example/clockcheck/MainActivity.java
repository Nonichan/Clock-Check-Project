package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button_siguiente;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_siguiente = findViewById(R.id.main_button_siguiente);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String guardado = preferences.getString("email" , null);
        Toast.makeText(MainActivity.this, ""+guardado, Toast.LENGTH_LONG).show();

        button_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (guardado == null) {
                    Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                            MainActivity.this,
                            SeleccionActivity.class
                    );
                    startActivity(intent);   //Con esta linea ejecutamos el intent
                }else {
                    Toast.makeText(getApplicationContext(), "ya hay un registro guardado: " + guardado, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(
                            MainActivity.this,
                            QR_Registra_Asistencia_Activity.class
                    );
                    startActivity(intent);
                }
            }
        });
    }
}