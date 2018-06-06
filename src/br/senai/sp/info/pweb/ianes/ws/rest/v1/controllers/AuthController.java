package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import br.senai.sp.info.pweb.ianes.ws.services.UsuarioService;
import br.senai.sp.info.pweb.ianes.ws.utils.JwtUtils;
import br.senai.sp.info.pweb.ianes.ws.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/jwt")
    public ResponseEntity<Object> gerarJWT(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {

        try {
            // 200
            Usuario usuarioBuscado = usuarioService.buscarEmailSenha(usuario, bindingResult);

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", JwtUtils.gerarToken(usuarioBuscado));
            return ResponseEntity
                    .ok(tokenMap);

        } catch (EntityNotFoundException e) {
            // 404
            return ResponseEntity
                    .notFound()
                    .header("X-Reason", "Entidade não encontrada")
                    .build();

        } catch (ValidationException e) {
            // 422
            return ResponseEntity
                    .unprocessableEntity()
                    .body(MapUtils.mapaDe(bindingResult));

        } catch (Exception e) {
            // 500
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }

    }

}
