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

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setString(1, entidade.getNome()); // seta o parametro 1 - primeira interrogacao
            ps.setString(2, entidade.getEmail()); // seta o parametro 2 - segunda interrogacao
            ps.setString(3, entidade.getSenha()); // seta o parametro 3 - terceira interrogacao

            // OBS.: note que o ID não deve ser informado, uma vez que o produto ainda
            // não existe no banco, e no nosso caso, o ID é autoincrementado

            // executa no banco
            boolean executed = ps.execute();

            // se o comando executou corretamente (insert)
            if (executed) {
                // lê o ID que foi gerado e atualiza o produto
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    // atualiza o objeto produto, para ser retornado para quem chamou
                    entidade.setId(id);
                }
            }

            // fecha/libera a conexão
            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao inserir: " + e.getMessage());
            // e.printStackTrace();
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
            PreparedStatement ps = conn.prepareStatement(selectStatement);

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
}