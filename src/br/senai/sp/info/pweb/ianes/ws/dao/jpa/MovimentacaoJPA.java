package br.senai.sp.info.pweb.ianes.ws.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.pweb.ianes.ws.dao.MovimentacaoDAO;
import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;

@Repository
@Transactional
public class MovimentacaoJPA implements MovimentacaoDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistir(Movimentacao obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void deletar(Movimentacao obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	@Override
	public void alterar(Movimentacao obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public Movimentacao buscarId(Long id) {
		String hql = "FROM Movimentacao m WHERE m.id = :id";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Movimentacao> result = query.list();
		
		if(!result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Movimentacao> buscarTodos() {
		String hql = "FROM Ambiente a";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list(); 
	}

}
