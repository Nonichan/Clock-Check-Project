package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.time.LocalDate;

public class QR_Registra_Asistencia_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_registra_asistencia);
        ImageView imgQr = findViewById(R.id.QR_asistencia);
        Button btn_salir = findViewById(R.id.btn_salir);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombre" , null);
        String correo = preferences.getString("email" , null);
        LocalDate fechaActual = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fechaActual = LocalDate.now();
        }

        String textoQR= nombre+"/"+
                        correo+"/"+
                        fechaActual.toString()+"/";

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(textoQR,
                    BarcodeFormat.QR_CODE, 1000, 1000);
            imgQr.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}