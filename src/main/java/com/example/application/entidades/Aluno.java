package com.example.application.entidades;

public class Aluno {
    private String nascimento;
    private String cpfAluno;
    private String cpfResp;
    private String nome;
    private int id;
    
    public String getCpfAluno() {
        return cpfAluno;
    }
    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }
    public String getCpfResp() {
        return cpfResp;
    }
    public void setCpfResp(String cpfResp) {
        this.cpfResp = cpfResp;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNascimento() {
        return nascimento;
    }
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
}
