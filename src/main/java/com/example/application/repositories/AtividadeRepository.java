package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Atividade;

public interface AtividadeRepository {
    public void inserir(Atividade atividade);
    public void editar(Atividade atividade);
    public void excluir(int id);
    public List<Atividade> listar();
}
