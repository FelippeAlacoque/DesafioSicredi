package com.sicredi.dto;

import com.sicredi.model.enums.PautaStatusEnum;
import com.sicredi.model.enums.SessaoStatusEnum;

public class ResultadoPautaDTO {

	private SessaoStatusEnum sessaoStatus;
	private PautaStatusEnum pautaStatus;
	private Integer numVotos;

	public ResultadoPautaDTO() {
		super();
	}

	public ResultadoPautaDTO(SessaoStatusEnum sessaoStatus, PautaStatusEnum pautaStatus, Integer numVotos) {
		super();
		this.sessaoStatus = sessaoStatus;
		this.pautaStatus = pautaStatus;
		this.numVotos = numVotos;
	}

	public SessaoStatusEnum getSessaoStatus() {
		return sessaoStatus;
	}

	public void setSessaoStatus(SessaoStatusEnum sessaoStatus) {
		this.sessaoStatus = sessaoStatus;
	}

	public PautaStatusEnum getPautaStatus() {
		return pautaStatus;
	}

	public void setPautaStatus(PautaStatusEnum pautaStatus) {
		this.pautaStatus = pautaStatus;
	}

	public Integer getNumVotos() {
		return numVotos;
	}

	public void setNumVotos(Integer numVotos) {
		this.numVotos = numVotos;
	}

}
