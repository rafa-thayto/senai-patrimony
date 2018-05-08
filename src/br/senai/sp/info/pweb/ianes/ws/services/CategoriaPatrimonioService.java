package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class CategoriaPatrimonioService {

    @Autowired
    private CategoriaPatrimonioDAO categoriaDAO;

    public CategoriaPatrimonio cadastrar(CategoriaPatrimonio categoria, BindingResult brCategoria) throws ValidacaoException {

        // Trata validacoes
        if (brCategoria.hasErrors()) {
            throw new ValidacaoException();
        }

        // Verifica se a categoria já existe
        CategoriaPatrimonio categoriaBuscada = categoriaDAO.buscarPorNome(categoria.getNome());
        if (categoriaBuscada != null) {
            throw new ValidacaoException();
        }

        categoriaDAO.persistir(categoria);
        return categoria;

    }

}
