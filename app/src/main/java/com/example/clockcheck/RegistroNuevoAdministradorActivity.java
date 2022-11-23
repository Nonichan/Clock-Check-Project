package com.example.clockcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clockcheck.db.DbUsuarios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class RegistroNuevoAdministradorActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtFechaNacimiento, txtNumTelefono, txtCorreoElectronico, txtContrasena;
    Button btnCrearBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nuevo_administrador);

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
                } else{
                    Toast.makeText(RegistroNuevoAdministradorActivity.this, "ERROR AL REGISTRAR NUEVOS DATOS", Toast.LENGTH_LONG).show();
                }

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


}