package br.com.limaisaias.bankingappapi.service.impl;

import br.com.limaisaias.bankingappapi.api.dto.ContaDTO;
import br.com.limaisaias.bankingappapi.api.dto.TransacaoDTO;
import br.com.limaisaias.bankingappapi.api.model.Conta;
import br.com.limaisaias.bankingappapi.api.model.Transacao;
import br.com.limaisaias.bankingappapi.api.service.ContaService;
import br.com.limaisaias.bankingappapi.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    @Override
    public Optional<Conta> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Conta conta) {
        validateConta(conta);
        this.repository.delete(conta);
    }


    public Conta update(Conta conta) {
        validateConta(conta);
        return repository.save(conta);
    }

    private void validateConta(Conta conta) {
        if (conta == null || conta.getId() == null) {
            throw new IllegalArgumentException("Conta id cant be null.");
        }
    }

    @Override
    public Page<Conta> find(Conta filter, Pageable pageRequest) {
        Example<Conta> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
        );
        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<ContaDTO> update(Long id, ContaDTO dto) {
        return getById(id).map(conta -> {
            conta.setConta(dto.getConta());
            conta.setAgencia(dto.getAgencia());
            conta.setDigito(dto.getDigito());
            conta.setSaldo(dto.getSaldo());
            if (Objects.nonNull(dto.getTransacoes()))
                for (TransacaoDTO transacaoDTO : dto.getTransacoes()) {
                    conta.getTransacoes().add(modelMapper.map(transacaoDTO, Transacao.class));
                }
            conta = update(conta);
            return modelMapper.map(conta, ContaDTO.class);
        });
    }
}
