package br.senai.sp.info.pweb.ianes.ws.dao;

import java.util.List;

public interface DAO<T> {
	
	T persistir(T obj);
	T deletar(T obj);
	T alterar(T obj);
	T buscarId(Long id);
	List<T> buscarTodos();
	
}
