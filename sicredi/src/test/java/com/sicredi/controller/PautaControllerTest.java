package com.sicredi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.dto.PautaDTO;
import com.sicredi.model.Pauta;
import com.sicredi.service.PautaService;

@SpringBootTest
@AutoConfigureMockMvc
public class PautaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PautaService pautaService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void criarPauta() throws Exception {
		Pauta pauta = new Pauta(1L, "Descrição pauta");				
		String pautaJson = mapper.writeValueAsString(pauta);
		
		Mockito.when(pautaService.salvar(any(PautaDTO.class))).thenReturn(pauta);
		
		String location = mockMvc.perform(post("/pauta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(pautaJson)
		)
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andReturn().getResponse().getHeader("Location");
		
		assertTrue(location.contains("/"+pauta.getId()));
		
		ArgumentCaptor<PautaDTO> captor = ArgumentCaptor.forClass(PautaDTO.class);
		Mockito.verify(pautaService).salvar(captor.capture());
		
		PautaDTO pautaCaptor = captor.getValue();
		assertNotNull(pautaCaptor);
		assertEquals(pauta.getAssunto(), pautaCaptor.getAssunto());
	}
	
	@Test
	public void listarPautaPorID() throws Exception {
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");
		String pautaJson = mapper.writeValueAsString(pauta);
		
		Mockito.when(pautaService.buscarPorID(anyLong())).thenReturn(pauta);
		
		mockMvc.perform(get("/pauta/"+pauta.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(pautaJson)
		);
		
		assertNotNull(pauta);
		assertEquals(pauta.getAssunto(), "Pauta RN 1254 de 2/12/2022");
	}

}
