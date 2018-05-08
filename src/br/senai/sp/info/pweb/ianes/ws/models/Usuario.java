package br.senai.sp.info.pweb.ianes.ws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.DigestUtils;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private TiposUsuario tipo = TiposUsuario.COMUM;
	
	@Column(length = 20, nullable = false, unique = false)
	@NotNull
	@Size(min = 1, max = 20)
	private String nome;
	
	@Column(length = 40, nullable = false, unique = false)
	@NotNull
	@Size(min = 1, max = 40)
	private String sobrenome;
	
	@Column(length = 120, nullable = false, unique = true)
	@NotNull
	@Email
	@Size(min = 1, max = 120)
	private String email;
	
	@Column(length = 33, nullable = false, unique = false)
	@NotNull
	@Size(min = 6, max = 33)
	private String senha;

	public void hashearSenha() {
		this.senha = DigestUtils.md5DigestAsHex(this.senha.getBytes());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TiposUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TiposUsuario tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", tipo=" + tipo + ", nome=" + nome + ", sobrenome=" + sobrenome + ", email="
				+ email + ", senha=" + senha + "]";
	}
	
	
	
}
