package br.com.limaisaias.bankingappapi.api.service;

import br.com.limaisaias.bankingappapi.api.dto.TransacaoDTO;
import br.com.limaisaias.bankingappapi.api.model.Transacao;

import java.util.Optional;

public interface TransacaoService {

    Transacao save(Transacao transacao);

    Optional<Transacao> getById(Long id);

    Transacao create(TransacaoDTO dto);

    Transacao agendar(TransacaoDTO transacao);

    void parcelar(TransacaoDTO transacaoDTO);

    Transacao reverter(Long id);

    Transacao cancelar(Long id);
}
