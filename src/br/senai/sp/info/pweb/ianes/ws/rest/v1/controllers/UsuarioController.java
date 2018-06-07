package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Search user by id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {

		try {

		    return ResponseEntity
                    .ok(usuarioService.buscarPorId(id));

        } catch (EntityNotFoundException e) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();

		} catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }

	}

	/**
	 * Search all users
	 * @return
	 */
	@GetMapping
    public ResponseEntity<Object> buscarTodos() {

		try {

            return ResponseEntity
                    .ok(usuarioService.buscarTodos());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

	/**
	 * Persists the user
	 * @param usuario
	 * @param brUsuario
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid Usuario usuario, BindingResult brUsuario) {
		
		try {

            return ResponseEntity
                    .ok(usuarioService.cadastrar(usuario, brUsuario));

		} catch (ValidationException e) {
			
			return ResponseEntity
						.unprocessableEntity()
						.body(MapUtils.mapaDe(brUsuario));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

	/**
	 * Delete the user
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {

		try {

            usuarioService.deletar(id);

            return ResponseEntity
                    .noContent()
					.build();

        } catch (EntityNotFoundException e) {

			return ResponseEntity
					.notFound()
					.header("X-Reason", "Entidade não encontrada")
					.build();

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
	}

    /**
     * Update the user
     * @param usuario
     * @return
     */
	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult bindingResult) {

		try {

            return ResponseEntity
                    .ok(usuarioService.alterar(id, usuario, bindingResult));

		} catch (EntityNotFoundException e) {

		    return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Entidade não encontrada")
                    .build();

		} catch (ValidationException e) {

		    return ResponseEntity
                    .unprocessableEntity()
                    .body(MapUtils.mapaDe(bindingResult));

		}  catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

//	@PatchMapping("/alterarsenha")
//	public ResponseEntity<Object> alterarSenha(@RequestBody String senhas) throws UnauthorizedException {
//		return ResponseEntity.status(201).build();
//	}

}
