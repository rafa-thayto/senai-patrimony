package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.ItemPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class ItemPatrimonioService {

    @Autowired
    private ItemPatrimonioDAO itemPatrimonioDAO;

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private PatrimonioService patrimonioService;

    /**
     * Persists a item in dabatase
     * @param item
     * @return
     * @throws ValidationException
     */
    public ItemPatrimonio cadastrar(ItemPatrimonio item, BindingResult bindingResult) throws EntityNotFoundException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        if (bindingResult.hasFieldErrors("id")) {
            bindingResult.addError(new FieldError("item",  "id", "Id tem que ser único e não nulo"));
            throw new ValidationException();
        }


        Usuario usuarioBuscado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        item.setPatrimonio(patrimonioService.buscarPorId(item.getPatrimonio().getId()));
        item.setAmbienteAtual(ambienteService.buscarPorId(item.getPatrimonio().getId()));
        item.setUsuario(usuarioBuscado);

        itemPatrimonioDAO.persistir(item);

        return item;

    }

    /**
     * Search by ID a item in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public ItemPatrimonio buscarPorId(Long id) throws EntityNotFoundException {

        ItemPatrimonio categoriaBuscada = itemPatrimonioDAO.buscarId(id);

        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all item in database
     * @return
     */
    public List<ItemPatrimonio> buscarTodos() {
        return itemPatrimonioDAO.buscarTodos();
    }

    /**
     * Delete a item in database
     * @param id
     * @throws EntityNotFoundException
     */
    public void deletar(Long id) throws EntityNotFoundException {
        itemPatrimonioDAO.deletar(buscarPorId(id));
    }

    /**
     * Update item in database
     * @param item
     */
    public ItemPatrimonio alterar(Long id, ItemPatrimonio item, BindingResult bindingResult) throws EntityNotFoundException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        if (bindingResult.hasFieldErrors("id")) {
            bindingResult.addError(new FieldError("item",  "id", "Id tem que ser único e não nulo"));
            throw new ValidationException();
        }

        ItemPatrimonio itemBuscado = buscarPorId(id);

        itemBuscado.setPatrimonio(item.getPatrimonio());
        itemBuscado.setAmbienteAtual(item.getAmbienteAtual());

        Usuario usuarioBuscado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemBuscado.setUsuario(usuarioBuscado);

        itemPatrimonioDAO.alterar(itemBuscado);

        return itemBuscado;

    }

    public void alterarAmbiente(Long id, ItemPatrimonio itemPatrimonio) throws EntityNotFoundException {

        ItemPatrimonio itemBuscado = buscarPorId(id);
        itemBuscado.setAmbienteAtual(itemPatrimonio.getAmbienteAtual());
        System.out.println("Id: " + id);
        System.out.println("ItemBuscado: " + itemBuscado);

        itemPatrimonioDAO.alterar(itemBuscado);

    }
}
