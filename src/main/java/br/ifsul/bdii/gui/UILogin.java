package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.service.UsuarioService;

public class UILogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JLabel txtStatus;

    public UILogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 22, 89, 14);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(97, 19, 165, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 59, 89, 14);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(97, 56, 165, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(10, 96, 89, 14);
        contentPane.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(97, 93, 165, 20);
        contentPane.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entrar();
            }
        });
        btnEntrar.setBounds(10, 134, 89, 23);
        contentPane.add(btnEntrar);

        JButton btnCriarConta = new JButton("Criar Conta");
        btnCriarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarConta();
            }
        });
        btnCriarConta.setBounds(97, 134, 89, 23);
        contentPane.add(btnCriarConta);

        txtStatus = new JLabel("");
        txtStatus.setBounds(107, 168, 210, 14);
        contentPane.add(txtStatus);
    }

    private void entrar() {
        Usuario u = new Usuario();
        u.setNome(txtNome.getText());
        u.setEmail(txtEmail.getText());
        u.setSenha(new String(txtSenha.getPassword()));

        Usuario usuario = UsuarioService.findByEmailAndSenha(u.getEmail(), u.getSenha());

        if (usuario != null) {
            UICadastroCategoria uicc = new UICadastroCategoria();
            uicc.setVisible(true);

            UIGerenciamentoEstoque uige = new UIGerenciamentoEstoque();
            uige.setVisible(true);

            dispose();
        } else {
            txtStatus.setText("Email ou senha incorretos!");
        }
    }

    private void criarConta() {
        Usuario u = new Usuario();
        u.setNome(txtNome.getText());
        u.setEmail(txtEmail.getText());
        u.setSenha(new String(txtSenha.getPassword()));

        UsuarioService.save(u);

        txtStatus.setText("Conta criada com sucesso!");
    }
}