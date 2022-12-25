package com.sicredi.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.converter.VotoConverter;
import com.sicredi.dto.VotoDTO;
import com.sicredi.model.SessaoVotacao;
import com.sicredi.model.Voto;
import com.sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.repository.VotoRepository;
import com.sicredi.utils.DateUtils;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private DateUtils utils;
	
	public Voto salvarVoto(VotoDTO votoDTO) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(votoDTO.getIdSessaoVotacao());
		if (sessaoVotacao.isEmpty()) {
			throw new IllegalArgumentException("Sessão não existe.");
		} else {
			LocalDateTime dataHoraAtual = LocalDateTime.now();
			if (validarStatusSessaoVotacao(dataHoraAtual, sessaoVotacao)) {
				throw new IllegalArgumentException("Sessão de votação já encerrada. Data/Hora de encerramento: " + utils.formataLocalDateTime(sessaoVotacao.get().getDataFim()));
			} else {
				Optional<Voto> voto = votoRepository.findByidAssociadoEqualsAndSessaoVotacaoIdEquals(
						votoDTO.getIdAssociado(), votoDTO.getIdSessaoVotacao());
				if (voto.isPresent()) {
					throw new IllegalArgumentException("Voto já registrado para o associado [cógido: " + votoDTO.getIdAssociado() +"] anteriormente na sessão.");
				} else {
					return votoRepository.save(VotoConverter.votoDTOToVoto(votoDTO, sessaoVotacao.get()));
				}
			}
		}
	}
	
	private Boolean validarStatusSessaoVotacao(LocalDateTime dataHoraAtual, Optional<SessaoVotacao> sessaoVotacao) {
		if (sessaoVotacao.get().getDataFim().isBefore(dataHoraAtual)
				|| sessaoVotacao.get().getDataInicio().isAfter(dataHoraAtual))
			return true;
		else
			return false;
	}

}
