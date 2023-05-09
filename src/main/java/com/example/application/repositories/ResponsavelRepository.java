package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Responsavel;

public interface ResponsavelRepository{

    public void inserir(Responsavel responsavel);
    public void excluir(int id);
    public void editar(Responsavel responsavel);
    public List<Responsavel> listar();
}
