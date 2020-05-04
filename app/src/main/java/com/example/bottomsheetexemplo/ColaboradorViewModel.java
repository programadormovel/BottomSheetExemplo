package com.example.bottomsheetexemplo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bottomsheetexemplo.data.Colaborador;
import com.example.bottomsheetexemplo.data.dao.ColaboradorDao;

public class ColaboradorViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public String mensagemRetorno = "Colaborador não inserido!";

    public final MutableLiveData<Colaborador> colaboradorLiveData =
            new MutableLiveData<>();

    public LiveData<Colaborador> getColaborador(){
        return colaboradorLiveData;
    }

    public ColaboradorViewModel() {

    }

    void doAction(Colaborador colaborador){
        int x = ColaboradorDao.inserirColaborador(colaborador);
        if(x>0) mensagemRetorno = "Colaborador Inserido com Sucesso";
    }
}
