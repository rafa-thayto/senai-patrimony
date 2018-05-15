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
	public Movimentacao persistir(Movimentacao obj) {
		sessionFactory.getCurrentSession().persist(obj);
		return obj;
	}

	@Override
	public Movimentacao deletar(Movimentacao obj) {
		sessionFactory.getCurrentSession().delete(obj);
		return obj;
	}

	@Override
	public Movimentacao alterar(Movimentacao obj) {
		sessionFactory.getCurrentSession().update(obj);
		return obj;
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
