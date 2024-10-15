package br.ifsul.bdii.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.bdii.domain.Estoque;
import br.ifsul.bdii.service.DBConnection;


public class EstoqueService {

    public static Estoque save(Estoque entidade) {
        if (entidade == null)
            return null;

        final String insertStatement = "INSERT INTO Estoque(produto_id, quantidade) VALUES(?, ?);";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setLong(1, entidade.getProdutoId()); // seta o parametro 1 - primeira interrogacao
            ps.setInt(2, entidade.getQuantidade()); // seta o parametro 2 - segunda interrogacao

            // OBS.: note que o ID não deve ser informado, uma vez que o estoque ainda
            // não existe no banco, e no nosso caso, o ID é autoincrementado

            // executa no banco
            boolean executed = ps.execute();

            // se o comando executou corretamente (insert)
            if (executed) {
                // lê o ID que foi gerado e atualiza o estoque
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    // atualiza o objeto estoque, para ser retornado para quem chamou
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

    public static Estoque findById(Long produtosId) {
        final String selectStatement = "SELECT * FROM Estoque WHERE produto_id = ?;";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(selectStatement);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setLong(1, produtosId); // seta o parametro 1 - primeira interrogacao

            // executa no banco
            ResultSet rs = ps.executeQuery();

            // se o comando executou corretamente (select)
            if (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setId(rs.getLong("estoque_id"));
                estoque.setProdutoId(rs.getLong("produto_id"));
                estoque.setQuantidade(rs.getInt("quantidade"));

                // fecha/libera a conexão
                conn.close();

                return estoque;
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

    public static List<Estoque> findAll() {
        final String selectStatement = "SELECT * FROM Estoque;";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(selectStatement);

            // executa no banco
            ResultSet rs = ps.executeQuery();

            List<Estoque> estoques = new ArrayList<>();

            // se o comando executou corretamente (select)
            while (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setId(rs.getLong("estoque_id"));
                estoque.setProdutoId(rs.getLong("produto_id"));
                estoque.setQuantidade(rs.getInt("quantidade"));

                estoques.add(estoque);
            }

            // fecha/libera a conexão
            conn.close();

            return estoques;

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }
    }
    public static boolean updateQuantidade(Estoque estoque, int novaQuantidade) {
        final String updateStatement = "UPDATE Estoque SET quantidade = ? WHERE estoque_id = ?;";
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.setInt(1, novaQuantidade);
            ps.setLong(2, estoque.getEstoqueId());
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao atualizar a quantidade: " + e.getMessage());
            return false;
        }
    
    }
    public static Estoque findByProdutoId(Long produtoId) {
        Estoque estoque = null;
        final String query = "SELECT * FROM Estoque WHERE produto_id = ?;";
    
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, produtoId); // Buscar pelo produto_id, não estoque_id
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estoque = new Estoque();
                estoque.setEstoqueId(rs.getLong("estoque_id"));
                estoque.setProdutoId(rs.getLong("produto_id"));
                estoque.setQuantidade(rs.getInt("quantidade"));
                estoque.setPreco(rs.getBigDecimal("preco"));
                estoque.setDataAtualizacao(rs.getTimestamp("data_atualizacao").toLocalDateTime());
            }
    
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar estoque: " + e.getMessage());
        }
    
        return estoque;
    }
    
    
    public static void atualizarEstoque(Long produtoId, int novaQuantidade, String novoNome) {
        final String updateStatement = "UPDATE Estoque SET quantidade = ? WHERE estoque_id = ?";     
           try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.setInt(1, novaQuantidade);
            ps.setString(2, novoNome);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Atualiza a data
            ps.setLong(4, produtoId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Estoque atualizado. Linhas afetadas: " + rowsAffected);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updatePreco(Estoque estoque, String novoPreco) {
        final String updateStatement = "UPDATE Estoque SET preco = ? WHERE estoque_id = ?;";
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.setBigDecimal(1, new BigDecimal(novoPreco));
            ps.setLong(2, estoque.getEstoqueId());
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao atualizar o preço: " + e.getMessage());
            return false;
        }
    }
    
    
    }
    
