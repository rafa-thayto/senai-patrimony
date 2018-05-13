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

    public CategoriaPatrimonio cadastrar(CategoriaPatrimonio categoria, BindingResult brCategoria) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brCategoria.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarPorNome(categoria.getNome());
        if (categoriaBuscada != null) {
            throw new ValidationException();
        }

        categoriaDAO.persistir(categoria);
        return categoria;

    }

    public CategoriaPatrimonio buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        return categoriaBuscada;
    }

    public List<CategoriaPatrimonio> buscarTodos() throws UnauthorizedException {
        return categoriaDAO.buscarTodos();
    }

    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarId(id);
        if (categoriaBuscada == null) {
            throw new EntityNotFoundException();
        }

        categoriaDAO.deletar(categoriaBuscada);

    }

    //    public void alterar(CategoriaPatrimonio categoria) throws UnauthorizedException {
    // TODO: Fazer o alterar
    //    }

}
