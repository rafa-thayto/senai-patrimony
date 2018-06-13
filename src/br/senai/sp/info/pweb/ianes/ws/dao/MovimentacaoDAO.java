package br.senai.sp.info.pweb.ianes.ws.dao;

import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;

import java.util.List;

public interface MovimentacaoDAO extends DAO<Movimentacao> {

    List<Movimentacao> buscarTodosPorItemId(Long itemId);
}
