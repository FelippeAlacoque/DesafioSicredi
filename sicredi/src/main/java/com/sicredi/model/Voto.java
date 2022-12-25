package com.sicredi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sicredi.model.enums.VotoEnum;

@Entity
@Table(name = "voto")
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long idAssociado;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fk_sessao_votacao")
	private SessaoVotacao sessaoVotacao;

	@NotNull
	private VotoEnum voto;

	public Voto() {
		super();
	}

	public Voto(Long id, @NotNull Long idAssociado, @NotNull SessaoVotacao sessaoVotacao, @NotNull VotoEnum voto) {
		super();
		this.id = id;
		this.idAssociado = idAssociado;
		this.sessaoVotacao = sessaoVotacao;
		this.voto = voto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	public SessaoVotacao getSessaoVotacao() {
		return sessaoVotacao;
	}

	public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
		this.sessaoVotacao = sessaoVotacao;
	}

	public VotoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoEnum voto) {
		this.voto = voto;
	}

}
