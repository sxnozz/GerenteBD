package br.ifsul.bdii.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime; 


@ToString (exclude = {"pedidoId"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Pedidos {
    private Long pedidoId;
    private Usuario usuarioId;
    private LocalDateTime dataPedido;
    private String status;


}
