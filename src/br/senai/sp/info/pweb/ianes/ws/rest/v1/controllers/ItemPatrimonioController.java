package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.services.ItemPatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/v1/patrimonios/itens")
public class ItemPatrimonioController {

    @Autowired
    private ItemPatrimonioService itemPatrimonioService;

    /**
     * Search item by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {

        try {

            return ResponseEntity
                    .ok(itemPatrimonioService.buscarPorId(id));

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
     * Search all itens
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> buscarTodos() {

        try {

            return ResponseEntity
                    .ok(itemPatrimonioService.buscarTodos());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

    /**
     * Delete the item
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        try {

            itemPatrimonioService.deletar(id);

            return ResponseEntity
                    .noContent()
                    .build();

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("X-Reason", "Entidade não encontrada")
                    .build();

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }


    /**
     * Persists the item
     * @param item
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid ItemPatrimonio item) {

        try {

            return ResponseEntity
                    .ok(itemPatrimonioService.cadastrar(item));

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Alguma das entidades não foi encontrada")
                    .build();

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody @Valid ItemPatrimonio item, BindingResult bindingResult) {

        try {

            return ResponseEntity
                    .ok(itemPatrimonioService.alterar(id, item, bindingResult));

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Entidade não encontrada")
                    .build();

        } catch (ValidationException e) {

            return ResponseEntity
                    .unprocessableEntity()
                    .body(MapUtils.mapaDe(bindingResult));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
