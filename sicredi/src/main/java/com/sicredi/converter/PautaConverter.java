package com.sicredi.converter;

import com.sicredi.dto.PautaDTO;
import com.sicredi.model.Pauta;

public class PautaConverter {
	
	public static Pauta PautaDTOToPauta(PautaDTO pautaDTO) {
		Pauta pauta = new Pauta();
		pauta.setAssunto(pautaDTO.getAssunto());
		
		return pauta;
	}

}
