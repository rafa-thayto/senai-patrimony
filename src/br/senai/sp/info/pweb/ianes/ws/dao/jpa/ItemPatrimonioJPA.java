package br.senai.sp.info.pweb.ianes.ws.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.info.pweb.ianes.ws.dao.ItemPatrimonioDAO;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;

@Repository
@Transactional
public class ItemPatrimonioJPA implements ItemPatrimonioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistir(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().persist(obj);
	}

	@Override
	public void deletar(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	@Override
	public void alterar(ItemPatrimonio obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public ItemPatrimonio buscarId(Long id) {
		String hql = "FROM ItemPatrimonio ip WHERE ip.id = :id";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		
		List<ItemPatrimonio> result = query.list();
		
		if(!result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ItemPatrimonio> buscarTodos() {
		String hql = "FROM ItemPatrimonio ip";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list(); 
	}

}
