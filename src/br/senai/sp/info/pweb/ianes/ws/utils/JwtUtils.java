package br.senai.sp.info.pweb.ianes.ws.utils;

import br.senai.sp.info.pweb.ianes.ws.models.TiposUsuario;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    private static final String TOKEN_SECRET = "6f95fbfa8319159b00f7298d0a28c037ecd1283b",
            TOKEN_ISSUER = "IANES - Patrimonio",
            TOKEN_SUBJECT = "Authentication";

    /**
     * Gera o token que vai ser utilizado para persistir o usuário autenticado
     * Setta o id, nome e tipo do usuário no token
     * Token expira em
     * @param usuario
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String gerarToken(Usuario usuario) throws UnsupportedEncodingException {

        Calendar calendarExpiracao = Calendar.getInstance();
        calendarExpiracao.add(Calendar.MINUTE, 120);
        Date dataExpiracao = calendarExpiracao.getTime();

        return JWT.create()
                .withIssuer(TOKEN_ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(dataExpiracao)
                .withSubject(TOKEN_SUBJECT)
                .withClaim("id", usuario.getId())
                .withClaim("nome", usuario.getNome())
                .withClaim("tipo", usuario.getTipo().toString())
                .sign(Algorithm.HMAC512(TOKEN_SECRET));

    }

    /**
     * Decodifica o token e pega os dados contidos dentro do mesmo
     * @param token
     * @return
     */
    public static Usuario extrairUsuarioToken(String token) {

        Usuario usuario = new Usuario();

        DecodedJWT decodedJWT = JWT.decode(token);

        usuario.setId(decodedJWT.getClaim("id").asLong());
        usuario.setNome(decodedJWT.getClaim("nome").asString());
        usuario.setTipo(TiposUsuario.valueOf(decodedJWT.getClaim("tipo").asString()));

        return usuario;

    }

    /**
     * Valida o token
     * @param token
     * @throws UnsupportedEncodingException
     */
    public static void validarToken(String token) throws UnsupportedEncodingException {

        JWT.require(Algorithm.HMAC512(TOKEN_SECRET))
            .build()
            .verify(token);

    }

}
