package com.example.clockcheck.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockcheck.R;
import com.example.clockcheck.entidades.Asistencia;

import java.util.ArrayList;

public class ListaAsistenciasAdapter extends RecyclerView.Adapter<ListaAsistenciasAdapter.AsistenciaViewHolder> {
    LayoutInflater inflater;
    ArrayList<Asistencia> listaAsistencia;

    public ListaAsistenciasAdapter(Context context, ArrayList<Asistencia> listaAsistencia){
        this.inflater = LayoutInflater.from(context);
        this.listaAsistencia = listaAsistencia;
    }

    @NonNull
    @Override
    public AsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_asistencia, null, false);
        return new AsistenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciaViewHolder holder, int position) {
        holder.viewNombres.setText(listaAsistencia.get(position).getNombres());
        holder.viewApellidos.setText(listaAsistencia.get(position).getApellidos());
        holder.viewHoraLlegada.setText(listaAsistencia.get(position).getHoraLlegada());
        holder.viewHoraSalida.setText(listaAsistencia.get(position).getHoraSalida());
    }

    @Override
    public int getItemCount() {
        return listaAsistencia.size();
    }

    public class AsistenciaViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombres, viewApellidos, viewHoraLlegada, viewHoraSalida;

        public AsistenciaViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombres = itemView.findViewById(R.id.view_Nombres_asistencia);
            viewApellidos  = itemView.findViewById(R.id.view_Apellidos_asistencia);
            viewHoraLlegada = itemView.findViewById(R.id.view_horallegada);
            viewHoraSalida = itemView.findViewById(R.id.view_horaSalida);
        }
    }
}
