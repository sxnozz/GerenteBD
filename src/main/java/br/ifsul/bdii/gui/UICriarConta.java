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

public class UICriarConta extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JLabel txtStatus;

    public UICriarConta() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 300);
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

        txtSenha = new JTextField();
        txtSenha.setBounds(97, 93, 165, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        JButton btnCriarConta = new JButton("Criar Conta");
        btnCriarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarConta();
            }
        });
        btnCriarConta.setBounds(97, 221, 89, 23);
        contentPane.add(btnCriarConta);

        txtStatus = new JLabel("");
        txtStatus.setBounds(107, 250, 210, 14);
        contentPane.add(txtStatus);
    }

    private void criarConta() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        if (UsuarioService.save(usuario) != null) {
            txtStatus.setText("Conta criada com sucesso!");
            dispose();
            new UILogin().setVisible(true);
        } else {
            txtStatus.setText("Erro ao criar conta.");
        }
    }
}