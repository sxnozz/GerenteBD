package br.ifsul.bdii.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.service.DBConnection;

public class UsuarioService {

    public static Usuario save(Usuario entidade) {
        if (entidade == null)
            return null;
    
        final String insertStatement = "INSERT INTO Usuarios(nome, email, senha) VALUES(?, ?, ?);";
    
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getEmail());
            ps.setString(3, entidade.getSenha());
    
            // Executa a inserção
            int affectedRows = ps.executeUpdate(); // Altere para executeUpdate()
    
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    entidade.setId(id);
                }
            }
    
            conn.close();
    
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao inserir: " + e.getMessage());
            return null;
        }
    
        return entidade;
    }

    public static Usuario findByEmailAndSenha(String email, String senha) {
        final String selectStatement = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?;";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn .prepareStatement(selectStatement);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setString(1, email); // seta o parametro 1 - primeira interrogacao
            ps.setString(2, senha); // seta o parametro 2 - segunda interrogacao

            // executa no banco
            ResultSet rs = ps.executeQuery();

            // se o comando executou corretamente (select)
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("usuario_id")); // alterado aqui
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                // fecha/libera a conexão
                conn.close();

                return usuario;
            }

            // fecha/libera a conexão
            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }

        return null;
    }

    public static Usuario findById(Long id) {
        final String selectStatement = "SELECT * FROM Usuarios WHERE usuario_id = ?;";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(selectStatement);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setLong(1, id); // seta o parametro 1 - primeira interrogacao

            // executa no banco
            ResultSet rs = ps.executeQuery();

            // se o comando executou corretamente (select)
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("usuario_id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                // fecha/libera a conexão
                conn.close();

                return usuario;
            }

            // fecha/libera a conexão
            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }

        return null;
    }
}