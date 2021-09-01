package br.com.limaisaias.bankingappapi.repository;

import br.com.limaisaias.bankingappapi.api.model.StatusTransacao;
import br.com.limaisaias.bankingappapi.api.model.Transacao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends PagingAndSortingRepository<Transacao, Long> {
    List<Transacao> findByStatus(@Param("status") StatusTransacao status);
    List<Transacao> findByContaRemetenteId(@Param("id") Long id);
    List<Transacao> findByContaDestinatarioId(@Param("id") Long id);
}
