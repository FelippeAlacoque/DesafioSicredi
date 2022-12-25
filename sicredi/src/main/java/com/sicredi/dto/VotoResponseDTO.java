package com.sicredi.dto;

import com.sicredi.model.enums.VotoEnum;

public class VotoResponseDTO {

	private Long idAssociado;
	private VotoEnum voto;

	public VotoResponseDTO() {
		super();
	}

	public VotoResponseDTO(Long idAssociado, VotoEnum voto) {
		super();
		this.idAssociado = idAssociado;
		this.voto = voto;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	public VotoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoEnum voto) {
		this.voto = voto;
	}

}
