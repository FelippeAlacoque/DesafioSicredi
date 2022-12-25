package com.sicredi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.converter.PautaConverter;
import com.sicredi.dto.PautaDTO;
import com.sicredi.dto.ResultadoPautaDTO;
import com.sicredi.dto.VotoResponseDTO;
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

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private DateUtils dateUtils;

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

	public ResultadoPautaDTO resultadoPauta(Long idPauta) {
		Optional<Pauta> pauta = pautaRepository.findById(idPauta);
		if (pauta.isEmpty()) {
			throw new IllegalArgumentException("Pauta [" + idPauta + "] inexistente.");
		} else {
			Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findByPautaId(idPauta);
			if (sessaoVotacao.isEmpty()) {
				throw new IllegalArgumentException("Sessão de votação inexistente para a pauta: " + idPauta);
			} else {
				if (verificaStatusSessaoVotacao(sessaoVotacao.get()).equals(SessaoStatusEnum.ABERTA)) {
					throw new IllegalArgumentException(
							"Sessão de votação [" + sessaoVotacao.get().getId() + "] ainda aberta. Aguarde o término ["
									+ dateUtils.formataLocalDateTime(sessaoVotacao.get().getDataFim())
									+ "] para realizar a apuração dos votos.");
				}
				return resultadoApuracaoSessaoVotacao(sessaoVotacao.get());
			}
		}
	}

	public ResultadoPautaDTO resultadoApuracaoSessaoVotacao(SessaoVotacao sessaoVotacao) {
		List<VotoResponseDTO> listaVotos = dtoToEntity(votoRepository.findAllBySessaoVotacaoId(sessaoVotacao.getId()));

		ResultadoPautaDTO result = contabilizaResultadoVotacao(listaVotos);
		result.setSessaoStatus(SessaoStatusEnum.FECHADA);
		result.setPautaStatus(result.getPautaStatus());
		result.setNumVotos(result.getNumVotos());

		return result;
	}

	public List<VotoResponseDTO> dtoToEntity(List<Voto> votos) {
		List<VotoResponseDTO> listaVotosDTO = new ArrayList<>();

		for (Voto voto : votos) {
			listaVotosDTO.add(new VotoResponseDTO(voto.getIdAssociado(), voto.getVoto()));
		}

		return listaVotosDTO;
	}

	public SessaoStatusEnum verificaStatusSessaoVotacao(SessaoVotacao sessaoVotacao) {
		LocalDateTime dataAtual = LocalDateTime.now();
		if (dataAtual.isAfter(sessaoVotacao.getDataFim()) || dataAtual.isBefore(sessaoVotacao.getDataInicio()))
			return SessaoStatusEnum.FECHADA;
		else
			return SessaoStatusEnum.ABERTA;
	}

	public ResultadoPautaDTO contabilizaResultadoVotacao(List<VotoResponseDTO> listaVotos) {
		ResultadoPautaDTO resultPautaDTO = new ResultadoPautaDTO();
		int result = 0;

		for (VotoResponseDTO voto : listaVotos)
			result += voto.getVoto().equals(VotoEnum.SIM) ? +1 : -1;

		if (listaVotos.isEmpty())
			resultPautaDTO.setPautaStatus((PautaStatusEnum.NAO_VOTADA));    
		else if (result == 0)
			resultPautaDTO.setPautaStatus(PautaStatusEnum.EMPATE);
		else if (result > 0)
			resultPautaDTO.setPautaStatus(PautaStatusEnum.APROVADA);
		else
			resultPautaDTO.setPautaStatus(PautaStatusEnum.REJEITADA);

		resultPautaDTO.setNumVotos(listaVotos.size());

		return resultPautaDTO;

	}

}
