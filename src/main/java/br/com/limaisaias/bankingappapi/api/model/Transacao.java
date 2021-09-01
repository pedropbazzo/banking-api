package br.com.limaisaias.bankingappapi.api.model;

import br.com.limaisaias.bankingappapi.api.dto.TransacaoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Transacao {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Conta contaRemetente;

    @ManyToOne
    @JoinColumn
    private Conta contaDestinatario;

    @Column
    private Double valor;

    @Column
    private LocalDate dataTransacao;

    @Enumerated(EnumType.ORDINAL)
    private StatusTransacao status;


    public Transacao(Long idContaRemetente, Long idContaDestinatario, Double valor, LocalDate dataTransacao) {
        this.contaRemetente = new Conta();
        this.contaDestinatario = new Conta();
        contaRemetente.setId(idContaRemetente);
        contaDestinatario.setId(idContaDestinatario);
        this.valor = valor;
        this.dataTransacao = dataTransacao;
    }


}
