package com.example.clockcheck.ui.home;

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
import com.example.clockcheck.adaptadores.ListaUsuariosAdapter;
import com.example.clockcheck.db.DbHelper;
import com.example.clockcheck.db.DbUsuarios;
import com.example.clockcheck.entidades.Usuario;
import com.example.clockcheck.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class UsuariosFragment extends Fragment {

    static ListaUsuariosAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Usuario> listUsuarios;
    FloatingActionButton fab;
    String datos[] = new String[7];
    ConexionSQLiteHelper conn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_clockcheck", null, 1);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_usuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listUsuarios = new ArrayList<>();
        consultarListaUsuarios();

        ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(getContext() ,listUsuarios);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void consultarListaUsuarios(){
        SQLiteDatabase db = conn.getWritableDatabase();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result.getContents() == null){
            Toast.makeText(getActivity(), "Lectura cancelada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();

            //Falta una condición que si no hay un determinado numero de diagonales / se cancele el procedimiento

            //Decodificación de la cadena de texto
            String cadena = result.getContents();
            String subcadena = "";
            int dato = 0, inicioCad = 0;

            for(int i=0; i<cadena.length(); i++){

                //Simbolo al actual en la cadena  ---*---
                String letra = cadena.substring(i, i+1);

                if(letra.equals("/")){
                    subcadena = cadena.substring(inicioCad, i);
                    inicioCad = i+1;
                    datos[dato] = subcadena;
                    dato++;
                }
            }

            DbUsuarios dbUsuarios = new DbUsuarios(getActivity());
            long id = dbUsuarios.insertarUsuarios(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);
            //adapter.notifyItemInserted(); agregar el dato al recyclerview también

            if(id>0) {
                Toast.makeText(getActivity(), "REGISTRO EXITOSO", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getActivity(), "REGISTRO FALLIDO", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

