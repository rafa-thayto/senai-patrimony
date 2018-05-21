package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.autenticacao.Autoridade;
import br.senai.sp.info.pweb.ianes.ws.autenticacao.JWTManager;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import br.senai.sp.info.pweb.ianes.ws.services.ItemPatrimonioService;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
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
@RequestMapping("/v1/patrimonios/itens")
public class ItemPatrimonioController {

    @Autowired
    private ItemPatrimonioService itemPatrimonioService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Search item by id
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id, @RequestHeader(name = "x-auth-token") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            ItemPatrimonio itemBuscado = itemPatrimonioService.buscarPorId(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(itemBuscado);

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
     * Search all itens
     * @param token
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> buscarTodos(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            List<ItemPatrimonio> itens = itemPatrimonioService.buscarTodos();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(itens);

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
     * Persists the item
     * @param item
     * @param brItem
     * @param token
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid ItemPatrimonio item, BindingResult brItem, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            DecodedJWT decoded = JWTManager.decodificarToken(token);
            Long usuarioId = decoded.getClaim("id").asLong();
            ItemPatrimonio itemCadastrado = itemPatrimonioService.cadastrar(item, brItem, usuarioId);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(itemCadastrado);

        } catch (UnauthorizedException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

        } catch (ValidationException e) {

            return ResponseEntity
                    .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                    .body(MapHelper.mapaDe(brItem));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    /**
     * Delete the item
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            ItemPatrimonio itemBuscado = itemPatrimonioService.buscarPorId(id);

            itemPatrimonioService.deletar(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(itemBuscado);

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
    public ResponseEntity<Object> alterar(@PathVariable Long id, @RequestBody ItemPatrimonio item, @RequestHeader(name = "X-AUTH-TOKEN") String token) {

        try {

            JWTManager.validarToken(token, Autoridade.ADMINISTRADOR);

            ItemPatrimonio itemBuscado = itemPatrimonioService.buscarPorId(id);

            DecodedJWT decoded = JWTManager.decodificarToken(token);
            Long usuarioId = decoded.getClaim("id").asLong();
            Usuario usuarioLogado = usuarioService.buscarPorId(usuarioId);

            itemBuscado.setAmbiente(item.getAmbiente());
            itemBuscado.setPatrimonio(item.getPatrimonio());
            itemBuscado.setUsuario(usuarioLogado);

            itemPatrimonioService.alterar(itemBuscado);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(itemBuscado);

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
