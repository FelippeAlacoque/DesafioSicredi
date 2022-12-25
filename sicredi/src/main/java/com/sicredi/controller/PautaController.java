package com.sicredi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.dto.PautaDTO;
import com.sicredi.dto.ResultadoPautaDTO;
import com.sicredi.event.RecursoCriadoEvent;
import com.sicredi.model.Pauta;
import com.sicredi.service.PautaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@ApiOperation(value = "Permite a criação de uma pauta de votação.")
	public ResponseEntity<Pauta> salvar(@Valid @RequestBody PautaDTO pautaDTO, HttpServletResponse response) {
		Pauta pautaSalva = pautaService.salvar(pautaDTO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pautaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaSalva);
	}
	
	@GetMapping
	@ApiOperation(value = "Permite listar pautas de votação.")
	public ResponseEntity<List<Pauta>> listarPautas() {
		List<Pauta> pautas = pautaService.listarPautas();
		
		return !pautas.isEmpty() ? ResponseEntity.ok(pautas) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{idPauta}")
	@ApiOperation(value = "Permite apurar o resultado da votação de uma pauta.")
	public ResponseEntity<ResultadoPautaDTO> resultadoVotacao(@PathVariable Long idPauta) {
		ResultadoPautaDTO resultado = pautaService.resultadoPauta(idPauta);
		
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
	
}
