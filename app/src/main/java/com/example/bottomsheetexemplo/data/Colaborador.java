package com.example.bottomsheetexemplo.data;

public class Colaborador {
    private int id;
    private String nome;
    private String cargo;
    private String cpf;
    private String endereco;
    private String email;
    private String caminhoFoto;
    private byte[] foto;

    public Colaborador(int id, String nome, String cargo, String cpf, String endereco, String email, String caminhoFoto, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.caminhoFoto = caminhoFoto;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
