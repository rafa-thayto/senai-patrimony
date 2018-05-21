package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.PatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Patrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

@Service
public class PatrimonioService {

    @Autowired
    private PatrimonioDAO patrimonioDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Persists a patrimonio in dabatase
     * @param patrimonio
     * @param brPatrimonio
     * @return
     * @throws ValidationException
     * @throws UnauthorizedException
     */
    public Patrimonio cadastrar(Patrimonio patrimonio, BindingResult brPatrimonio, Long usuarioId) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brPatrimonio.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        Patrimonio patrimonioBuscado = patrimonioDAO.buscarId(patrimonio.getId());
        if (patrimonioBuscado != null) {
            throw new ValidationException("A categoria já existe");
        }

        patrimonio.setUsuario(usuarioDAO.buscarId(usuarioId));
        // Setting register date
        patrimonio.setData_cadastro(new Date(new Date().getTime()));
        patrimonioDAO.persistir(patrimonio);
        return patrimonioDAO.buscarId(patrimonio.getId());

    }

    /**
     * Search by ID a patrimonio in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public Patrimonio buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        Patrimonio categoriaBuscada = patrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all patrimonio in database
     * @return
     * @throws UnauthorizedException
     */
    public List<Patrimonio> buscarTodos() throws UnauthorizedException {
        return patrimonioDAO.buscarTodos();
    }

    /**
     * Delete a patrimonio in database
     * @param id
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        Patrimonio categoriaBuscada = patrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        patrimonioDAO.deletar(categoriaBuscada);
    }

    /**
     * Update patrimonio in database
     * @param patrimonio
     * @throws UnauthorizedException
     */
    public void alterar(Patrimonio patrimonio, Long id) throws UnauthorizedException {
        patrimonio.setUsuario(usuarioDAO.buscarId(id));
        patrimonioDAO.alterar(patrimonio);
    }

}
