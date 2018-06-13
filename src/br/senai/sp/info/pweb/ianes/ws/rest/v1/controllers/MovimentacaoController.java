package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;
import br.senai.sp.info.pweb.ianes.ws.services.MovimentacaoService;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/patrimonios/itens/{itemId}/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movientacaoService;

    /**
     * Search user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id, @PathVariable("itemId") Long itemId) {

        try {

            Movimentacao movimentacaoBuscada = movientacaoService.buscarPorId(id);

            return ResponseEntity
                    .ok(movimentacaoBuscada);

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Alguma das entidade não foi encontrada")
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
    @GetMapping("/todas")
    public ResponseEntity<Object> buscarTodos() {

        try {

            return ResponseEntity
                    .ok(movientacaoService.buscarTodos());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

    @GetMapping
    public ResponseEntity buscarTodosPorItemId(@PathVariable("itemId") Long itemId) {
        try {

            return ResponseEntity
                    .ok(movientacaoService.buscarTodosPorItemId(itemId));

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "A entidade item não foi encontrada")
                    .build();

        }  catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

    /**
     * Persists the user
     * @param movimentacao
     * @param brMovimentacao
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> movimentar(@RequestBody @Valid Movimentacao movimentacao, BindingResult brMovimentacao, @PathVariable("itemId") Long itemId) {

        try {
            return ResponseEntity
                    .ok(movientacaoService.movimentar(movimentacao, brMovimentacao, itemId));

        } catch (ValidationException e) {

            return ResponseEntity
                    .unprocessableEntity()
                    .body(MapUtils.mapaDe(brMovimentacao));

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Alguma das entidades não foi encontrada")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }

    }

}
