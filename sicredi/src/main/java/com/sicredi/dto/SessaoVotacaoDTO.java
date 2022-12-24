package com.sicredi.dto;

import javax.validation.constraints.NotNull;

public class SessaoVotacaoDTO {

	@NotNull
	private Long idPauta;

	private Integer tempoAberturaSessao;

	public SessaoVotacaoDTO() {
		super();
	}

	public SessaoVotacaoDTO(@NotNull Long idPauta, Integer tempoAberturaSessao) {
		super();
		this.idPauta = idPauta;
		this.tempoAberturaSessao = tempoAberturaSessao;
	}

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Integer getTempoAberturaSessao() {
		return tempoAberturaSessao;
	}

	public void setTempoAberturaSessao(Integer tempoAberturaSessao) {
		this.tempoAberturaSessao = tempoAberturaSessao;
	}

}
