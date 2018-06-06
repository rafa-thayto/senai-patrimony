package br.senai.sp.info.pweb.ianes.ws.config;

import br.senai.sp.info.pweb.ianes.ws.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String authorization = req.getHeader("Authorization");

        if (authorization != null) {

            if (authorization.matches("(Bearer )(\\w|\\.|\\-)+")) {

                String token = authorization.split(" ")[1];

                try {
                    JwtUtils.validarToken(token);


                } catch (Exception e) {
                    System.out.println("Token inválido.");
                }

                System.out.println("Token validado com sucesso.");

            } else {
                System.out.println("Token inválido.");
            }

        } else {
            System.out.println("Authorization não informado.");
        }

        chain.doFilter(request, response);

    }
}
