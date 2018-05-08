package br.senai.sp.info.pweb.ianes.ws.dao;

import br.senai.sp.info.pweb.ianes.ws.models.Usuario;

public interface UsuarioDAO extends DAO<Usuario> {

	public Usuario buscarPorEmail(String email);
	public Usuario buscarPorEmailESenha(String email, String senha);

}
