package com.sicredi.dto;

import javax.validation.constraints.NotNull;

public class PautaDTO {

	@NotNull
	private String assunto;

	public PautaDTO() {
		super();
	}

	public PautaDTO(@NotNull String assunto) {
		super();
		this.assunto = assunto;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

}
