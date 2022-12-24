package com.sicredi.converter;

import java.time.LocalDateTime;

import com.sicredi.dto.SessaoVotacaoDTO;
import com.sicredi.model.Pauta;
import com.sicredi.model.SessaoVotacao;

public class SessaoVotacaoConverter {
	
	public static SessaoVotacao sessaoVotacaoDTOToSessaoVotacao(SessaoVotacaoDTO sessaoVotacaoDTO, Pauta pauta) {
		LocalDateTime dtInicio = LocalDateTime.now();
		LocalDateTime dtFim = null;
		
		if(sessaoVotacaoDTO.getTempoAberturaSessao() == null)
			dtFim = dtInicio. plusMinutes(sessaoVotacaoDTO.getDEFAULT_TIME());
        else
        	dtFim = dtInicio.plusMinutes(sessaoVotacaoDTO.getTempoAberturaSessao());

		return buildSessaoVotação(dtInicio, dtFim, pauta);
	}
	
	public static SessaoVotacao buildSessaoVotação(LocalDateTime dtInicio, LocalDateTime dtFim, Pauta pauta) {
		SessaoVotacao sessao = new SessaoVotacao();
		sessao.setDataInicio(dtInicio);
		sessao.setDataFim(dtFim);
		sessao.setPauta(pauta);
		
		return sessao;
	}

}
