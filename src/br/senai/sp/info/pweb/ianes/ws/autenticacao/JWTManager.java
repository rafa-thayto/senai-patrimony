package br.senai.sp.info.pweb.ianes.ws.autenticacao;

import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTManager {

    private static final String TOKEN_SECRET = "6f95fbfa8319159b00f7298d0a28c037ecd1283b", TOKEN_ISSUER = "Ianes Patrimonio", TOKEN_SUBJECT = "Ianes Patrimonio";

    public static String gerarToken(Autoridade autoridade, Usuario usuario) {
        try {
            Builder tokenbuilder = JWT
                    .create()
                    .withIssuer(TOKEN_ISSUER)
                    .withSubject(TOKEN_SUBJECT)
                    .withClaim("nome", usuario.getNome())
                    .withClaim("email", usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withClaim("autoridade", autoridade.toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + (60 * 60 * 1000)));

            return tokenbuilder.sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public static DecodedJWT decodificarToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Autoridade validarToken(String token, Autoridade nivelNecessario) throws UnauthorizedException {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .build()
                    .verify(token);
            Autoridade nivelUsuario = Autoridade.valueOf(jwt.getClaim("autoridade").asString().toUpperCase());
            if(nivelUsuario.getLevel() < nivelNecessario.getLevel())
                throw new UnauthorizedException("Autoridade insuficiente");
            return nivelUsuario;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean permissaoDeAcesso(String token, Autoridade nivelNecessario) throws UnauthorizedException {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .build()
                    .verify(token);
            Autoridade nivelUsuario = Autoridade.valueOf(jwt.getClaim("autoridade").asString().toUpperCase());
            if(nivelUsuario.getLevel() < nivelNecessario.getLevel())
                return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
