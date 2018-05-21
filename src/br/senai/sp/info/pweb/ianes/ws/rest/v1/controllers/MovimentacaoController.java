package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;
import br.senai.sp.info.pweb.ianes.ws.services.MovimentacaoService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movientacaoService;

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

            Movimentacao movimentacaoBuscada = movientacaoService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(movimentacaoBuscada);

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

            List<Movimentacao> movimentacoes = movientacaoService.buscarTodos();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(movimentacoes);

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
     * @param movimentcao
     * @param brMovimentacao
     * @param token
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Movimentacao movimentcao, BindingResult brMovimentacao, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            Movimentacao movimentcaoCadastrada = movientacaoService.cadastrar(movimentcao, brMovimentacao);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(movimentcaoCadastrada);

        } catch (UnauthorizedException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

        } catch (ValidationException e) {

            return ResponseEntity
                    .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                    .body(MapHelper.mapaDe(brMovimentacao));

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

            Movimentacao movimentacaoBuscada = movientacaoService.buscarPorId(id);

            movientacaoService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(movimentacaoBuscada);

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

}
