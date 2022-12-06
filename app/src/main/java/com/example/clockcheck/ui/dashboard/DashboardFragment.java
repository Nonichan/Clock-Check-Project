package com.example.clockcheck.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.clockcheck.R;
import com.example.clockcheck.databinding.FragmentDashboardBinding;
import com.example.clockcheck.db.DbUsuarios;
import com.example.clockcheck.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    List<Usuario> elements;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        return view;
    }

    public void init(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        DbUsuarios db = new DbUsuarios(getContext());

        usuarios = db.mostrarUsuarios();
        //elements =
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}