package br.senai.sp.info.pweb.ianes.ws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ItemPatrimonio {
	
	@Id
	@NotNull
	@Column(unique = true)
	private Long id;

	@ManyToOne
	@NotNull
	private Patrimonio patrimonio;

	@ManyToOne
	@NotNull
	private Ambiente ambienteAtual;

	@OneToOne
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Ambiente getAmbienteAtual() {
		return ambienteAtual;
	}

	public void setAmbienteAtual(Ambiente ambienteAtual) {
		this.ambienteAtual = ambienteAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "ItemPatrimonio{" +
				"id=" + id +
				", patrimonio=" + patrimonio +
				", ambienteAtual=" + ambienteAtual +
				", usuario=" + usuario +
				'}';
	}
}
