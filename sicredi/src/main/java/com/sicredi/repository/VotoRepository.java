package com.sicredi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sicredi.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
	
	Optional<Voto> findByidAssociadoEqualsAndSessaoVotacaoIdEquals(Long idAssociado, Long SessaoVotacaoId);
	
	List<Voto> findAllBySessaoVotacaoId(Long idSessaoVotacao);

}
