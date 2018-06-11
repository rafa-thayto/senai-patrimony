package br.senai.sp.info.pweb.ianes.ws.dao;

import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;

import java.util.List;

public interface ItemPatrimonioDAO extends DAO<ItemPatrimonio> {

    List<ItemPatrimonio> buscarItensPorIdPatrimonio(Long id);
    ItemPatrimonio buscarPorNome(String nome);
}
