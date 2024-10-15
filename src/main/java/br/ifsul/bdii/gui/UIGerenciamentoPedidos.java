package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ifsul.bdii.domain.Categoria;
import br.ifsul.bdii.domain.Estoque;
import br.ifsul.bdii.domain.Pedido;
import br.ifsul.bdii.domain.Produto;
import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.service.CategoriaService;
import br.ifsul.bdii.service.PedidoService;
import br.ifsul.bdii.service.ProdutoService;
import br.ifsul.bdii.service.EstoqueService;

public class UIGerenciamentoPedidos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtQuantidade;
    private JComboBox<Categoria> cmbCategoria;
    private JComboBox<Produto> cmbProduto;
    private JLabel txtStatus;
    private Usuario usuarioLogado;

    public UIGerenciamentoPedidos(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(10, 22, 89, 14);
        contentPane.add(lblCategoria);

        cmbCategoria = new JComboBox<>();
        cmbCategoria.setBounds(97, 19, 165, 20);
        contentPane.add(cmbCategoria);

        // Popular o combo box com as categorias
        List<Categoria> categorias = CategoriaService.findAll();
        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria);
        }

        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setBounds(10, 59, 89, 14);
        contentPane.add(lblProduto);

        cmbProduto = new JComboBox<>();
        cmbProduto.setBounds(97, 56, 165, 20);
        contentPane.add(cmbProduto);

        // Popular o combo box com os produtos
        List<Produto> produtos = ProdutoService.findAll();
        for (Produto produto : produtos) {
            cmbProduto.addItem(produto);
        }

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(10, 96, 89, 14);
        contentPane.add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(97, 93, 165, 20);
        contentPane.add(txtQuantidade);
        txtQuantidade.setColumns(10);

        JButton btnFazerPedido = new JButton("Fazer Pedido");
        btnFazerPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fazerPedido();
            }
        });
        btnFazerPedido.setBounds(97, 221, 89, 23);
        contentPane.add(btnFazerPedido);

        txtStatus = new JLabel("");
        txtStatus.setBounds(107, 255, 210, 14);
        contentPane.add(txtStatus);
    }

    public UIGerenciamentoPedidos() {
        //TODO Auto-generated constructor stub
    }

    private void fazerPedido() {
        Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
        Produto produto = (Produto) cmbProduto.getSelectedItem();
        int quantidade = Integer.parseInt(txtQuantidade.getText());

        // Verificar se a quantidade está disponível no estoque
        Estoque estoque = EstoqueService.findByProdutoId(produto.getProdutosId());
        if (estoque.getQuantidade() < quantidade) {
            txtStatus.setText("Quantidade não disponível no estoque.");
            return;
        }

        // Criar o pedido
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuarioLogado);
        pedido.setDataPedido(java.time.LocalDateTime.now());
        pedido.setStatus("Pendente");

        PedidoService.save(pedido);

        // Criar o pedido-produto
        if (PedidoService.savePedidoProduto(pedido.getPedidoId(), produto, quantidade)) {
            txtStatus.setText("Pedido feito com sucesso!");
        } else {
            txtStatus.setText("Erro ao fazer pedido.");
        }
    }
}