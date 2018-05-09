package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.BadRequestException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id, BindingResult brUsuario) {
		try {
		    Usuario usuarioBuscado = usuarioService.buscarPorId(id);

		    return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioBuscado);

        } catch (EntityNotFoundException e) {

		    return ResponseEntity
                    .unprocessableEntity()
                    .body(MapHelper.mapaDe(brUsuario));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }

	}

	@GetMapping
    public ResponseEntity<Object> buscarTodos() {
	    try {

            List<Usuario> usuarios = usuarioService.buscarTodos();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarios);

        } catch (UnauthorizedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestHeader(name="x-auth-token") String token, @RequestBody @Valid Usuario usuario, BindingResult brUsuario) {
		
		try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Usuario usuarioCadastrado = usuarioService.cadastrar(usuario, brUsuario);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usuarioCadastrado);

        } catch(UnauthorizedException e) {
		    return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header("thaytotoso", "AEHUOL")
                    .build();

		} catch (ValidationException e) {
			
			return ResponseEntity
						.unprocessableEntity()
						.body(MapHelper.mapaDe(brUsuario));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

	@PostMapping("/auth")
	public ResponseEntity<Object> autenticar(@RequestBody Usuario usuario) {
		try {

		    Usuario autenticado = usuarioService.autenticar(usuario);

            String token = JWTManager.gerarToken(Autoridade.parseTipoUsuario(autenticado.getTipo()));

			return ResponseEntity
					.status(HttpStatus.OK)
                    .header("X-AUTH-TOKEN", token)
					.build();

		} catch (javax.validation.ValidationException e) {

			return ResponseEntity
					.status(203)
					.build();

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.build();

		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
	    try {
	        Usuario usuarioBuscado = usuarioService.buscarPorId(id);
            usuarioService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioBuscado);

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();

        }
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}
}
