package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clockcheck.db.DbUsuarios;
import com.example.clockcheck.utilidades.Utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class RegistroNuevoAdministradorActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtFechaNacimiento, txtNumTelefono, txtCorreoElectronico, txtContrasena;
    Button btnCrearBD;
    SharedPreferences preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nuevo_administrador);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_clockcheck", null, 1);

        txtNombres = findViewById(R.id.txt_nombres);
        txtApellidos = findViewById(R.id.txt_apellidos);
        txtFechaNacimiento = findViewById(R.id.txt_fechanacimiento);
        txtNumTelefono = findViewById(R.id.txt_numtelefono);
        txtCorreoElectronico = findViewById(R.id.txt_correoelectronico);
        //fechaRegistro se obtiene al pulsar el boton
        txtContrasena = findViewById(R.id.txt_contraseña);

        btnCrearBD = findViewById(R.id.btn_crearBD);


        //Metodo donde se va a insertar datos por primera vez
        btnCrearBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombres.getText().toString().trim();

                preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor objEditor = preferencias.edit();
                //objeto de tipo sharedpreferences que ayuda a la aplicacion a saber que tipo de usuario se registro
                objEditor.putString("nombre", nombre);
                objEditor.putString("tipoUsuario", "administrador");
                objEditor.commit(); //sentencia que ejecuta el objeto editor y guarda permanentemente los datos

                registrarUsuarios();


                //metodo anterior para insertar usuarios en la vieja base de datos DbUsuarios ahora ConexiónSQLiteHelper
                /*
                DbUsuarios DbUsuarios = new DbUsuarios(RegistroNuevoAdministradorActivity.this);
                String fechaRegistro = obtenerFechadeHoy();


                //este metodo retorna un valor de tipo long, nos ayuda a saber si se realizo el procedimiento correctamente
                long id = DbUsuarios.insertarUsuarios(
                        txtNombres.getText().toString().trim(),
                        txtApellidos.getText().toString().trim(),
                        txtFechaNacimiento.getText().toString().trim(), //tengo que arreglar la manera en la que pido la fecha
                        txtNumTelefono.getText().toString().trim(),
                        txtCorreoElectronico.getText().toString().trim(),
                        fechaRegistro,
                        txtContrasena.getText().toString().trim()
                );

                //El administrador es el primer usuario en registrarse en la base de datos, su id = 1
                if (id > 0){
                    Toast.makeText(RegistroNuevoAdministradorActivity.this, "NUEVO ADMINISTRADOR REGISTRADO", Toast.LENGTH_LONG).show();
                    limpiar();

                    //despues de agregar al administrador cambiamos ala interfaz donde podra agregar mas usuarios
                    Intent intent = new Intent(RegistroNuevoAdministradorActivity.this, ActivityAdministrador.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(RegistroNuevoAdministradorActivity.this, "ERROR AL REGISTRAR NUEVOS DATOS", Toast.LENGTH_LONG).show();
                }
                */
            }
        });
    }

    public String obtenerFechadeHoy(){
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd"); //instanciamos el formateador de fechas
        Calendar calendar = Calendar.getInstance();

        Date dateobj = calendar.getTime();  //getTime() devuelve el resultado como tipo Date con hora y fecha horaria
        String formatedDate = dtf.format(dateobj);
        return formatedDate;
    }

    private void limpiar(){
        txtNombres.setText("");
        txtApellidos.setText("");
        txtFechaNacimiento.setText("");
        txtNumTelefono.setText("");
        txtCorreoElectronico.setText("");
        txtContrasena.setText("");
    }

    private void registrarUsuarios(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_clockcheck", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRES, txtNombres.getText().toString());
        values.put(Utilidades.CAMPO_APELLIDOS, txtApellidos.getText().toString());
        values.put(Utilidades.CAMPO_FECHANACIMIENTO, txtFechaNacimiento.getText().toString());
        values.put(Utilidades.CAMPO_NUMTELEFONO, txtNumTelefono.getText().toString());
        values.put(Utilidades.CAMPO_CORREOELECTRONICO, txtCorreoElectronico.getText().toString());
        values.put(Utilidades.CAMPO_FECHAREGISTRO, obtenerFechadeHoy());
        values.put(Utilidades.CAMPO_CONTRASENA, txtContrasena.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIOS, Utilidades.CAMPO_ID, values);

        if (idResultante > 0){
            Toast.makeText(getApplicationContext(), "Id Registro: "+idResultante, Toast.LENGTH_SHORT).show();
            db.close();

            //Toast.makeText(RegistroNuevoAdministradorActivity.this, "NUEVO ADMINISTRADOR REGISTRADO", Toast.LENGTH_LONG).show();
            //limpiar();

            //despues de agregar al administrador cambiamos ala interfaz donde podra agregar mas usuarios
            Intent intent = new Intent(RegistroNuevoAdministradorActivity.this, ActivityAdministrador.class);
            startActivity(intent);
        } else{
            Toast.makeText(RegistroNuevoAdministradorActivity.this, "ERROR AL REGISTRAR NUEVOS DATOS", Toast.LENGTH_LONG).show();
        }
    }
}