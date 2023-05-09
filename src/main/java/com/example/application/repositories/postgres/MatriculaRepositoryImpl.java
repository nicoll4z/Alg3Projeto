package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Matricula;
import com.example.application.repositories.MatriculaRepository;

public class MatriculaRepositoryImpl 
                    implements MatriculaRepository{

    public void inserir(Matricula matricula){
        Connection con;
        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "insert into matricula(cpf, data, numMatricula) values (?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, matricula.getCpf());
            pstm.setString(2, matricula.getData());
            pstm.setString(3, matricula.getNumMatricula());
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
            String sql = "delete from matricula where cpf=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cpf);
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }

    }

    public void editar(Matricula matricula){
        Connection con;
        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "UPDATE matricula SET cpf=?, data=?, numMatricula=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, matricula.getCpf());
            pstm.setString(2, matricula.getData());
            pstm.setString(3, matricula.getNumMatricula());
            pstm.setInt(4, matricula.getId());
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }

    }

    public List<Matricula> listar(){
        List<Matricula> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM matricula order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Matricula d = new Matricula();
                d.setCpf(rs.getString("cpf"));
                d.setData(rs.getString("data"));
                d.setNumMatricula(rs.getString("numMatricula"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista; 
    }
    
}
