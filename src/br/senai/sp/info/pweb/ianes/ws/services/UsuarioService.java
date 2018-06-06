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
	 * Persists a {@link Usuario} in the database
	 * @param usuario
	 * @param brUsuario
	 * @return
	 * @throws ValidationException
	 * <ul>
	 * 	<li> Caso os campos tenham problemas de integridade</li>
	 * 	<li> E-mail ja esta duplicado</li>
	 * </ul>
	 */
	public Usuario cadastrar(Usuario usuario, BindingResult brUsuario) throws ValidationException, UnauthorizedException {
		//Trata as validacoes
		if(brUsuario.hasErrors()) {
			throw new ValidationException();
		}
		
		//Verificando campo de e-mail duplicado
		if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
			brUsuario.addError(new FieldError("usuario", "email", "O e-mail ja esta em uso"));
			throw new ValidationException();
		}
		
		//Hasheia a senha e persiste o objeto
		usuario.hashearSenha();
		usuarioDAO.persistir(usuario);
		
		return usuario;
	}

    /**
     * Search a user by ID in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
	public Usuario buscarPorId(Long id) throws EntityNotFoundException {

		Usuario usuarioBuscado = usuarioDAO.buscarId(id);

		if (usuarioBuscado == null) {
			throw new EntityNotFoundException();
		}
		
		return usuarioBuscado;
	}

    /**
     * Search all users in database
     * @return
     */
	public List<Usuario> buscarTodos() {
		return usuarioDAO.buscarTodos();
	}

    /**
     * Delete a user in database
     * @param id
     * @throws EntityNotFoundException
     */
	public void deletar(Long id) throws EntityNotFoundException {
		usuarioDAO.deletar(buscarPorId(id));
	}

    /**
     * Update a user in database
     * @param usuario
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
	public void alterar(Usuario usuario) throws EntityNotFoundException, UnauthorizedException {

	    usuarioDAO.alterar(usuario);

	}

	public Usuario buscarEmailSenha(Usuario usuario, BindingResult bindingResult) throws ValidationException, EntityNotFoundException {

		if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("senha")) {
			throw new ValidationException();
		}

		usuario.hashearSenha();
		Usuario usuarioBuscado =
				usuarioDAO.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());

		if (usuarioBuscado == null) {
			throw new EntityNotFoundException();
		}

		return usuarioBuscado;
	}

}
