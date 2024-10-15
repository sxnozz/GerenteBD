package br.ifsul.bdii.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.bdii.domain.Produto;

public final class ProdutoService {

    /**
     * Método para cadastrar um produto no banco - gera um novo registro
     * 
     * @param newProduto - produto a ser inserido no banco
     * @return retorna o produto cadastrado, com o ID preenchido
     */
    public static Produto save(Produto entidade) {

        if (entidade == null)
            return null;
    
        final String insertStatement = "INSERT INTO Produtos(nome, descricao, categoria_id) VALUES(?, ?, ?);";
    
        try {
    
            // abre a conexao
            Connection conn = DBConnection.openConnection();
    
            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
    
            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setString(1, entidade.getNome()); // seta o parametro 1 - primeira interrogacao
            ps.setString(2, entidade.getDescricao()); // seta o parametro 2 - segunda interrogacao
    
            // Verifica se o campo categoriaId é null antes de tentar chamar o método longValue()
            if (entidade.getCategoriaId() != null) {
                ps.setLong(3, entidade.getCategoriaId().longValue()); // seta o parametro 3 - terceira interrogacao
            } else {
                ps.setNull(3, java.sql.Types.BIGINT); // seta o parametro 3 como null se categoriaId for null
            }
    
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
                    entidade.setProdutosId(id);
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

    /**
     * Lista todos os produtos da tabela Produtos
     * 
     * @return Lista de produtos
     */
    public static List<Produto> findAll() {
        List<Produto> lista = new ArrayList<>();
    
        final String query = "SELECT * FROM Produtos;";
    
        try {
            Connection c = DBConnection.openConnection();
    
            // statement é um objeto para representar um comando no banco
            Statement st = c.createStatement();
    
            ResultSet rs = st.executeQuery(query); // executa o comando
    
            // Esse while percorre linha por linha do resutlado da query
            while (rs.next()) {
    
                // cada GET deve respeitar o tipo de dado de cada coluna
                Long id = rs.getLong("produto_id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Long categoriaId = rs.getLong("categoria_id");
    
                // cria o objeto produto com os dados lidos da linha em consideracao
                Produto produto = new Produto(id, nome, descricao, categoriaId);
    
                // adiciona lista
                lista.add(produto);
    
                // e vai para o proximo registro
            }
    
            c.close(); // fecha/libera a conexão
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }
    
        return lista;
    }

    /**
     * Método para deletar um produto no banco
     * 
     * @param id - ID do produto a ser deletado
     * @return true se o produto foi deletado com sucesso, false caso contrário
     */
    public static boolean delete(Long id) {
        final String deleteStatement = "DELETE FROM Produtos WHERE produto_id = ?;";
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(deleteStatement);
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateNome(Produto produto, String novoNome) {
        final String updateStatement = "UPDATE Produtos SET nome = ? WHERE produto_id = ?;";
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.setString(1, novoNome);
            ps.setLong(2, produto.getProdutosId());
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao atualizar o nome: " + e.getMessage());
            return false;
        }
    }


    public static boolean updatePreco(Produto produto, String novoPreco) {
        final String updateStatement = "UPDATE Estoque SET preco = ? WHERE produto_id = ?;";
        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.setBigDecimal(1, new BigDecimal(novoPreco));
            ps.setLong(2, produto.getProdutosId());
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao atualizar o preço: " + e.getMessage());
            return false;
        }
    }
     }

