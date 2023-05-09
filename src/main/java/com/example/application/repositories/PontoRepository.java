package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Ponto;

public interface PontoRepository {
    public void inserir(Ponto ponto);
    public void editar(Ponto ponto);
    public void excluir(String cpf);
    public List<Ponto> listar();
}
