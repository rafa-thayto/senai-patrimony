package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.ItemPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.dao.PatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.models.Patrimonio;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

@Service
public class PatrimonioService {

    @Autowired
    private PatrimonioDAO patrimonioDAO;

    @Autowired
    private ItemPatrimonioDAO itemPatrimonioDAO;

    @Autowired
    private CategoriaPatrimonioService categoriaService;

    /**
     * Persists a patrimonio in dabatase
     * @param patrimonio
     * @param brPatrimonio
     * @return
     * @throws ValidationException
     */
    public Patrimonio cadastrar(Patrimonio patrimonio, BindingResult brPatrimonio) throws ValidationException, EntityNotFoundException {

        // Trata validacoes
        if (brPatrimonio.hasErrors()) {
            throw new ValidationException();
        }

        Usuario usuarioBuscado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        patrimonio.setUsuario(usuarioBuscado);

        // Setting register date
        patrimonio.setData_cadastro(new Date(new Date().getTime()));

        // Buscando categoria para settar bunitin
        patrimonio.setCategoria(categoriaService.buscarPorId(patrimonio.getCategoria().getId()));

        patrimonioDAO.persistir(patrimonio);

        return patrimonio;

    }

    /**
     * Search by ID a patrimonio in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public Patrimonio buscarPorId(Long id) throws EntityNotFoundException {

        Patrimonio categoriaBuscada = patrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all patrimonio in database
     * @return
     */
    public List<Patrimonio> buscarTodos() {
        return patrimonioDAO.buscarTodos();
    }

    /**
     * Delete a patrimonio in database
     * @param id
     * @throws EntityNotFoundException
     */
    public void deletar(Long id) throws EntityNotFoundException {

        Patrimonio categoriaBuscada = patrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        patrimonioDAO.deletar(categoriaBuscada);
    }

    public List<ItemPatrimonio> buscarItens(Long id) throws EntityNotFoundException {

        if (patrimonioDAO.buscarId(id) == null) {
            throw new EntityNotFoundException();
        }

        return itemPatrimonioDAO.buscarItensPorIdPatrimonio(id);

    }


    /**
     * Update patrimonio in database
     * @param patrimonio
     */
    public Patrimonio alterar(Long id, Patrimonio patrimonio, BindingResult bindingResult) throws EntityNotFoundException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Patrimonio patrimonioBuscado = buscarPorId(id);
        patrimonioBuscado.setNome(patrimonio.getNome());
        patrimonioBuscado.setCategoria(patrimonio.getCategoria());

        Usuario usuarioBuscado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        patrimonioBuscado.setUsuario(usuarioBuscado);

        patrimonioDAO.alterar(patrimonioBuscado);

        return patrimonioBuscado;
    }

}
