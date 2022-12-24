package com.sicredi.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sicredi.dto.PautaDTO;
import com.sicredi.model.Pauta;
import com.sicredi.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@PostMapping
	public ResponseEntity<Pauta> salvar(@Valid @RequestBody PautaDTO pautaDTO, HttpServletResponse response) {
		Pauta pautaSalva = pautaService.salvar(pautaDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pautaSalva.getId()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(pautaSalva);
	}
	
	@GetMapping
	public ResponseEntity<List<Pauta>> listarPautas() {
		List<Pauta> pautas = pautaService.listarPautas();
		
		return !pautas.isEmpty() ? ResponseEntity.ok(pautas) : ResponseEntity.notFound().build();
	}
}
