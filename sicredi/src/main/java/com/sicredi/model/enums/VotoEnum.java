package com.sicredi.model.enums;

public enum VotoEnum {
	
	SIM(true), NAO(false);

	boolean chave;

	private VotoEnum(boolean chave) {
		this.chave = chave;
	}

	public boolean isChave() {
		return chave;
	}

}
