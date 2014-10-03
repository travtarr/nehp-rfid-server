package com.nehp.rfid_system.server.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.util.Generics;

public class AbstractDAOImp<E> extends AbstractDAO<E> {

	private final SessionFactory sessionFactory;
	private final Class<?> entityClass;

	public AbstractDAOImp(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
		this.entityClass = Generics.getTypeParameter(getClass());
	}

	protected Session currentSession() {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (SessionException se) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	protected void closeSession() {
		sessionFactory.getCurrentSession().close();
	}

	protected Query namedQuery(String queryName) throws HibernateException {
		currentSession().beginTransaction();
		Query qry = currentSession().getNamedQuery(checkNotNull(queryName));
		currentSession().getTransaction().commit();
		closeSession();
		return qry;
	}

	@SuppressWarnings("unchecked")
	protected E get(Serializable id) {
		currentSession().beginTransaction();
		Object entity = (E) currentSession().get(entityClass, checkNotNull(id));
		currentSession().getTransaction().commit();
		closeSession();
		return (E) entity;
	}
	
	protected E persist(E entity) throws HibernateException {
		currentSession().beginTransaction();
        currentSession().saveOrUpdate(checkNotNull(entity));
        currentSession().getTransaction().commit();
        closeSession();
        return entity;
    }
	
	@SuppressWarnings("unchecked")
    protected List<E> list(Query query) throws HibernateException {
        currentSession().beginTransaction();
		List<E> list = checkNotNull(query).list();
		currentSession().getTransaction().commit();
		closeSession();
		return list;
    }
}
