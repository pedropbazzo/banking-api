package br.com.limaisaias.bankingappapi.api.resource;


import br.com.limaisaias.bankingappapi.api.dto.ContaDTO;
import br.com.limaisaias.bankingappapi.api.model.Conta;
import br.com.limaisaias.bankingappapi.api.service.ContaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contas")
@Api("Conta API")
@RequiredArgsConstructor
@Slf4j
public class ContaController {
    @Autowired
    private final ContaService contaService;
    //    private TransacaoService transacaoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a conta")
    public ContaDTO create(@RequestBody @Valid ContaDTO dto) {
        Conta entity = modelMapper.map(dto, Conta.class);
        entity = contaService.save(entity);
        return modelMapper.map(entity, ContaDTO.class);
    }

    @GetMapping("{id}")
    @ApiOperation("Obtains a conta details by id")
    public ContaDTO get(@PathVariable Long id) {
        log.info(" Obtaining datails for conta id: {} ", id);
        return contaService
                .getById(id)
                .map(conta -> modelMapper.map(conta, ContaDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a conta by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Conta succesfully deleted")
    })
    public void delete(@PathVariable Long id) {
        log.info(" Deleting a conta by id: {} ", id);
        Conta conta = contaService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        contaService.delete(conta);
    }

    @PutMapping("{id}")
    @ApiOperation("Updates a conta")
    public ContaDTO update(@PathVariable Long id, @RequestBody @Valid ContaDTO dto) {
        log.info(" Updating a conta by id: {} ", id);
        return contaService.update(id, dto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ApiOperation("Find contas by params")
    public Page<ContaDTO> find(ContaDTO dto, Pageable pageableRequest) {
        Conta filter = modelMapper.map(dto, Conta.class);
        Page<Conta> result = contaService.find(filter, pageableRequest);
        List<ContaDTO> list = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, ContaDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<ContaDTO>(list, pageableRequest, result.getTotalElements());
    }


}
