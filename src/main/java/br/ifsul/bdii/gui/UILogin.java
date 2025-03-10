package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.service.UsuarioService;

public class UILogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JLabel txtStatus;
    private Usuario usuarioLogado;

    public UILogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 22, 89, 14);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(97, 19, 165, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(10, 59, 89, 14);
        contentPane.add(lblSenha);

        txtSenha = new JTextField();
        txtSenha.setBounds(97, 56, 165, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnLogin.setBounds(97, 221, 89, 23);
        contentPane.add(btnLogin);

        JButton btnCriarConta = new JButton("Criar Conta");
        btnCriarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarConta();
            }
        });
        btnCriarConta.setBounds(97, 250, 89, 23);
        contentPane.add(btnCriarConta);

        txtStatus = new JLabel("");
        txtStatus.setBounds(107, 275, 210, 14);
        contentPane.add(txtStatus);
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    private void login() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        usuarioLogado = UsuarioService.findByEmailAndSenha(email, senha);

        if (usuarioLogado != null) {
            txtStatus.setText("Login bem-sucedido!");
            dispose();
            new UIMenu().setVisible(true);
        } else {
            txtStatus.setText("Email ou senha incorretos.");
        }
    }

    private void criarConta() {
        new UICriarConta().setVisible(true);
    }
}