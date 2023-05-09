package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Ponto;
import com.example.application.repositories.PontoRepository;

public class PontoRepositoryImpl 
                implements PontoRepository{

     public void inserir(Ponto ponto){
        Connection con;
        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "insert into ponto(cpf, chegada) values (?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, ponto.getCpf());
            pstm.setString(2, ponto.getChegada());
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }

    public void excluir(String cpf){
        Connection con;
        try{

            con = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "delete from ponto where cpf=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cpf);
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }
    public void editar(Ponto ponto){
        Connection con;
        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "update ponto set saida=? where cpf=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, ponto.getSaida());
            pstm.setString(2, ponto.getCpf());
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }

    public List<Ponto> listar(){
        List<Ponto> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM ponto order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Ponto d = new Ponto();
                d.setCpf(rs.getString("cpf"));
                d.setChegada(rs.getString("chegada"));
                d.setSaida(rs.getString("saida"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista; 
    }
}
