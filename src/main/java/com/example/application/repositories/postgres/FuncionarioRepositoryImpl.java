package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Funcionario;
import com.example.application.repositories.FuncionarioRepository;

public class FuncionarioRepositoryImpl 
                    implements FuncionarioRepository{
    
    public void inserir(Funcionario funcionario){
        Connection con;

        try{
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "insert into funcionario(nome, cpf, telefone, email, categoria, salario) values (?,?,?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, funcionario.getNome());
            pstm.setString(2, funcionario.getCpf());
            pstm.setString(3, funcionario.getTelefone());
            pstm.setString(4, funcionario.getEmail());
            pstm.setString(5, funcionario.getCategoria());
            pstm.setDouble(6, funcionario.getSalario());
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
            String sql = "delete from funcionario where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        }catch(Exception error){
            throw new RuntimeException(error);
        }
    }

    public List<Funcionario> listar(){
        List<Funcionario> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "SELECT * FROM funcionario order by id;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Funcionario d = new Funcionario();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCpf(rs.getString("cpf"));
                d.setTelefone(rs.getString("telefone"));
                d.setEmail(rs.getString("email"));
                d.setCategoria(rs.getString("categoria"));
                d.setSalario(rs.getDouble("salario"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista; 
    }

    public void editar(Funcionario funcionario){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/teste","postgres", "12345");
            String sql = "UPDATE funcionario SET nome=?, cpf=?, telefone=?, email=?, categoria=?, salario=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, funcionario.getNome());
            pstm.setString(2, funcionario.getCpf());
            pstm.setString(3, funcionario.getTelefone());
            pstm.setString(4, funcionario.getEmail());
            pstm.setString(5, funcionario.getCategoria());
            pstm.setDouble(6, funcionario.getSalario());
            pstm.setInt(7, funcionario.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

}
