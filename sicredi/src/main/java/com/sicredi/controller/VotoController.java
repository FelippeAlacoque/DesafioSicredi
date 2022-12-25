package com.sicredi.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.dto.VotoDTO;
import com.sicredi.event.RecursoCriadoEvent;
import com.sicredi.model.Voto;
import com.sicredi.service.VotoService;

@RestController
@RequestMapping("/voto")
public class VotoController {
	
	@Autowired
	private VotoService votoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Voto> salvar(@Valid @RequestBody VotoDTO votoDTO, HttpServletResponse response) {
		Voto votoSalvo = votoService.salvarVoto(votoDTO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, votoSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(votoSalvo);
	}

}
