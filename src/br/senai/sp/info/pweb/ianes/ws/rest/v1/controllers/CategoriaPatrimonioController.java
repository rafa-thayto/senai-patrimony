package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.services.CategoriaPatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
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
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {

        try {

            CategoriaPatrimonio categoriaBuscada = categoriaService.buscarPorId(id);

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

	/**
	 * Search all categories
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Object> buscarTodos() {

		try {

			List<CategoriaPatrimonio> categorias = categoriaService.buscarTodos();

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(categorias);

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
     * @return
     */
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CategoriaPatrimonio categoria, BindingResult brCategoria) {
		
		try {

			CategoriaPatrimonio categoriaCadastrada = categoriaService.cadastrar(categoria, brCategoria);
			
			return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(categoriaCadastrada);
			
		} catch (ValidationException e) {

			return ResponseEntity
						.unprocessableEntity()
						.body(MapUtils.mapaDe(brCategoria));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

	/**
	 * Delete the category
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {

		try {

            categoriaService.deletar(id);

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

//	@PutMapping("/{id}")
//	public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody CategoriaPatrimonio categoria) {
//
//		try {
//
//			CategoriaPatrimonio categoriaBuscada = categoriaService.buscarPorId(id);
//
//			categoriaBuscada.setNome(categoria.getNome());
//
//			categoriaService.alterar(categoriaBuscada);
//
//			return ResponseEntity
//					.status(HttpStatus.OK)
//					.body(categoriaBuscada);
//
//		} catch (UnauthorizedException e) {
//			return ResponseEntity
//					.status(HttpStatus.UNAUTHORIZED)
//					.build();
//		} catch (EntityNotFoundException e) {
//			return ResponseEntity
//					.status(HttpStatus.BAD_REQUEST)
//					.build();
//		} catch (Exception e) {
//			return ResponseEntity
//					.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.build();
//		}
//	}

}
