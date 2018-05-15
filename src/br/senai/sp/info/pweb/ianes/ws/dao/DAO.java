package br.senai.sp.info.pweb.ianes.ws.dao;

import java.util.List;

public interface DAO<T> {
	
	public T persistir(T obj);
	public T deletar(T obj);
	public T alterar(T obj);
	public T buscarId(Long id);
	public List<T> buscarTodos();
	
}
