package br.ifsul.bdii.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Usuario {

    @Setter
    private Long id;
    private String nome;
    private String email;
    private String senha;
}