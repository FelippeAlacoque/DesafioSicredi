package com.sicredi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sicredi.model.SessaoVotacao;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
	Optional<SessaoVotacao> findByPautaId(Long pautaId);
}
