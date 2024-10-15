package br.ifsul.bdii.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Categoria {

    private Long categoriaId;
    private String nome;

    public Categoria(String nome, Long categoriaId) {
        this.nome = nome;
        this.categoriaId = categoriaId;
    }
}