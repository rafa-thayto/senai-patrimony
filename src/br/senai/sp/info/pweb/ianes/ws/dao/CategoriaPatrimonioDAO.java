package br.senai.sp.info.pweb.ianes.ws.dao;

import br.senai.sp.info.pweb.ianes.ws.models.CategoriaPatrimonio;

public interface CategoriaPatrimonioDAO extends DAO<CategoriaPatrimonio> {
    public CategoriaPatrimonio buscarPorNome(String nome);
}
