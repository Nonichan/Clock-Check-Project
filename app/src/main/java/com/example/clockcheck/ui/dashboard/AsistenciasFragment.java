package com.example.clockcheck.ui.dashboard;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockcheck.ConexionSQLiteHelper;
import com.example.clockcheck.R;
import com.example.clockcheck.adaptadores.ListaAsistenciasAdapter;
import com.example.clockcheck.databinding.FragmentDashboardBinding;
import com.example.clockcheck.db.DbUsuarios;
import com.example.clockcheck.entidades.Asistencia;
import com.example.clockcheck.entidades.Usuario;
import com.example.clockcheck.ui.home.UsuariosFragment;
import com.example.clockcheck.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AsistenciasFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    ArrayList<Asistencia> listAsistencia;
    ConexionSQLiteHelper conn;
    RecyclerView recyclerViewAsistencias;
    FloatingActionButton fab;
    String datos[] = new String[4]; //son 6 datos que va a recibir del QR

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_clockcheck", null, 1);
        listAsistencia = new ArrayList<>();
        recyclerViewAsistencias = (RecyclerView) view.findViewById(R.id.recyclerView_asistencias);
        recyclerViewAsistencias.setLayoutManager(new LinearLayoutManager(getContext()));

        consultarListaAsistencias();

        ListaAsistenciasAdapter adapter = new ListaAsistenciasAdapter(getContext(), listAsistencia);
        recyclerViewAsistencias.setAdapter(adapter);

        fab = view.findViewById(R.id.fab_asistencias);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "El boton funciona", Toast.LENGTH_SHORT).show();
                escanear();
            }
        });
        return view;
    }

    public void escanear(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(AsistenciasFragment.this); //para usarlo en un fragment cambia el metodo de la clase IntentIntegrator
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

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
                    Toast.makeText(getActivity(), "Error en [dato]", Toast.LENGTH_LONG).show();
                    System.out.println("Error: "+e);
                }
                RegistrarUsuarios(datos[0], datos[1], datos[2], datos[3]);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void RegistrarUsuarios(String nombres, String apellidos, String horaActual, String calidadAsistencia){
        SQLiteDatabase db = conn.getWritableDatabase();
        String id = null;

        Cursor cursor = db.rawQuery("SELECT id FROM usuarios WHERE nombres = '"+nombres+"' AND apellidos = '"+apellidos+"'", null);
        if (cursor!=null){

            while(cursor.moveToNext())
            id = cursor.getString(0);
        }

        ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_ID, id);
            values.put(Utilidades.CAMPO_HORALLEGADA, horaActual);
            values.put(Utilidades.CAMPO_HORASALIDA, "null");
            values.put(Utilidades.CAMPO_ASISTENCIA, "1");
            values.put(Utilidades.CAMPO_CALIDADASISTENCIA, "null");


        Long idResultante = db.insert(Utilidades.TABLA_ASISTENCIAS, Utilidades.CAMPO_ID, values);
        Toast.makeText(getContext(), "Id Registro: ["+idResultante+"]", Toast.LENGTH_LONG).show();
    }

    private void consultarListaAsistencias(){
        SQLiteDatabase db = conn.getWritableDatabase();

        Asistencia asistencia = null;
        Cursor cursor = db.rawQuery(Utilidades.SELECT_USUARIOS_ASISTENCIAS, null);

        while (cursor.moveToNext()){
            asistencia = new Asistencia();
            asistencia.setNombres(cursor.getString(0));
            asistencia.setApellidos(cursor.getString(1));
            asistencia.setHoraLlegada(cursor.getString(2));
            asistencia.setCalidadAsistencia(cursor.getString(3));

            listAsistencia.add(asistencia);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}