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

public class RegistroEmpleadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleado);

        EditText txtnombre = findViewById(R.id.txt_nombre);
        EditText txtapellidoP = findViewById(R.id.txt_apellidoPaterno);
        EditText txtapellidoM = findViewById(R.id.txt_apellidoMaterno);
        EditText fechaNacimiento = findViewById(R.id.txt_fechaNac);
        EditText numeroTelefono = findViewById(R.id.txt_numeroTelefono);
        EditText direccion = findViewById(R.id.txt_direccion);
        EditText correo = findViewById(R.id.txt_email);

        Button btn_Genera = findViewById(R.id.btn_generarQR);
        ImageView imgQr = findViewById(R.id.QR_generado);


        btn_Genera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor obj_editor = preferencias.edit();

                try {


                    String textoQR = txtnombre.getText().toString()+"/"+
                            txtapellidoP.getText().toString().trim()+"/"+
                            txtapellidoM.getText().toString().trim()+"/"+
                            fechaNacimiento.getText().toString().trim()+"/"+
                            numeroTelefono.getText().toString().trim()+"/"+
                            direccion.getText().toString().trim()+"/"+
                            correo.getText().toString().trim()+"/";

                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(textoQR,
                            BarcodeFormat.QR_CODE, 750, 750);
                    imgQr.setImageBitmap(bitmap);


                    //Se guarda el nombre y el correo electronico en una variable para bloquear la aplicación despues
                    obj_editor.putString("nombre", txtnombre.getText().toString());
                    obj_editor.putString("email" , correo.getText().toString());
                    obj_editor.commit();
                    Toast.makeText(getApplicationContext(), "Se guardo: "+correo.getText().toString(), Toast.LENGTH_LONG ).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }
}