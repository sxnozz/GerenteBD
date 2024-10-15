package br.ifsul.bdii.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.bdii.domain.Categoria;

public final class CategoriaService {

    /**
     * Método para cadastrar uma categoria no banco - gera um novo registro
     * 
     * @param newCategoria - categoria a ser inserida no banco
     * @return retorna a categoria cadastrada, com o ID preenchido
     */
    public static Categoria save(Categoria entidade) {

        if (entidade == null)
            return null;

        final String insertStatement = "INSERT INTO Categorias(nome) VALUES(?);";

        try {

            // abre a conexao
            Connection conn = DBConnection.openConnection();

            // o prepared statement deve ser usado quando o comando requer parâmetros
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);

            // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
            ps.setString(1, entidade.getNome()); // seta o parametro 1 - primeira interrogacao

            // OBS.: note que o ID não deve ser informado, uma vez que a categoria ainda
            // não existe no banco, e no nosso caso, o ID é autoincrementado

            // executa no banco
            boolean executed = ps.execute();

            // se o comando executou corretamente (insert)
            if (executed) {
                // lê o ID que foi gerado e atualiza a categoria
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    // atualiza o objeto categoria, para ser retornado para quem chamou
                    entidade.setCategoriaId(id);
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
     * Lista todas as categorias da tabela Categorias
     * 
     * @return Lista de categorias
     */
    public static List<Categoria> findAll() {
        List<Categoria> lista = new ArrayList<>();

        final String query = "SELECT * FROM Categorias;";

        try {
            Connection c = DBConnection.openConnection();

            // statement é um objeto para representar um comando no banco
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query); // executa o comando

            // Esse while percorre linha por linha do resutlado da query
            while (rs.next()) {

                // cada GET deve respeitar o tipo de dado de cada coluna
                Long id = rs.getLong("categoria_id");
                String nome = rs.getString("nome");

                // cria o objeto categoria com os dados lidos da linha em consideracao
                Categoria categoria = new Categoria(nome, id);

                // adiciona lista
                lista.add(categoria);

                // e vai para o proximo registro
            }

            c.close(); // fecha/libera a conexão
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }

        return lista;
    }


/**
 * Busca uma categoria pelo nome
 * 
 * @param nome - nome da categoria a ser buscada
 * @return Categoria encontrada
 */
public static Categoria findByNome(String nome) {
    Categoria categoria = null;

    final String query = "SELECT * FROM Categorias WHERE nome = ?;";

    try {
        Connection c = DBConnection.openConnection();

        // o prepared statement deve ser usado quando o comando requer parâmetros
        PreparedStatement ps = c.prepareStatement(query);

        // seta os parametros - cuidado com os tipos de dados de cada coluna/atributo
        ps.setString(1, nome); // seta o parametro 1 - primeira interrogacao

        ResultSet rs = ps.executeQuery(); // executa o comando

        // Esse while percorre linha por linha do resutlado da query
        if (rs.next()) {

            // cada GET deve respeitar o tipo de dado de cada coluna
            Long categoriaId = rs.getLong("categoria_id");
            String nomeCategoria = rs.getString("nome");

            // cria o objeto categoria com os dados lidos da linha em consideracao
            categoria = new Categoria(nomeCategoria, categoriaId);
        }

        c.close(); // fecha/libera a conexão
    } catch (SQLException e) {
        System.err.println("Ocorreu um erro: " + e.getMessage());
    }

    return categoria;

}
}

