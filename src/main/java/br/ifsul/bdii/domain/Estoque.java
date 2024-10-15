package br.ifsul.bdii.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime; 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString (exclude = {"estoqueId"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Estoque {
    
    private Long estoqueId;
    private Long produtoId;
    private int quantidade;
    private BigDecimal preco; 
    private LocalDateTime dataAtualizacao;
    public void setId(Long long1) {
        this.estoqueId = long1;
    }

}
