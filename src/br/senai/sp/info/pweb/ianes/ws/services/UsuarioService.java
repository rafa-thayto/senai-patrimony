package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;

import java.util.List;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/**
	 * Cadastra um {@link Usuario} no banco de dados
	 * @param usuario
	 * @param brUsuario
	 * @return
	 * @throws ValidationException
	 * <ul>
	 * 	<li> Caso os campos tenham problemas de integridade</li>
	 * 	<li> E-mail ja esta duplicado</li>
	 * </ul>
	 */
	public Usuario cadastrar(Usuario usuario, BindingResult brUsuario) throws ValidationException {
		//Trata as validacoes
		if(brUsuario.hasErrors()) {
			throw new ValidationException();
		}
		
		//Verificando campo de e-mail duplicado
		Usuario usuarioBuscado = usuarioDAO.buscarPorEmail(usuario.getEmail());
		if(usuarioBuscado != null) {
			brUsuario.addError(new FieldError("usuario", "email", "O e-mail ja esta em uso"));
			throw new ValidationException();
		}
		
		//Hasheia a senha e persiste o objeto
		usuario.hashearSenha();
		usuarioDAO.persistir(usuario);
		
		return usuario;
	}
	
	public Usuario buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

		Usuario usuarioBuscado = usuarioDAO.buscarId(id);
		if (usuarioBuscado == null) {
			throw new EntityNotFoundException();
		}
		
		return usuarioBuscado;
	}

	public List<Usuario> buscarTodos() throws UnauthorizedException {

		return usuarioDAO.buscarTodos();

	}

	public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

		Usuario usuarioBuscado = usuarioDAO.buscarId(id);
		if (usuarioBuscado == null) {
			throw new EntityNotFoundException();
		}

		usuarioDAO.deletar(usuarioBuscado);
	}
	
	public Usuario autenticar(Usuario usuario) throws ValidationException {

	    usuario.hashearSenha();

		return usuarioDAO.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());

	}

}
