package br.senai.sp.info.pweb.ianes.ws.interceptors;

import br.senai.sp.info.pweb.ianes.ws.models.TiposUsuario;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AutenticacaoInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        boolean necessitaAutenticacao = request.getRequestURI().contains("/app");
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        boolean usuarioEstaAutenticado = usuarioAutenticado != null;
        boolean necessitaSerAdministrador = request.getRequestURI().contains("/adm");

        //CASO A PÁGINA PRECISE DE AUTENTICAÇÃO
        if (necessitaAutenticacao) {

            //CASO O USUÁRIO ESTEJA LOGADO
            if (usuarioEstaAutenticado) {

                //CASO O USUÁRIO PRECISE SER ADMINSTRADOR
                if (necessitaSerAdministrador && usuarioAutenticado.getTipo() != TiposUsuario.ADMINISTRADOR) {
                    response.sendError(403);
                    return false;
                }

            } else {
                response.sendError(401);
                return false;
            }
        }

        return true;
    }
}

