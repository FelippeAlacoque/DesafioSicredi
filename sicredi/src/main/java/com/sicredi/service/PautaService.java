package com.sicredi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.converter.PautaConverter;
import com.sicredi.dto.PautaDTO;
import com.sicredi.model.Pauta;
import com.sicredi.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta salvar(PautaDTO pautaDTO) {
		return pautaRepository.save(PautaConverter.PautaDTOToPauta(pautaDTO));
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}
	
	public Pauta buscarPorID(Long idPauta) {
		Optional<Pauta> pauta = pautaRepository.findById(idPauta);
		
		return pauta.get();
	}

}
