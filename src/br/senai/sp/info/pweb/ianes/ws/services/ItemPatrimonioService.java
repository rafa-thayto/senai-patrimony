package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.ItemPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.dao.UsuarioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ItemPatrimonioService {

    @Autowired
    private ItemPatrimonioDAO itemPatrimonioDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Persists a item in dabatase
     * @param item
     * @param brItem
     * @return
     * @throws ValidationException
     * @throws UnauthorizedException
     */
    public ItemPatrimonio cadastrar(ItemPatrimonio item, BindingResult brItem, Long usuarioId) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brItem.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        ItemPatrimonio patrimonioBuscado = itemPatrimonioDAO.buscarId(item.getId());
        if (patrimonioBuscado != null) {
            throw new ValidationException("O item já existe");
        }

        item.setUsuario(usuarioDAO.buscarId(usuarioId));

        itemPatrimonioDAO.persistir(item);
        return item;

    }

    /**
     * Search by ID a item in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public ItemPatrimonio buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        ItemPatrimonio categoriaBuscada = itemPatrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all item in database
     * @return
     * @throws UnauthorizedException
     */
    public List<ItemPatrimonio> buscarTodos() throws UnauthorizedException {
        return itemPatrimonioDAO.buscarTodos();
    }

    /**
     * Delete a item in database
     * @param id
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        ItemPatrimonio categoriaBuscada = itemPatrimonioDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        itemPatrimonioDAO.deletar(categoriaBuscada);
    }

    /**
     * Update item in database
     * @param item
     * @throws UnauthorizedException
     */
    public void alterar(ItemPatrimonio item) throws UnauthorizedException {
        itemPatrimonioDAO.alterar(item);
    }

}
