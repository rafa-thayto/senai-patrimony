package br.senai.sp.info.pweb.ianes.ws.dao;

import br.senai.sp.info.pweb.ianes.ws.models.Usuario;

public interface UsuarioDAO extends DAO<Usuario> {

	Usuario buscarPorEmail(String email);
	Usuario buscarPorEmailESenha(String email, String senha);

}
