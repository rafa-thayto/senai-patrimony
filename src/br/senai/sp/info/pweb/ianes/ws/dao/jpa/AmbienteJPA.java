package br.senai.sp.info.pweb.ianes.ws.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.pweb.ianes.ws.dao.AmbienteDAO;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;

@Repository
@Transactional
public class AmbienteJPA implements AmbienteDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Ambiente persistir(Ambiente obj) {
		sessionFactory.getCurrentSession().persist(obj);
		return obj;
	}

	@Override
	public Ambiente deletar(Ambiente obj) {
		sessionFactory.getCurrentSession().delete(obj);
		return obj;
	}

	@Override
	public Ambiente alterar(Ambiente obj) {
		sessionFactory.getCurrentSession().update(obj);
		return obj;
	}

	@Override
	public Ambiente buscarId(Long id) {
		String hql = "FROM Ambiente a WHERE a.id = :id";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Ambiente> result = query.list();
		
		if(!result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Ambiente> buscarTodos() {
		String hql = "FROM Ambiente a";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list(); 
	}

}
