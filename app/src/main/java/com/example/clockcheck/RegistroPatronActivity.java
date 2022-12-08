//todo Esta clase sera descontinuada pronto de la aplicacion pero la seguire usando para referencias
package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clockcheck.db.DbHelper;
import com.example.clockcheck.db.DbUsuarios;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class RegistroPatronActivity extends AppCompatActivity {

    String datos[] = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_patron);
        Button btnScan = findViewById(R.id.btn_escanearQR);

        //Este boton sirve para abrir el escaner de codigos QR

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(RegistroPatronActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector QR");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();

        if(result.getContents() == null){
            Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String cadena = result.getContents();
            String subcadena = "";
            int dato = 0, inicioCad = 0;

            for(int i=0; i<cadena.length(); i++){

                //Simbolo al actual en la cadena  ---*---
                String letra = cadena.substring(i, i+1);

                if(letra.equals("/")){
                    subcadena = cadena.substring(inicioCad, i);
                    inicioCad = i+1;
                    datos[dato] = subcadena;
                    dato++;
                    //System.out.println(subcadena);
                }
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            DbUsuarios dbUsuarios = new DbUsuarios(RegistroPatronActivity.this);
            long id = dbUsuarios.insertarUsuarios(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);

            //si registraba abria una clase que ya fue eliminada
            if(id>0){
                Toast.makeText(RegistroPatronActivity.this, "REGISTRO EXITOSO", Toast.LENGTH_LONG).show();
                obj_editor.putString("tipoUsuario", "patron");
                Intent intent = new Intent(     //con esta clase nos movemos a otro activity
                        //RegistroPatronActivity.this,
                        //MenuPrincipalPatronActivity.class
                );
            }else{
                Toast.makeText(RegistroPatronActivity.this, "REGISTRO FALLIDO", Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // En esta parte se crea la base de datos he ahi la razon del condicional if db != null
    private void RegistrarUsuarios(){
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();        //Con esto indicamos que vamos a escribir en la base de datos
        ContentValues values = new ContentValues();

        if(db != null) {
            //values.put("id", "null");
            values.put("nombre", datos[0]);
            values.put("apellidoP", datos[1]);
            values.put("apellidoM", datos[2]);
            values.put("fechaN", datos[3]);
            values.put("numTel", datos[4]);
            values.put("direccion", datos[5]);
            values.put("correo", datos[6]);



        }else{
            Toast.makeText(getApplicationContext(), "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    }

}