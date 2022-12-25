package com.sicredi.dto;

import javax.validation.constraints.NotNull;

import com.sicredi.model.enums.VotoEnum;

public class VotoDTO {

	@NotNull
	private Long idAssociado;

	@NotNull
	private Long idSessaoVotacao;

	@NotNull
	private VotoEnum voto;

	public VotoDTO() {
		super();
	}

	public VotoDTO(@NotNull Long idAssociado, @NotNull Long idSessaoVotacao, @NotNull VotoEnum voto) {
		super();
		this.idAssociado = idAssociado;
		this.idSessaoVotacao = idSessaoVotacao;
		this.voto = voto;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	public Long getIdSessaoVotacao() {
		return idSessaoVotacao;
	}

	public void setIdSessaoVotacao(Long idSessaoVotacao) {
		this.idSessaoVotacao = idSessaoVotacao;
	}

	public VotoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoEnum voto) {
		this.voto = voto;
	}

}
