package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class CategoriaPatrimonioService {

    @Autowired
    private CategoriaPatrimonioDAO categoriaDAO;

    /**
     * Persists a categoriaPatrimonio in dabatase
     * @param categoria
     * @param brCategoria
     * @return
     * @throws ValidationException
     */
    public CategoriaPatrimonio cadastrar(CategoriaPatrimonio categoria, BindingResult brCategoria) throws ValidationException {

        // Trata validacoes
        if (brCategoria.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        if (categoriaDAO.buscarPorNome(categoria.getNome()) != null) {
            brCategoria.addError(new FieldError("categoria", "nome", "A categoria já existe!"));
            throw new ValidationException();
        }

        categoriaDAO.persistir(categoria);
        return categoria;

    }

    /**
     * Search by ID a categoriaPatrimonio in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public CategoriaPatrimonio buscarPorId(Long id) throws EntityNotFoundException {

        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarId(id);

        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all categoriaPatrimonio in database
     * @return
     */
    public List<CategoriaPatrimonio> buscarTodos() {
        return categoriaDAO.buscarTodos();
    }

    /**
     * Delete a categoriaPatrimonio in database
     * @param id
     * @throws EntityNotFoundException
     */
    public void deletar(Long id) throws EntityNotFoundException {
        categoriaDAO.deletar(buscarPorId(id));
    }

    /**
     * Update categoriaPatrimonio in database
     * @param categoria
     */
    public CategoriaPatrimonio alterar(Long id, CategoriaPatrimonio categoria, BindingResult bindingResult) throws EntityNotFoundException, ValidationException {

        CategoriaPatrimonio categoriaBuscada = buscarPorId(id);
        categoriaBuscada.setNome(categoria.getNome());

        // Verifica se a categoria já existe
        if (categoriaDAO.buscarPorNome(categoriaBuscada.getNome()) != null) {
            bindingResult.addError(new FieldError("categoria", "nome", "Não pode colocar o mesmo NOME!"));
            throw new ValidationException();
        }

        categoriaDAO.alterar(categoriaBuscada);

        return categoriaBuscada;
    }

}
