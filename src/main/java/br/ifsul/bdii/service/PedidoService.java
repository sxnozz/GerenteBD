package br.ifsul.bdii.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.bdii.domain.Pedido;
import br.ifsul.bdii.domain.Produto;
import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.service.DBConnection;

public class PedidoService {

    public static Pedido save(Pedido pedido) {
        if (pedido == null)
            return null;

        final String insertStatement = "INSERT INTO Pedidos(usuario_id, data_pedido, status) VALUES(?, ?, ?);";

        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, pedido.getUsuarioId().getId()); // Corrigido para usar usuarioId
            ps.setDate(2, java.sql.Date.valueOf(pedido.getDataPedido().toLocalDate())); // Ajuste de data
            ps.setString(3, pedido.getStatus());

            ps.executeUpdate(); // Executa a inserção
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                pedido.setPedidoId(id); // Atualiza o ID do pedido
            }

            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao inserir: " + e.getMessage());
            return null;
        }

        return pedido;
    }

    public static boolean savePedidoProduto(Long pedidoId, Produto produto, int quantidade) {
        final String insertStatement = "INSERT INTO Pedido_produto(pedido_id, produto_id, quantidade) VALUES(?, ?, ?);";

        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(insertStatement);
            ps.setLong(1, pedidoId);
            ps.setLong(2, produto.getProdutosId());
            ps.setInt(3, quantidade);
            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao inserir: " + e.getMessage());
            return false;
        }

        return true;
    }

    public static List<Pedido> findAllPedidosByUsuarioId(Long usuarioId) {
        final String selectStatement = "SELECT * FROM Pedidos WHERE usuario_id = ?;";

        List<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(selectStatement);
 ps.setLong(1, usuarioId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setPedidoId(rs.getLong("pedido_id"));
                pedido.setUsuarioId(UsuarioService.findById(rs.getLong("usuario_id"))); // Verifique se este método está correto
                pedido.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime()); // Ajuste de data
                pedido.setStatus(rs.getString("status"));

                pedidos.add(pedido);
            }

            conn.close();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar: " + e.getMessage());
        }

        return pedidos;
    }
}