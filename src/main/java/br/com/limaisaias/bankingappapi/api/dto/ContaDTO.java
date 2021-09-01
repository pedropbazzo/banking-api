package br.com.limaisaias.bankingappapi.api.dto;

import br.com.limaisaias.bankingappapi.api.model.Transacao;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {

    private Long id;

    @NotEmpty
    private String agencia;
    @NotEmpty
    private String conta;
    @NotEmpty
    private String digito;

    private Double saldo;

    private Set<TransacaoDTO> transacoes;
}
