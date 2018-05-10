package br.senai.sp.info.pweb.ianes.ws.jobs;

import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.models.TiposUsuario;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CriarAdministradorJob implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Usuario usuario = new Usuario();

        usuario.setNome("Administrador");
        usuario.setSobrenome("do Sistema");
        usuario.setEmail("admin@email.com");
        usuario.setSenha("admin@132");
        usuario.setTipo(TiposUsuario.ADMINISTRADOR);
        usuario.hashearSenha();

        System.out.println("Verificando se o administrador existe...");
        if (usuarioDAO.buscarPorEmail(usuario.getEmail()) == null) {
            System.out.println("Cadastrando usuario administrador em ... Ja foi!");
            usuarioDAO.persistir(usuario);
        } else {
            System.out.println("O usuario ja existe, voltemos a programacao normal");
        }
        System.out.println(usuario);

    }
}
