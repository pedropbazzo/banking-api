package br.com.limaisaias.bankingappapi.api.dto;

import br.com.limaisaias.bankingappapi.api.model.StatusTransacao;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransacaoDTO {
    private Long idContaRemetente;
    private Long idContaDestinatario;
    private Double valor;
    private LocalDate dataTransacao;
    private StatusTransacao status;
    private Integer numParcelas;
}
