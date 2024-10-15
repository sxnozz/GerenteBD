package br.ifsul.bdii.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Produto {

    private Long produtosId;
    private String nome;
    private String descricao;
    private Long categoriaId;
    private int quantidade;

    public Produto(Long produtosId, String nome, String descricao, Long categoriaId) {
        this.produtosId = produtosId;
        this.nome = nome;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
    }
}