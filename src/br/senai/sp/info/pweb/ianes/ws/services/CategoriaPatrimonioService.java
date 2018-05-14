package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
     * @throws UnauthorizedException
     */
    public CategoriaPatrimonio cadastrar(CategoriaPatrimonio categoria, BindingResult brCategoria) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brCategoria.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarPorNome(categoria.getNome());
        if (categoriaBuscada != null) {
            throw new ValidationException("A categoria já existe");
        }

        categoriaDAO.persistir(categoria);
        return categoria;

    }

    /**
     * Search by ID a categoriaPatrimonio in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public CategoriaPatrimonio buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    /**
     * Search all categoriaPatrimonio in database
     * @return
     * @throws UnauthorizedException
     */
    public List<CategoriaPatrimonio> buscarTodos() throws UnauthorizedException {
        return categoriaDAO.buscarTodos();
    }

    /**
     * Delete a categoriaPatrimonio in database
     * @param id
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        categoriaDAO.deletar(categoriaBuscada);
    }

    /**
     * Update categoriaPatrimonio in database
     * @param categoria
     * @throws UnauthorizedException
     */
    public void alterar(CategoriaPatrimonio categoria) throws UnauthorizedException {
        categoriaDAO.alterar(categoria);
    }

}
