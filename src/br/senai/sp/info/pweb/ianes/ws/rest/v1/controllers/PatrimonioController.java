package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Patrimonio;
import br.senai.sp.info.pweb.ianes.ws.services.PatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/v1/patrimonios")
public class PatrimonioController {

    @Autowired
    private PatrimonioService patrimonioService;

    /**
     * Search user by id
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id, @RequestHeader(name = "x-auth-token") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.COMUM);

            Patrimonio patrimonioBuscado = patrimonioService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(patrimonioBuscado);

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

            JWTManager.validarToken(token, Autoridade.COMUM);

            List<Patrimonio> patrimonios = patrimonioService.buscarTodos();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(patrimonios);

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
     * @param patrimonio
     * @param brPatrimonio
     * @param token
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Patrimonio patrimonio, BindingResult brPatrimonio, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            DecodedJWT decoded = JWTManager.decodificarToken(token);
            Long usuarioId = decoded.getClaim("id").asLong();
            Patrimonio patrimonioCadastrado = patrimonioService.cadastrar(patrimonio, brPatrimonio, usuarioId);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(patrimonioCadastrado);

        } catch (UnauthorizedException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

        } catch (ValidationException e) {

            return ResponseEntity
                    .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                    .body(MapHelper.mapaDe(brPatrimonio));

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

            Patrimonio patrimonioBuscado = patrimonioService.buscarPorId(id);

            patrimonioService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(patrimonioBuscado);

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
    public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody Patrimonio patrimonio ,@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Patrimonio patrimonioBuscado = patrimonioService.buscarPorId(id);

            DecodedJWT decoded = JWTManager.decodificarToken(token);
            Long usuarioId = decoded.getClaim("id").asLong();
            Patrimonio usuarioLogado = patrimonioService.buscarPorId(usuarioId);

            patrimonioService.alterar(patrimonioBuscado, usuarioId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(patrimonioBuscado);

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
