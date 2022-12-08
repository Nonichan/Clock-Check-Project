package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clockcheck.entidades.Usuario;
import com.example.clockcheck.utilidades.Utilidades;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class QR_Registra_Asistencia_Activity extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    String datos[] = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_registra_asistencia);
        ImageView imgQr = findViewById(R.id.QR_asistencia);
        Button btn_salir = findViewById(R.id.btn_salir);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombre" , null);
        String apellido = preferences.getString("apellido" , null);


        // Este es el texto que se crea
        String textoQR= nombre+"/"+
                        apellido+"/"+
                        obtenerHoraActual()+"/"+
                        "null/";
        Toast.makeText(QR_Registra_Asistencia_Activity.this, obtenerHoraActual(), Toast.LENGTH_SHORT).show();

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

    public String obtenerHoraActual(){
        SimpleDateFormat dtf = new SimpleDateFormat("h:mm a"); //instanciamos el formateador de fechas
        Calendar calendar = Calendar.getInstance();

        Date dateobj = calendar.getTime();  //getTime() devuelve el resultado como tipo Date con hora y fecha horaria
        String formatedDate = dtf.format(dateobj);
        return formatedDate;
    }
}