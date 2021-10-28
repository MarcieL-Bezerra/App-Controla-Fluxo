package com.santosstampa.controla_de_fluxo;

public class Cliente {
    int codigo;
    String nome;
    String telefone;
    String email;
    String classificacao;

    public Cliente(){


    }
    public Cliente(int _codigo, String _nome, String _telefone, String _email, String _classificacao){
        this.codigo = _codigo;
        this.nome = _nome;
        this.telefone = _telefone;
        this.email = _email;
        this.classificacao = _classificacao;
    }

    public Cliente(String _nome, String _telefone, String _email, String _classificacao){
        this.nome = _nome;
        this.telefone = _telefone;
        this.email = _email;
        this.classificacao = _classificacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
}
