package com.example.bottomsheetexemplo;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomsheetexemplo.data.Colaborador;

public class ColaboradorFragment extends Fragment {

    private ColaboradorViewModel mViewModel;
    private AppCompatEditText edtNome, edtCargo, edtCpf, edtEndereco, edtEmail;

    public static ColaboradorFragment newInstance() {
        return new ColaboradorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.colaborador_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtNome = getActivity().findViewById(R.id.edtNome);
        edtCargo = getActivity().findViewById(R.id.edtCargo);
        edtCpf = getActivity().findViewById(R.id.edtCpf);
        edtEndereco = getActivity().findViewById(R.id.edtEndereco);
        edtEmail = getActivity().findViewById(R.id.edtEmail);

        mViewModel = new ViewModelProvider(requireActivity())
                .get(ColaboradorViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getColaborador().observe(getViewLifecycleOwner(),
                new Observer<Colaborador>() {
                    @Override
                    public void onChanged(Colaborador colaborador) {
                        edtNome.setText(colaborador.getNome());
                        edtCargo.setText(colaborador.getCargo());
                        edtCpf.setText(colaborador.getCpf());
                        edtEndereco.setText(colaborador.getEndereco());
                        edtEmail.setText(colaborador.getEmail());
                    }
                });

        getActivity().findViewById(R.id.btnSalvarColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Colaborador colaborador = new Colaborador(
                        0, edtNome.getText().toString(),
                        edtCargo.getText().toString(),
                        edtCpf.getText().toString(),
                        edtEndereco.getText().toString(),
                        edtEmail.getText().toString(),
                        null, null);

                        mViewModel.doAction(colaborador);
                    }
                });
    }

}
