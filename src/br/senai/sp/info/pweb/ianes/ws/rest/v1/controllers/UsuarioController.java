package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable(name = "id") Long id, BindingResult brUsuario) {
		try {
		    Usuario usuarioBuscado = usuarioService.buscarPorId(id, brUsuario);

		    return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioBuscado);

        } catch (ValidacaoException e) {

		    return ResponseEntity
                    .unprocessableEntity()
                    .body(MapHelper.mapaDe(brUsuario));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }

	}
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid Usuario usuario, BindingResult brUsuario) {
		
		try {
			Usuario usuarioCadastrado = usuarioService.cadastrar(usuario, brUsuario);
			
			return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(usuarioCadastrado);
			
		} catch (ValidacaoException e) {
			
			return ResponseEntity
						.unprocessableEntity()
						.body(MapHelper.mapaDe(brUsuario));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

}
