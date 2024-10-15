package br.ifsul.bdii.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ifsul.bdii.domain.Categoria;
import br.ifsul.bdii.service.CategoriaService;

public class UICadastroCategoria extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JLabel txtStatus;
    private JButton btnListar;
    private JList<Categoria> listCategorias;
    private DefaultListModel<Categoria> model = new DefaultListModel<>();

    public UICadastroCategoria() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 334, 499);
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

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarCategoria();
            }
        });

        btnSalvar.setBounds(97, 84, 89, 23);
        contentPane.add(btnSalvar);

        txtStatus = new JLabel("");
        txtStatus.setBounds(107, 118, 210, 14);
        contentPane.add(txtStatus);

        btnListar = new JButton("Listar");
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarCategorias();
            }
        });
        btnListar.setBounds(10, 165, 89, 23);
        contentPane.add(btnListar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 199, 278, 237);
        contentPane.add(scrollPane);

        listCategorias = new JList<>();
        scrollPane.setViewportView(listCategorias);

        listCategorias.setModel(model);
    }

    private void salvarCategoria() {
        Categoria c = new Categoria(txtNome.getText(), null);
        CategoriaService.save(c);

        txtStatus.setText("Categoria cadastrada!");
    }

    private void listarCategorias() {
        List<Categoria> categorias = CategoriaService.findAll();
        model.clear();
        model.addAll(categorias);
    }
}