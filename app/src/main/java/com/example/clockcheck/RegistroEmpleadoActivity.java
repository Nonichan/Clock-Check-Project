package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
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
        EditText contrasena = findViewById(R.id.txt_password);
        Button btn_Genera = findViewById(R.id.btn_generarQR);
        ImageView imgQr = findViewById(R.id.QR_generado);






        btn_Genera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String textoQR = txtnombre.getText().toString().trim()+"/"+
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}