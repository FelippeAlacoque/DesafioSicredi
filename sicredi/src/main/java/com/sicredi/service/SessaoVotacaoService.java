package com.sicredi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.converter.SessaoVotacaoConverter;
import com.sicredi.dto.SessaoVotacaoDTO;
import com.sicredi.model.Pauta;
import com.sicredi.model.SessaoVotacao;
import com.sicredi.repository.SessaoVotacaoRepository;

@Service
public class SessaoVotacaoService {
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private PautaService pautaService;

	public SessaoVotacao salvarSessaoVotacao(SessaoVotacaoDTO sessaoVotacaoDTO) {
		Optional<SessaoVotacao> sessao = sessaoVotacaoRepository.findByPautaId(sessaoVotacaoDTO.getIdPauta());

		if (sessao.isPresent()) {
			throw new IllegalArgumentException(
					"Sessão já existente para a pauta [" + sessaoVotacaoDTO.getIdPauta() + "]");
		} else {
			Pauta pauta = pautaService.buscarPorID(sessaoVotacaoDTO.getIdPauta());
			if (pauta == null) {
				throw new IllegalArgumentException("Pauta [" + sessaoVotacaoDTO.getIdPauta() + "] inexistente.");
			} else {
				return sessaoVotacaoRepository
						.save(SessaoVotacaoConverter.sessaoVotacaoDTOToSessaoVotacao(sessaoVotacaoDTO, pauta));
			}
		}
	}

}
