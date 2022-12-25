package com.sicredi.converter;

import com.sicredi.dto.VotoDTO;
import com.sicredi.model.SessaoVotacao;
import com.sicredi.model.Voto;
import com.sicredi.model.enums.VotoEnum;

public class VotoConverter {
	
	public static Voto votoDTOToVoto(VotoDTO votoDTO, SessaoVotacao sessaoVotacao) {
		Voto voto = new Voto();
		voto.setIdAssociado(votoDTO.getIdAssociado());
		voto.setSessaoVotacao(sessaoVotacao);		
		voto.setVoto(votoDTO.getVoto() == VotoEnum.SIM ? VotoEnum.SIM : VotoEnum.NAO);
		
		return voto;
	}

}
