package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;
import br.senai.sp.info.pweb.ianes.ws.services.AmbienteService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    /**
     * Search user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {

        try {

            Ambiente ambienteBuscado = ambienteService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ambienteBuscado);

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
     * Search all users
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> buscarTodos() {

        try {

            return ResponseEntity
                    .ok(ambienteService.buscarTodos());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

    /**
     * Persists the user
     * @param ambiente
     * @param bindingResult
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Ambiente ambiente, BindingResult bindingResult) {

        try {

            return ResponseEntity
                    .ok(ambienteService.cadastrar(ambiente, bindingResult));

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

    /**
     * Delete the ambiente
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        try {

            ambienteService.deletar(id);

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
     * Update ambiente
     * @param id
     * @param ambiente
     * @param bindingResult
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable Long id, @Valid @RequestBody Ambiente ambiente, BindingResult bindingResult) {

        try {

            return ResponseEntity
                    .ok(ambienteService.alterar(id, ambiente, bindingResult));

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
