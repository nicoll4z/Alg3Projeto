package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Atividade;
import com.example.application.repositories.AtividadeRepository;

public class AtividadeRepositoryImpl
                        implements AtividadeRepository{
 
    public void inserir(Atividade atividade){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "INSERT INTO atividades (nome, cargahoraria) VALUES (?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, atividade.getNome());
            pstm.setInt(2,atividade.getCargaHoraria());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }
 
 
    public List<Atividade> listar(){
        List<Atividade> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM atividades order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Atividade d = new Atividade();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setCargaHoraria(rs.getInt("cargahoraria"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista;
    }
 
    public void excluir(int id){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "DELETE FROM atividades where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
 
    public void editar(Atividade atividade){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "UPDATE atividades SET nome=?, cargahoraria=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,atividade.getNome());
            pstm.setInt(2, atividade.getCargaHoraria());
            pstm.setInt(3, atividade.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}
