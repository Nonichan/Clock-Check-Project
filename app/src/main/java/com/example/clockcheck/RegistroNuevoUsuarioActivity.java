package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clockcheck.utilidades.Utilidades;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistroNuevoUsuarioActivity extends AppCompatActivity {

    SharedPreferences preferencias;
    ConexionSQLiteHelper conn;
    String datos[] = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nuevo_usuario);
        conn = new ConexionSQLiteHelper(RegistroNuevoUsuarioActivity.this, "bd_clockcheck", null, 1);

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
                datos[0] = txtnombre.getText().toString().trim();
                datos[1] = txtapellido.getText().toString().trim();
                datos[2] = txtFechaNacimiento.getText().toString().trim();
                datos[3] = txtNumeroTelefono.getText().toString().trim();
                datos[4] = txtCorreoElectronico.getText().toString().trim();
                datos[5] = txtContrasenaUsuario.getText().toString().trim();

                try {

                    //Creamos una cadena que contenga todos los datos que recogimos, separados por una /
                    String textoQR = datos[0]+"/"+
                            datos[1]+"/"+
                            datos[2]+"/"+
                            datos[3]+"/"+
                            datos[4]+"/"+
                            datos[5]+"/";

                    //Generamos el codigo QR con la cadena creada
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(textoQR, BarcodeFormat.QR_CODE, 750,750);

                    //objeto del shared preferences para guardar un texto en memoria
                    obj_editor.putString("nombre", datos[0]);
                    obj_editor.putString("apellido", datos[1]);
                    obj_editor.putString("tipoUsuario", "usuario");
                    obj_editor.commit();
                    Toast.makeText(RegistroNuevoUsuarioActivity.this, "MODO USUARIO ACTIVADO", Toast.LENGTH_SHORT).show();

                    //Se usa el contenedor ImageView para visualizar el codigo generado
                    iv_QRgenerar.setImageBitmap(bitmap);
                    RegistrarUsuarios(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            datos[4],
                            datos[5]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void RegistrarUsuarios(String nombres, String apellidos, String fechanacimiento, String numerotelefono,String correoelectronico, String contrasena){
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRES, nombres);
        values.put(Utilidades.CAMPO_APELLIDOS, apellidos);
        values.put(Utilidades.CAMPO_FECHANACIMIENTO, fechanacimiento);
        values.put(Utilidades.CAMPO_NUMTELEFONO, numerotelefono);
        values.put(Utilidades.CAMPO_CORREOELECTRONICO, correoelectronico);
        values.put(Utilidades.CAMPO_FECHAREGISTRO, obtenerFechadeHoy());
        values.put(Utilidades.CAMPO_CONTRASENA, contrasena);

        Long idResultante = db.insert(Utilidades.TABLA_USUARIOS, Utilidades.CAMPO_ID, values);

        if (idResultante != null){
            Toast.makeText(RegistroNuevoUsuarioActivity.this, "Error en el registro", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(RegistroNuevoUsuarioActivity.this, "Registro de Usuario exitoso", Toast.LENGTH_LONG).show();
        }


    }

    public String obtenerFechadeHoy(){
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd"); //instanciamos el formateador de fechas
        Calendar calendar = Calendar.getInstance();

        Date dateobj = calendar.getTime();  //getTime() devuelve el resultado como tipo Date con hora y fecha horaria
        String formatedDate = dtf.format(dateobj);
        return formatedDate;
    }
}