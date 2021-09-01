package br.com.limaisaias.bankingappapi.service.impl;

import br.com.limaisaias.bankingappapi.api.dto.TransacaoDTO;
import br.com.limaisaias.bankingappapi.api.exception.BusinessException;
import br.com.limaisaias.bankingappapi.api.model.StatusTransacao;
import br.com.limaisaias.bankingappapi.api.model.Transacao;
import br.com.limaisaias.bankingappapi.api.service.ContaService;
import br.com.limaisaias.bankingappapi.api.service.TransacaoService;
import br.com.limaisaias.bankingappapi.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    public static final String MSG_CONTA_NAO_EXISTE = "Conta não existe";
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaService contaService;

    @Override
    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @Override
    public Optional<Transacao> getById(Long id) {
        return transacaoRepository.findById(id);
    }

    @Override
    public Transacao create(TransacaoDTO dto) {
        Transacao transacao = new Transacao(dto.getIdContaRemetente(), dto.getIdContaDestinatario(), dto.getValor(), dto.getDataTransacao());
        populaContas(dto, transacao);
        transacao.setDataTransacao(LocalDate.now());
        transacao.setStatus(StatusTransacao.EFETIVADA);
        save(transacao);
        calculaSaldo(transacao);
        contaService.save(transacao.getContaDestinatario());
        contaService.save(transacao.getContaRemetente());
        return transacao;
    }


    @Override
    public Transacao agendar(TransacaoDTO dto) {
        Transacao transacao = new Transacao(dto.getIdContaRemetente(), dto.getIdContaDestinatario(), dto.getValor(), dto.getDataTransacao());
        populaContas(dto, transacao);
        transacao.setStatus(StatusTransacao.AGENDADA);
        return save(transacao);
    }

    @Override
    public void parcelar(TransacaoDTO transacaoDTO) {
        Double valorParcela = transacaoDTO.getValor() / transacaoDTO.getNumParcelas();
        transacaoDTO.setValor(valorParcela);
        LocalDate primeiraData = transacaoDTO.getDataTransacao();
        for (int i = 0; i < transacaoDTO.getNumParcelas(); i++) {
            transacaoDTO.setDataTransacao(primeiraData.plusMonths(i));
            agendar(transacaoDTO);
        }
    }

    @Override
    public Transacao reverter(Long id) {
        Transacao transacao = getById(id).orElseThrow(() -> new BusinessException(MSG_CONTA_NAO_EXISTE));
        if (StatusTransacao.EFETIVADA.equals(transacao.getStatus())) {
            transacao.setStatus(StatusTransacao.REVERTIDA);
            populaContas(null, transacao);
            save(transacao);
            reverteSaldo(transacao);
            contaService.save(transacao.getContaDestinatario());
            contaService.save(transacao.getContaRemetente());
        }
        return transacao;
    }

    @Override
    public Transacao cancelar(Long id) {
        Transacao transacao = getById(id).orElseThrow(() -> new BusinessException(MSG_CONTA_NAO_EXISTE));
        if (StatusTransacao.AGENDADA.equals(transacao.getStatus())) {
            populaContas(null, transacao);
            transacao.setStatus(StatusTransacao.CANCELADA);
            save(transacao);
            reverteSaldo(transacao);
            contaService.save(transacao.getContaDestinatario());
            contaService.save(transacao.getContaRemetente());
        }
        return transacao;
    }

    private void reverteSaldo(Transacao transacao) {
        transacao.getContaDestinatario().setSaldo(transacao.getContaDestinatario().getSaldo() - transacao.getValor());
        transacao.getContaRemetente().setSaldo(transacao.getContaRemetente().getSaldo() + transacao.getValor());
    }

    private void calculaSaldo(Transacao transacao) {
        transacao.getContaDestinatario().setSaldo(transacao.getContaDestinatario().getSaldo() + transacao.getValor());
        transacao.getContaRemetente().setSaldo(transacao.getContaRemetente().getSaldo() - transacao.getValor());
    }

    private void populaContas(TransacaoDTO dto, Transacao transacao) {
        if (transacao != null) {
            Long idRemetente = dto != null && dto.getIdContaRemetente() != null ? dto.getIdContaRemetente() : transacao.getContaRemetente().getId();
            Long idDestinatario = dto != null && dto.getIdContaDestinatario() != null ? dto.getIdContaDestinatario() : transacao.getContaDestinatario().getId();
            transacao.setContaDestinatario(contaService.getById(idDestinatario).orElseThrow(() -> new BusinessException(MSG_CONTA_NAO_EXISTE)));
            transacao.setContaRemetente(contaService.getById(idRemetente).orElseThrow(() -> new BusinessException(MSG_CONTA_NAO_EXISTE)));
        } else {
            throw new BusinessException("Transação está nula");
        }
    }

}

