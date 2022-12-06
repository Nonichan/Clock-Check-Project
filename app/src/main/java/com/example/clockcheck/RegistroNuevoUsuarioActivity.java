package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class RegistroNuevoUsuarioActivity extends AppCompatActivity {

    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nuevo_usuario);

        EditText txtnombre = findViewById(R.id.txt_nombres_usuario);
        EditText txtapellido = findViewById(R.id.txt_apellidos_usuario);
        EditText txtFechaNacimiento = findViewById(R.id.txt_fechanacimiento_usuario);
        EditText txtNumeroTelefono = findViewById(R.id.txt_numeroTelefono_usuario);
        EditText txtCorreoElectronico = findViewById(R.id.txt_correoelectronico_usuario);
        EditText txtContrasenaUsuario = findViewById(R.id.txt_contraseña_usuario);
        Button btn_generarQR = findViewById(R.id.btn_generar_QR);
        ImageView iv_QRgenerar = findViewById(R.id.QR_generar_2);

        //Metodo que pone en funcionamiento el botón
        btn_generarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor obj_editor = preferencias.edit();

                try {

                    //Creamos una cadena que contenga todos los datos que recogimos, separados por una /
                    String textoQR = txtnombre.getText().toString().trim()+"/"+
                            txtapellido.getText().toString().trim()+"/"+
                            txtFechaNacimiento.getText().toString().trim()+"/"+
                            txtNumeroTelefono.getText().toString().trim()+"/"+
                            txtCorreoElectronico.getText().toString().trim()+"/"+
                            txtContrasenaUsuario.getText().toString().trim()+"/";

                    //Generamos el codigo QR con la cadena creada
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(textoQR, BarcodeFormat.QR_CODE, 750,750);

                    //objeto del shared preferences para guardar un texto en memoria
                    obj_editor.putString("nombre", txtnombre.getText().toString().trim());
                    obj_editor.putString("tipoUsuario", "usuario");
                    obj_editor.commit();
                    Toast.makeText(RegistroNuevoUsuarioActivity.this, "MODO USUARIO ACTIVADO", Toast.LENGTH_SHORT).show();

                    //Se usa el contenedor ImageView para visualizar el codigo generado
                    iv_QRgenerar.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}