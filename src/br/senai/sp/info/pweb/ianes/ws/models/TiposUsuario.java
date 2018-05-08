package br.senai.sp.info.pweb.ianes.ws.models;

public enum TiposUsuario {
	COMUM("Comum"), 
	ADMINISTRADOR("Administrador");
	
	String descricao;
	
	private TiposUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
