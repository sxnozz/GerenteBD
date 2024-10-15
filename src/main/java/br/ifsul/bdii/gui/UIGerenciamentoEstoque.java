package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ifsul.bdii.domain.Categoria;
import br.ifsul.bdii.domain.Estoque;
import br.ifsul.bdii.domain.Produto;
import br.ifsul.bdii.service.CategoriaService;
import br.ifsul.bdii.service.EstoqueService;
import br.ifsul.bdii.service.ProdutoService;

public class UIGerenciamentoEstoque extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtQuantidade;
    private JTextField txtPreco; // Adicionado o campo de texto txtPreco
    private JLabel txtStatus;
    private JComboBox<Categoria> cmbCategoria;
    private JComboBox<Produto> cmbProduto;
    private JComboBox<Estoque> cmbEstoque;

    public UIGerenciamentoEstoque() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 550); // Ajustado o tamanho da janela para acomodar o novo campo de texto
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(10, 22, 89, 14);
        contentPane.add(lblNewLabel);

        txtNome = new JTextField();
        txtNome.setBounds(97, 19, 165, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(10, 59, 89, 14);
        contentPane.add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(97, 56, 165, 20);
        contentPane.add(txtQuantidade);
        txtQuantidade.setColumns(10);

        JLabel lblPreco = new JLabel("Preço:"); // Adicionado o label para o campo de texto txtPreco
        lblPreco.setBounds(10, 96, 89, 14);
        contentPane.add(lblPreco);

        txtPreco = new JTextField(); // Adicionado o campo de texto txtPreco
        txtPreco.setBounds(97, 93, 165, 20);
        contentPane.add(txtPreco);
        txtPreco.setColumns(10);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(10, 127, 89, 14);
        contentPane.add(lblCategoria);

        cmbCategoria = new JComboBox<>();
        cmbCategoria.setBounds(97, 124, 165, 20);
        contentPane.add(cmbCategoria);

        // Popular o combo box com as categorias
        List<Categoria> categorias = CategoriaService.findAll();
        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria);
        }

        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setBounds(10, 158, 89, 14);
        contentPane.add(lblProduto);

        cmbProduto = new JComboBox<>();
        cmbProduto.setBounds(97, 155, 165, 20);
        contentPane.add(cmbProduto);

        // Popular o combo box com os produtos
        List<Produto> produtos = ProdutoService.findAll();
        for (Produto produto : produtos) {
            cmbProduto.addItem(produto);
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarProduto();
            }
        });
        btnSalvar.setBounds(97, 221, 89, 23);
        contentPane.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarEstoque();
            }
        });
        btnAtualizar.setBounds(97, 255, 89, 23);
        contentPane.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirProduto();
            }
        });
        btnExcluir.setBounds(97, 289, 89, 23);
        contentPane.add(btnExcluir);

        txtStatus = new JLabel("");
        txtStatus.setBounds (107, 323, 210, 14);
        contentPane.add(txtStatus);
    }

    private void salvarProduto() {
        Produto p = new Produto();
        p.setNome(txtNome.getText());
        p.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
        p.setCategoriaId(((Categoria) cmbCategoria.getSelectedItem()).getCategoriaId());
    
        // Salvar produto
        Produto produtoSalvo = ProdutoService.save(p);
        if (produtoSalvo != null) {
            // Criar o estoque para o produto
            Estoque estoque = new Estoque();
            estoque.setProdutoId(produtoSalvo.getProdutosId());
            estoque.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            estoque.setPreco(new BigDecimal(txtPreco.getText())); // Adicionando o preço
            
            EstoqueService.save(estoque); // Salvar o estoque
            txtStatus.setText("Produto e estoque cadastrados!");
        } else {
            txtStatus.setText("Erro ao cadastrar o produto.");
        }
    }

    private void atualizarEstoque() {
        try {
            Produto produto = (Produto) cmbProduto.getSelectedItem(); // Obtém o produto selecionado
            Estoque estoque = EstoqueService.findById(produto.getProdutosId()); // Busca estoque pelo produto_id

            if (estoque == null) {
                txtStatus.setText("Nenhum estoque encontrado para o produto selecionado.");
                return;
            }

            String quantidade = txtQuantidade.getText();
            String nome = txtNome.getText(); // Obtém o novo nome
            String preco = txtPreco.getText(); // Obtém o novo preço

            if (quantidade.isEmpty()) {
                txtStatus.setText("Por favor, informe a quantidade.");
                return;
            }

            int novaQuantidade = Integer.parseInt(quantidade);
            EstoqueService.updateQuantidade(estoque, novaQuantidade);
            ProdutoService.updateNome(produto, nome); // Atualiza o nome do produto
            EstoqueService.updatePreco(estoque, preco); // Atualiza o preço do estoque
            txtStatus.setText("Estoque atualizado!");
        } catch (Exception e) {
            txtStatus.setText("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    private void excluirProduto() {
        Produto produto = (Produto) cmbProduto.getSelectedItem();

        ProdutoService.delete(produto.getProdutosId());

        txtStatus.setText("Produto excluído!");
    }
} 