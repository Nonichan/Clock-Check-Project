package com.example.clockcheck.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.clockcheck.R;
import com.example.clockcheck.entidades.Usuario;

import java.util.List;

public class ListaCVUsuariosAdapter extends RecyclerView.Adapter<ListaCVUsuariosAdapter.ViewHolder> {
    private List<Usuario> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListaCVUsuariosAdapter(List<Usuario> Usuario, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = Usuario;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public ListaCVUsuariosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_item_cardview, null);
        return new ListaCVUsuariosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaCVUsuariosAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Usuario> usuarios){ mData = usuarios; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage, color;
        TextView nombres, apellidos, numTelefono, correoElectronico;

        ViewHolder(View itemView){
            super(itemView);
            //iconImage = itemView.findViewById(R.id.iconImageView);
            nombres = itemView.findViewById(R.id.tv_nombres);
            apellidos = itemView.findViewById(R.id.tv_apellidos);
            numTelefono = itemView.findViewById(R.id.tv_numTelefono);
            correoElectronico = itemView.findViewById(R.id.tv_correoElectronico);
            color = itemView.findViewById(R.id.imageView_status);
        }

        void bindData(final Usuario item) {
            nombres.setText(item.getNombres());
            apellidos.setText(item.getApellidos());
            numTelefono.setText(item.getNumTelefono());
            correoElectronico.setText(item.getCorreoElectronico());
            color.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
        }
    }
}
