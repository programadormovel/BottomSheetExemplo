package com.example.bottomsheetexemplo.data.dao;

import com.example.bottomsheetexemplo.data.Colaborador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColaboradorDao {
    public static int inserirColaborador(Colaborador colab){
        int resultado = 0;
        PreparedStatement pst;

        try {
            pst = Conexao.conexao().prepareStatement(
                    "Insert Into Colaborador " +
                            "(nome, cargo, cpf, endereco, email, caminhoFoto, foto) values " +
                            "(?,?,?,?,?,?,?)");
            //pst.setInt(1, colab.getId());
            pst.setString(1, colab.getNome());
            pst.setString(2, colab.getCargo());
            pst.setString(3, colab.getCpf());
            pst.setString(4, colab.getEndereco());
            pst.setString(5, colab.getEmail());
            pst.setString(6, colab.getCaminhoFoto());
            pst.setBytes(7, colab.getFoto());

            resultado = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static ArrayList<Colaborador> pesquisarColaboradores(){
        ArrayList<Colaborador> listaColabs;
        listaColabs = new ArrayList<Colaborador>();
        Colaborador colab;
        PreparedStatement pst;
        ResultSet res;

        try {
            pst = Conexao.conexao().prepareStatement("" +
                    "Select * from Colaborador");

            res = pst.executeQuery();

            while(res.next()){
                colab = new Colaborador(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getBytes(8)
                );

                listaColabs.add(colab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaColabs;
    }
}
