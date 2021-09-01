package br.com.limaisaias.bankingappapi.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Conta {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String agencia;

    @Column
    private String conta;

    @Column
    private String digito;

    @Column
    private Double saldo;

    @Transient
    private List<Transacao> transacoes;

}