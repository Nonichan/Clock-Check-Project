//Esta clase es un adaptador que ayuda a generar y asignar cada uno de los elementos y opciones que tendra cada elemento de la lista
package com.example.clockcheck.adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockcheck.R;
import com.example.clockcheck.entidades.Usuario;

import java.util.ArrayList;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuarioViewHolder> implements View.OnClickListener{ //implementa el click a algun elemento

    LayoutInflater inflater;
    ArrayList<Usuario> listaUsuarios;

    //Listener
    private View.OnClickListener listener;

    public ListaUsuariosAdapter(Context context, ArrayList<Usuario> listaUsuarios){
        this.inflater = LayoutInflater.from(context);
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override //Este metodo nos ayuda a asignar el diseño que tendra cada elemento de la lista
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_usuario, null);  //cambios hechos para adaptarlo a un fragment
        View view = inflater.inflate(R.layout.lista_item_usuario, parent, false);
        view.setOnClickListener(this);
        return new UsuarioViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override //Aqui es donde vamos a poder asignar los elementos que corresponden a cada opcion
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        //Aqui indicamos que traiga el elemento nombres de la posicion que esta indicando
        holder.viewNombres.setText(listaUsuarios.get(position).getNombres());
        holder.viewApellidos.setText(listaUsuarios.get(position).getApellidos());
        holder.viewNumTelefono.setText(listaUsuarios.get(position).getNumTelefono());
        holder.viewCorreoElectronico.setText(listaUsuarios.get(position).getCorreoElectronico());
    }

    @Override  //Necesitamos mostrar el numero de elementos que tendra la lista
    public int getItemCount() {
        return listaUsuarios.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombres, viewApellidos, viewNumTelefono, viewCorreoElectronico;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombres = itemView.findViewById(R.id.viewNombres);
            viewApellidos = itemView.findViewById(R.id.viewApellidos);
            viewNumTelefono = itemView.findViewById(R.id.viewNumTelefono);
            viewCorreoElectronico = itemView.findViewById(R.id.viewCorreoElectronico);
        }
    }


}
