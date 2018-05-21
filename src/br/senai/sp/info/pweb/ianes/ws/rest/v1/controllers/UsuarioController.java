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
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

	/**
	 * Search user by id
	 * @param id
	 * @param token
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id, @RequestHeader(name = "x-auth-token") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

		    Usuario usuarioBuscado = usuarioService.buscarPorId(id);

		    return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioBuscado);

        } catch (EntityNotFoundException e) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.build();

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

	/**
	 * Search all users
	 * @param token
	 * @return
	 */
	@GetMapping
    public ResponseEntity<Object> buscarTodos(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

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

	/**
	 * Persists the user
	 * @param usuario
	 * @param brUsuario
	 * @param token
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid Usuario usuario, BindingResult brUsuario, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
		
		try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Usuario usuarioCadastrado = usuarioService.cadastrar(usuario, brUsuario);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usuarioCadastrado);

        } catch (UnauthorizedException e) {

		    return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

		} catch (ValidationException e) {
			
			return ResponseEntity
						.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
						.body(MapHelper.mapaDe(brUsuario));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

	/**
	 * Delete the user
	 * @param id
	 * @param token
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

	        Usuario usuarioBuscado = usuarioService.buscarPorId(id);

            usuarioService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioBuscado);

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

//	/**
//	 * Update the user
//	 * @param id
//	 * @param token
//	 * @return
//	 */
//	@PutMapping("/{id}")
//	public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody Usuario usuario, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
//
//		try {
//
//
//            JWTManager.permissaoDeAcesso(token, Autoridade.COMUM)
//
//                Usuario usuarioBuscado = usuarioService.buscarPorId(id);
//                usuarioBuscado.setNome(usuario.getNome());
//                usuarioBuscado.setSobrenome(usuario.getSobrenome());
//                usuarioBuscado.setEmail(usuario.getEmail());
//                usuarioBuscado.setTipo(usuario.getTipo());
//                usuarioBuscado.setSenha(usuario.getSenha());
//
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .build();
//
//		} catch (UnauthorizedException e) {
//
//			return ResponseEntity
//					.status(HttpStatus.NON_AUTHORITATIVE_I NFORMATION)
//					.build();
//
//		} catch (Exception e) {
//
//			return ResponseEntity
//					.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.build();
//		}
//
//	}
	@PatchMapping("/alterarsenha")
	public ResponseEntity<Object> alterarSenha(@RequestBody String senhas, @RequestHeader(name = "X-AUTH-TOKEN") String token) throws UnauthorizedException {
        JWTManager.permissaoDeAcesso(token, Autoridade.COMUM);
        DecodedJWT decoded = JWTManager.decodificarToken(token);
		System.out.println(senhas);
        Long id = decoded.getClaim("id").asLong();
		return ResponseEntity.status(201).body(id);
	}

	/**
	 * Auth the user
	 * @param usuario
	 * @return
	 */
	@PostMapping("/auth")
	public ResponseEntity<Object> autenticar(@RequestBody Usuario usuario) {

		try {

			Usuario autenticado = usuarioService.autenticar(usuario);

			String token = JWTManager.gerarToken(Autoridade.parseTipoUsuario(autenticado.getTipo()), autenticado);

			return ResponseEntity
					.status(HttpStatus.OK)
					.header("X-AUTH-TOKEN", token)
					.build();

		} catch (ValidationException e) {

			return ResponseEntity
					.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
					.build();

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();

		}
	}

}
