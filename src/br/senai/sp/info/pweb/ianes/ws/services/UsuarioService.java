package br.senai.sp.info.pweb.ianes.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/**
	 * Cadastra um {@link Usuario} no banco de dados
	 * @param usuario
	 * @param brUsuario
	 * @return
	 * @throws ValidacaoException
	 * <ul>
	 * 	<li> Caso os campos tenham problemas de integridade</li>
	 * 	<li> E-mail ja esta duplicado</li>
	 * </ul>
	 */
	public Usuario cadastrar(Usuario usuario, BindingResult brUsuario) throws ValidacaoException {
		//Trata as validacoes
		if(brUsuario.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Verificando campo de e-mail duplicado
		Usuario usuarioBuscado = usuarioDAO.buscarPorEmail(usuario.getEmail());
		if(usuarioBuscado != null) {
			brUsuario.addError(new FieldError("usuario", "email", "O e-mail ja esta em uso"));
			throw new ValidacaoException();
		}
		
		//Hasheia a senha e persiste o objeto
		usuario.hashearSenha();
		usuarioDAO.persistir(usuario);
		
		return usuario;
	}
	
	public Usuario buscarPorId(Long id, BindingResult brUsuario) throws ValidacaoException {
	
		if (brUsuario.hasErrors()) {
			throw new ValidacaoException();
		}
		
		Usuario usuarioBuscado = usuarioDAO.buscarId(id);
		if (usuarioBuscado == null) {
			brUsuario.addError(new FieldError("usuario", "id", "O usuario nao esta cadastrado no sistema"));
			throw new ValidacaoException();
		}
		
		return usuarioBuscado;
	}
	
//	public Usuario autenticar(Usuario usuario, BindingResult brUsuario) throws ValidacaoException {
//		
//		if(brUsuario.hasErrors()) {
//			throw new ValidacaoException();
//		}
//		
//		Usuario usuarioAutenticado = usuarioDAO.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());
//		if (usuarioAutenticado == null) {
//			brUsuario.addError(new FieldError("usuario", "email", "E-mail e/ou senha incorretos"));
//		}
//	}

}
