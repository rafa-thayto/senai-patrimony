package br.senai.sp.info.pweb.ianes.ws.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Patrimonio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 30, nullable = false, unique = false)	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private CategoriaPatrimonio categorias;
	
	@Column(nullable = false)
	private Date data_cadastro;
	
	@ManyToOne
	@JoinColumn(name= "usuario_id", nullable = false)
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CategoriaPatrimonio getCategorias() {
		return categorias;
	}

	public void setCategorias(CategoriaPatrimonio categorias) {
		this.categorias = categorias;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
