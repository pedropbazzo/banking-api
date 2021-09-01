package br.com.limaisaias.bankingappapi.repository;

import br.com.limaisaias.bankingappapi.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta,Long> {
}
