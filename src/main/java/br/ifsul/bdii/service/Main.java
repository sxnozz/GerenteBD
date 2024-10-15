package br.ifsul.bdii.service;

import br.ifsul.bdii.domain.Usuario;
import br.ifsul.bdii.gui.UICadastroCategoria;
import br.ifsul.bdii.gui.UIGerenciamentoEstoque;
import br.ifsul.bdii.gui.UILogin;
import br.ifsul.bdii.gui.UIGerenciamentoPedidos;

public class Main {

    public static void main(String[] args) {

        UILogin uilo = new UILogin();
        uilo.setVisible(true);

        uilo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                Usuario usuarioLogado = uilo.getUsuarioLogado();
                UIGerenciamentoPedidos uiped = new UIGerenciamentoPedidos(usuarioLogado);
                uiped.setVisible(true);
            }
        });
    }
}