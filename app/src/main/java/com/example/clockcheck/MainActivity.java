package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button_siguiente;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_siguiente = findViewById(R.id.main_button_siguiente);
        button_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                        MainActivity.this,
                        SeleccionActivity.class
                );
                startActivity(intent);   //Con esta linea ejecutamos el intent
            }
        });
    }
}