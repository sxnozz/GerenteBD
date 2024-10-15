package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UIMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public UIMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnGerenciamentoPedidos = new JButton("Gerenciamento de Pedidos");
        btnGerenciamentoPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UIGerenciamentoPedidos().setVisible(true);
            }
        });
        btnGerenciamentoPedidos.setBounds(10, 22, 200, 23);
        contentPane.add(btnGerenciamentoPedidos);

        JButton btnCriarCategoria = new JButton("Criar Categoria");
        btnCriarCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UICadastroCategoria().setVisible(true);
            }
        });
        btnCriarCategoria.setBounds(10, 59, 200, 23);
        contentPane.add(btnCriarCategoria);

        JButton btnGerenciamentoEstoque = new JButton("Gerenciamento de Estoque");
        btnGerenciamentoEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UIGerenciamentoEstoque().setVisible(true);
            }
        });
        btnGerenciamentoEstoque.setBounds(10, 96, 200, 23);
        contentPane.add(btnGerenciamentoEstoque);
    }
}