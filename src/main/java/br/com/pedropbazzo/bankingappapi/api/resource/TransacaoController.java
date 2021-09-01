package br.com.limaisaias.bankingappapi.api.resource;

import br.com.limaisaias.bankingappapi.api.dto.TransacaoDTO;
import br.com.limaisaias.bankingappapi.api.model.Transacao;
import br.com.limaisaias.bankingappapi.api.service.TransacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transacoes")
@Api("Conta API")
@RequiredArgsConstructor
@Slf4j
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Creates a transacao ")
    public Transacao create(@RequestBody TransacaoDTO dto) {
        return transacaoService.create(dto);
    }

    @PostMapping(value = "/agendar")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Agendar Transferencia")
    public Transacao agendar(@RequestBody TransacaoDTO transacao) {
        return transacaoService.agendar(transacao);
    }

    @PostMapping(value = "/parcelar")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("parcelar a transacao ")
    public @ResponseBody
    ResponseEntity<?> parcelar(@RequestBody TransacaoDTO transacaoDTO) {
        transacaoService.parcelar(transacaoDTO);
        return ResponseEntity.ok("FEITO");
    }


    @PutMapping(value = "/{id}/reverter")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("reverter a transacao ")
    public Transacao reverter(@PathVariable Long id) {
        Transacao transacao = transacaoService.reverter(id);
        return transacao;
    }

    @PutMapping(value = "/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("cancelar a transacao ")
    public Transacao cancelar(@PathVariable Long id) {
        Transacao transacao = transacaoService.cancelar(id);
        return transacao;
    }


}

