package br.com.limaisaias.bankingappapi.api.service;

import br.com.limaisaias.bankingappapi.api.dto.ContaDTO;
import br.com.limaisaias.bankingappapi.api.model.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContaService {

    Conta save(Conta conta);

    Optional<Conta> getById(Long id);

    void delete(Conta conta);

    Page<Conta> find(Conta filter, Pageable pageRequest);

    Optional<ContaDTO> update(Long id, ContaDTO dto);
}
