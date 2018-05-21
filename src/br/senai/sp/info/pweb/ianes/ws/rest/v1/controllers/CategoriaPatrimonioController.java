package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.services.CategoriaPatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/patrimonios/categorias")
public class CategoriaPatrimonioController {

	@Autowired
	private CategoriaPatrimonioService categoriaService;

    /**
     * Search category by id
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id, @RequestHeader(name = "x-auth-token") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            CategoriaPatrimonio categoriaBuscada = categoriaService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoriaBuscada);

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
	 * Search all categories
	 * @param token
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Object> buscarTodos(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

			List<CategoriaPatrimonio> categorias = categoriaService.buscarTodos();

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(categorias);

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
     * Persists category
     * @param categoria
     * @param brCategoria
     * @param token
     * @return
     */
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CategoriaPatrimonio categoria, BindingResult brCategoria, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
		
		try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

			CategoriaPatrimonio categoriaCadastrada = categoriaService.cadastrar(categoria, brCategoria);
			
			return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(categoriaCadastrada);
			
		} catch (ValidationException e) {

			return ResponseEntity
						.unprocessableEntity()
						.body(MapHelper.mapaDe(brCategoria));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

	/**
	 * Delete the category
	 * @param id
	 * @param token
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

			CategoriaPatrimonio categoriaBuscada = categoriaService.buscarPorId(id);

			categoriaService.deletar(id);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(categoriaBuscada);

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

	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody CategoriaPatrimonio categoria, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

		try {

			JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

			CategoriaPatrimonio categoriaBuscada = categoriaService.buscarPorId(id);

			categoriaBuscada.setNome(categoria.getNome());

			categoriaService.alterar(categoriaBuscada);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(categoriaBuscada);

		} catch (UnauthorizedException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.build();
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

}
