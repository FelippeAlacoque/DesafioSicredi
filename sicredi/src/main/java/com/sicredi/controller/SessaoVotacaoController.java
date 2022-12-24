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

import com.sicredi.dto.SessaoVotacaoDTO;
import com.sicredi.event.RecursoCriadoEvent;
import com.sicredi.model.SessaoVotacao;
import com.sicredi.service.SessaoVotacaoService;

@RestController
@RequestMapping("/sessaoVotacao")
public class SessaoVotacaoController {
	
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<SessaoVotacao> salvarSessao(@Valid @RequestBody SessaoVotacaoDTO sessaoDTO, HttpServletResponse response) {
		SessaoVotacao sessaoVotacao = sessaoVotacaoService.salvarSessaoVotacao(sessaoDTO);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, sessaoVotacao.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(sessaoVotacao);
	}

}
