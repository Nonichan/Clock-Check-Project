package com.example.clockcheck.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockcheck.ActivityAdministrador;
import com.example.clockcheck.ConexionSQLiteHelper;
import com.example.clockcheck.R;
import com.example.clockcheck.RegistroPatronActivity;
import com.example.clockcheck.adaptadores.ListaUsuariosAdapter;
import com.example.clockcheck.db.DbHelper;
import com.example.clockcheck.db.DbUsuarios;
import com.example.clockcheck.entidades.Usuario;
import com.example.clockcheck.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UsuariosFragment extends Fragment {

    static ListaUsuariosAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Usuario> listUsuarios;
    FloatingActionButton fab;
    String datos[] = new String[6]; //son 6 datos que va a recibir del QR
    ConexionSQLiteHelper conn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //instancia de la base de datos, sirve para hacer una conexion
        conn = new ConexionSQLiteHelper(getActivity(), "bd_clockcheck", null, 1);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_usuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listUsuarios = new ArrayList<>();
        consultarListaUsuarios(); //llamada al metodo que llena el arraylist listUsuarios

        ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(getContext() ,listUsuarios);
        recyclerView.setAdapter(adapter);

        //implementar un boton para insertar usuarios usando la Camara del dispositivo
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "El boton funciona", Toast.LENGTH_SHORT).show();
                escanear();
            }
        });
        return view;
    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(UsuariosFragment.this); //para usarlo en un fragment cambia el metodo de la clase IntentIntegrator
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    //este metodo sirve para consultar los usuarios de la base de datos para mostrarlos en una lista recyclerview
    private void consultarListaUsuarios(){
        SQLiteDatabase db = conn.getWritableDatabase();           //usamos la instancia de conexion que cree anteriormente para manipular la base de datos
        Usuario usuario = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIOS, null);
        while (cursor.moveToNext()){
            usuario = new Usuario();
            //usuario.setId(cursor.getInt(0));
            usuario.setNombres(cursor.getString(1));
            usuario.setApellidos(cursor.getString(2));
            usuario.setNumTelefono(cursor.getString(4));
            usuario.setCorreoElectronico(cursor.getString(6));
            listUsuarios.add(usuario);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //metodo del escaner que se activa al detectar un codigo QR con la camara
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getActivity(), "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                String cadena = result.getContents();
                String subcadena = "";
                int dato = 0, inicioCad = 0;

                System.out.println(cadena);

                //El programa analizara el texto contenido en el QR y guardará los datos en un String [array]
                try {
                    for (int i = 0; i < cadena.length(); i++) {

                        //Simbolo al actual en la cadena  ---*---
                        String letra = cadena.substring(i, i + 1);

                        if (letra.equals("/")) {
                            subcadena = cadena.substring(inicioCad, i);
                            inicioCad = i + 1;
                            datos[dato] = subcadena;
                            dato++;
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Error en el dato fechaNacimiento: valor no admitido: '/'", Toast.LENGTH_LONG).show();
                    System.out.println(e);
                }

                System.out.println("nombres: ["+datos[0]+"]");
                System.out.println("apellidos: ["+datos[1]+"]");
                System.out.println("fechaNacimiento: ["+datos[2]+"]");
                System.out.println("numeroTelefono: ["+datos[3]+"]");
                System.out.println("correoElectronico: ["+datos[4]+"]");
                System.out.println("contraseña: ["+datos[5]+"]");

                RegistrarUsuarios(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                Toast.makeText(getActivity(), "RegistroExitoso", Toast.LENGTH_SHORT).show();


                agregarItemRecyclerView(datos[0], datos[1], datos[3], datos[4]);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

        Toast.makeText(getContext(), "Id Registro: ["+idResultante+"]", Toast.LENGTH_LONG).show();
    }

    //esto no sale bien todavia  //se podría arreglar mandando a llamar al activity administrador para que se refresque
    private void agregarItemRecyclerView(String nombres, String apellidos, String numtelefono, String correoelectronico){
        Usuario usuario = new Usuario();

        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setNumTelefono(numtelefono);
        usuario.setCorreoElectronico(correoelectronico);

        listUsuarios.add(usuario);
        System.out.println("listUsuarios= "+ listUsuarios.get(2));
        //adapter.notifyItemInserted();
        recyclerView.scrollToPosition(listUsuarios.size()-1);
    }

    public String obtenerFechadeHoy(){
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd"); //instanciamos el formateador de fechas
        Calendar calendar = Calendar.getInstance();

        Date dateobj = calendar.getTime();  //getTime() devuelve el resultado como tipo Date con hora y fecha horaria
        String formatedDate = dtf.format(dateobj);
        return formatedDate;
    }

}

