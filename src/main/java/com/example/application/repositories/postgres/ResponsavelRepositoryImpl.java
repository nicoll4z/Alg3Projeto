package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Responsavel;
import com.example.application.repositories.ResponsavelRepository;

public class ResponsavelRepositoryImpl
                implements ResponsavelRepository{

    public void inserir(Responsavel responsavel){
        Connection con;

        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "insert into responsavel(nome, cpfaluno, cpfresp, endereco, telefone, email) values (?,?,?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, responsavel.getNome());
            pstm.setString(2, responsavel.getCpfAluno());
            pstm.setString(3, responsavel.getCpfResp());
            pstm.setString(4, responsavel.getEndereco());
            pstm.setString(5, responsavel.getTelefone());
            pstm.setString(6, responsavel.getEmail());
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }
    

    public void excluir(int id){
        Connection con;

        try{

            con = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "delete from responsavel where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }

    public List<Responsavel> listar(){
        List<Responsavel> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM responsavel order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Responsavel d = new Responsavel();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCpfAluno(rs.getString("cpfaluno"));
                d.setCpfResp(rs.getString("cpfresp"));
                d.setEndereco(rs.getString("endereco"));
                d.setTelefone(rs.getString("telefone"));
                d.setEmail(rs.getString("email"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista; 
    }

    public void editar(Responsavel responsavel){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "UPDATE responsavel SET nome=?, cpfAluno=?, cpfResp=?, endereco=?, telefone=?, email=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, responsavel.getNome());
            pstm.setString(2, responsavel.getCpfAluno());
            pstm.setString(3, responsavel.getCpfResp());
            pstm.setString(4, responsavel.getEndereco());
            pstm.setString(5, responsavel.getTelefone());
            pstm.setString(6, responsavel.getEmail());
            pstm.setInt(7, responsavel.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

}
