package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;
import br.senai.sp.info.pweb.ianes.ws.services.AmbienteService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    /**
     * Search user by id
     * @param id
     * @param brAmbiente
     * @param token
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id, BindingResult brAmbiente, @RequestHeader(name = "x-auth-token") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Ambiente ambienteBuscado = ambienteService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ambienteBuscado);

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .unprocessableEntity()
                    .body(MapHelper.mapaDe(brAmbiente));

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

            List<Ambiente> ambientes = ambienteService.buscarTodos();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ambientes);

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
     * @param Ambiente
     * @param brAmbiente
     * @param token
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Ambiente Ambiente, BindingResult brAmbiente, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Ambiente ambienteBuscado = ambienteService.cadastrar(Ambiente, brAmbiente);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ambienteBuscado);

        } catch (UnauthorizedException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

        } catch (ValidationException e) {

            return ResponseEntity
                    .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                    .body(MapHelper.mapaDe(brAmbiente));

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

            Ambiente ambienteBuscado = ambienteService.buscarPorId(id);

            ambienteService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ambienteBuscado);

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
