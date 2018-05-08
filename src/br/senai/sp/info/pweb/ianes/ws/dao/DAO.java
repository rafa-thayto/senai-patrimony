package br.senai.sp.info.pweb.ianes.ws.dao;

import java.util.List;

public interface DAO<T> {
	
	public void persistir(T obj);
	public void deletar(T obj);
	public void alterar(T obj);
	public T buscarId(Long id);
	public List<T> buscarTodos();
	
}
