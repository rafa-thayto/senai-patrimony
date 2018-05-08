package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.services.CategoriaPatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/patrimonios/categorias")
public class CategoriaPatrimonioController {

	@Autowired
	private CategoriaPatrimonioService categoriaService;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CategoriaPatrimonio categoria, BindingResult brCategoria) {
		
		try {
			CategoriaPatrimonio categoriaCadastrada = categoriaService.cadastrar(categoria, brCategoria);
			
			return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(categoriaCadastrada);
			
		} catch (ValidacaoException e) {

			return ResponseEntity
						.unprocessableEntity()
						.body(MapHelper.mapaDe(brCategoria));
			
		} catch (Exception e) {
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
		
	}

}
