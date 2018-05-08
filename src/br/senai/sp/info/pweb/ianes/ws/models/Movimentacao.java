package br.senai.sp.info.pweb.ianes.ws.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Movimentacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "patrimonio_id", nullable = false)
	private Patrimonio patrimonio;

	@ManyToOne
	@JoinColumn(name = "ambiente_origem", nullable = false)
	private Ambiente origem;
	
	@ManyToOne
	@JoinColumn(name = "ambiente_destino", nullable = false)
	private Ambiente destino;
	
	@Column(nullable = false)
	@NotNull
	private Date data_movimentacao;
	
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

	public Ambiente getOrigem() {
		return origem;
	}

	public void setOrigem(Ambiente origem) {
		this.origem = origem;
	}

	public Ambiente getDestino() {
		return destino;
	}

	public void setDestino(Ambiente destino) {
		this.destino = destino;
	}

	public Date getData_movimentacao() {
		return data_movimentacao;
	}

	public void setData_movimentacao(Date data_movimentacao) {
		this.data_movimentacao = data_movimentacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

}
