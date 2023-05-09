package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Aluno;
import com.example.application.repositories.AlunoRepository;

public class AlunoRepositoryImpl 
                    implements AlunoRepository{

    public void inserir(Aluno aluno){
        Connection con;

        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "insert into Aluno(nome, cpfaluno, cpfresp, nascimento) values (?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aluno.getNome());
            pstm.setString(2, aluno.getCpfAluno());
            pstm.setString(3, aluno.getCpfResp());
            pstm.setString(4, aluno.getNascimento());
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
            String sql = "delete from aluno where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }

    public List<Aluno> listar(){
        List<Aluno> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM aluno order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Aluno d = new Aluno();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setNascimento(rs.getString("nascimento"));
                d.setCpfAluno(rs.getString("cpfaluno"));
                d.setCpfResp(rs.getString("cpfresp"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista; 
    }

    public void editar(Aluno aluno){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "UPDATE aluno SET nome=?, cpfAluno=?, cpfResp=?, nascimento=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aluno.getNome());
            pstm.setString(2, aluno.getCpfAluno());
            pstm.setString(3, aluno.getCpfResp());
            pstm.setString(4, aluno.getNascimento());
            pstm.setInt(5, aluno.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}
