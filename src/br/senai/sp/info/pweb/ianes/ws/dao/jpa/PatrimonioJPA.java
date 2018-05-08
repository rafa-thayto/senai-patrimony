package br.senai.sp.info.pweb.ianes.ws.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.pweb.ianes.ws.dao.PatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.models.Patrimonio;

@Repository
@Transactional
public class PatrimonioJPA implements PatrimonioDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void persistir(Patrimonio obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void deletar(Patrimonio obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	@Override
	public void alterar(Patrimonio obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public Patrimonio buscarId(Long id) {
		String hql = "FROM Patrimonio p WHERE p.id = :id";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<Patrimonio> result = query.list();
		
		if(!result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Patrimonio> buscarTodos() {
		String hql = "FROM Patrimonio p";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}
