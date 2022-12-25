package com.sicredi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.sicredi.dto.PautaDTO;
import com.sicredi.dto.ResultadoPautaDTO;
import com.sicredi.model.Pauta;
import com.sicredi.model.SessaoVotacao;
import com.sicredi.model.Voto;
import com.sicredi.model.enums.PautaStatusEnum;
import com.sicredi.model.enums.SessaoStatusEnum;
import com.sicredi.model.enums.VotoEnum;
import com.sicredi.repository.PautaRepository;
import com.sicredi.repository.SessaoVotacaoRepository;
import com.sicredi.repository.VotoRepository;
import com.sicredi.utils.DateUtils;

@SpringBootTest
public class PautaServiceTest {
	
	@InjectMocks
	private PautaService pautaService;

	@Mock
	private PautaRepository pautaRepository;

	@Mock
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Mock
	private VotoRepository votoRepository;

	@Mock
	private DateUtils dateUtils;

	// ResultadoPautaDTO
	private static final Integer NUM_VOTOS = 1;
	private static final SessaoStatusEnum SESSAO_STATUS_FECHADA = SessaoStatusEnum.FECHADA;
	private static final PautaStatusEnum PAUTA_STATUS_APROVADA = PautaStatusEnum.APROVADA;

	// SessaoVotacao
	private static final Long ID_SESSAO_VOTACAO = 5L;
	private static final LocalDateTime DATA_INICIO_SESSAO = LocalDateTime.now();
	private static final LocalDateTime DATA_FIM_SESSAO = LocalDateTime.now();

	// Voto
	private static final Long ID_VOTO = 9L;
	private static final Long ID_ASSOCIADO = 1258L;
	private static final VotoEnum VOTO_SIM = VotoEnum.SIM;

	@Test
	public void salvarPauta() {
		PautaDTO pautaDTO = new PautaDTO("Pauta RN 1254 de 2/12/2022");
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");

		Mockito.when(pautaRepository.save(any(Pauta.class))).thenReturn((pauta));

		Pauta pautaSalva = pautaService.salvar(pautaDTO);

		assertNotNull(pautaSalva);
		assertThat(pautaSalva.getAssunto()).isSameAs(pautaDTO.getAssunto());
	}
	
	@Test
	public void resultadoPautaDeveFalharQuandoPautaInexistente() {
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");
		SessaoVotacao sessaoVotacao = new SessaoVotacao(ID_SESSAO_VOTACAO, pauta, DATA_INICIO_SESSAO, LocalDateTime.now().plusMinutes(1L));
		Voto voto = new Voto(ID_VOTO, ID_ASSOCIADO, sessaoVotacao, VOTO_SIM);

		Mockito.when(sessaoVotacaoRepository.findByPautaId(anyLong())).thenReturn(Optional.of(sessaoVotacao));
		Mockito.when(votoRepository.findAllBySessaoVotacaoId(sessaoVotacao.getId())).thenReturn(List.of(voto));

		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> pautaService.resultadoPauta(pauta.getId()));
		
		assertTrue(illegalArgumentException.getMessage().contains("Pauta [1] inexistente."));
	}
	
	@Test
	public void resultadoPautaDeveFalharQuandoSesaoInexistente() {
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");
		SessaoVotacao sessaoVotacao = new SessaoVotacao(ID_SESSAO_VOTACAO, pauta, DATA_INICIO_SESSAO, LocalDateTime.now().plusMinutes(1L));
		Voto voto = new Voto(ID_VOTO, ID_ASSOCIADO, sessaoVotacao, VOTO_SIM);

		Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
		Mockito.when(votoRepository.findAllBySessaoVotacaoId(sessaoVotacao.getId())).thenReturn(List.of(voto));

		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,() -> pautaService.resultadoPauta(pauta.getId()));
		
		assertTrue(illegalArgumentException.getMessage().contains("Sessão de votação inexistente para a pauta: 1"));
	}

	@Test
	public void resultadoPautaDeveFalharQuandoSesaoAberta() {
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");
		SessaoVotacao sessaoVotacao = new SessaoVotacao(ID_SESSAO_VOTACAO, pauta, DATA_INICIO_SESSAO, LocalDateTime.now().plusMinutes(1L));
		Voto voto = new Voto(ID_VOTO, ID_ASSOCIADO, sessaoVotacao, VOTO_SIM);

		Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
		Mockito.when(sessaoVotacaoRepository.findByPautaId(anyLong())).thenReturn(Optional.of(sessaoVotacao));
		Mockito.when(votoRepository.findAllBySessaoVotacaoId(sessaoVotacao.getId())).thenReturn(List.of(voto));

		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,() -> pautaService.resultadoPauta(pauta.getId()));
		
		assertTrue(illegalArgumentException.getMessage().contains("Sessão de votação [5] ainda aberta."));
	}
	
	@Test
	public void resultadoPauta() {
		Pauta pauta = new Pauta(1L, "Pauta RN 1254 de 2/12/2022");
		ResultadoPautaDTO resultado = new ResultadoPautaDTO(SESSAO_STATUS_FECHADA, PAUTA_STATUS_APROVADA, NUM_VOTOS);
		SessaoVotacao sessaoVotacao = new SessaoVotacao(ID_SESSAO_VOTACAO, pauta, DATA_INICIO_SESSAO, DATA_FIM_SESSAO);
		Voto voto = new Voto(ID_VOTO, ID_ASSOCIADO, sessaoVotacao, VOTO_SIM);

		Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
		Mockito.when(sessaoVotacaoRepository.findByPautaId(anyLong())).thenReturn(Optional.of(sessaoVotacao));
		Mockito.when(votoRepository.findAllBySessaoVotacaoId(sessaoVotacao.getId())).thenReturn(List.of(voto));

		ResultadoPautaDTO res = pautaService.resultadoPauta(pauta.getId());

		assertNotNull(res);
		assertEquals(res.getNumVotos(), resultado.getNumVotos());
		assertEquals(res.getPautaStatus(), resultado.getPautaStatus());
		assertEquals(res.getSessaoStatus(), resultado.getSessaoStatus());
	}

}
