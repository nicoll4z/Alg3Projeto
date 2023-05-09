package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Matricula;

public interface MatriculaRepository {
    public void inserir(Matricula matricula);
    public void editar(Matricula matricula);
    public void excluir(String cpf);
    public List<Matricula> listar();
}
