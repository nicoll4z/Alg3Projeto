package com.example.application.entidades;

public class Ponto {
    private int id;
    private String cpf;
    private String chegada;
    private String saida;

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getChegada() {
        return chegada;
    }
    public void setChegada(String chegada) {
        this.chegada = chegada;
    }
    public String getSaida() {
        return saida;
    }
    public void setSaida(String saida) {
        this.saida = saida;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
