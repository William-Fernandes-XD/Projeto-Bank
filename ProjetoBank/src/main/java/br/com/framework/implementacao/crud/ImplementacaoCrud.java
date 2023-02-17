package br.com.framework.implementacao.crud;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.interfaceCrud;

@Component
@Transactional
public class ImplementacaoCrud<T> implements interfaceCrud<T>{

	private static final long serialVersionUID = 1L;
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;
	
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsertImpl simpleJdbcInsert;
	
	@Autowired
	private SimpleJdbcClassImpl simpleJdbcClass;
	
	private void validarTransaction() {
		
		if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}
	
	private void validarSessionFactory() {
		
		if(sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		
		validarTransaction();
	}
	
	@SuppressWarnings("unused")
	private void commitProcessoAjax() {
		
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}
	
	@SuppressWarnings("unused")
	private void rollbackProcesoAjax() {
		
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}
	
	// executa instantaneamente a operação
	private void executeFlushSession() {
		
		sessionFactory.getCurrentSession().flush();
	}
	
	@Override
	public void save(T obj) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
	}

	@Override
	public void persist(T obj) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T obj) throws Exception {
		
		validarSessionFactory();
		T retorno = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return retorno;
	}

	@Override
	public void update(T obj) throws Exception {

		validarSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();
	}

	@Override
	public void delete(T obj) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();
	}

	@Override
	public List<T> findList(Class<T> classEntity) throws Exception {
		
		validarSessionFactory();
		
		StringBuilder query = new StringBuilder();
		query.append("select distinct(entity) from ").append(classEntity.getSimpleName()).append(" entity ");
		
		@SuppressWarnings("unchecked")
		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();
		
		return lista;
	}

	@Override
	public T findById(Class<T> classEntity, Long id) throws Exception {
		
		validarSessionFactory();
		
		@SuppressWarnings("unchecked")
		T retorno = (T) sessionFactory.getCurrentSession().load(classEntity, id);
		return retorno;
	}

	@Override
	public List<T> findListByQueryDinamic(String s) throws Exception {
		
		validarSessionFactory();
		
		@SuppressWarnings("unchecked")
		List<T> lista = sessionFactory.getCurrentSession().createQuery(s).list();
		
		return lista;
	}

	@Override
	public void executeUpdateQueryDinamic(String s) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDDinamic(String s) throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {
		
		validarSessionFactory();
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void evict(T obj) throws Exception {

		validarSessionFactory();
		sessionFactory.getCurrentSession().evict(obj);
	}

	@Override
	public Session getSession() throws Exception {
		
		validarSessionFactory();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamic(String sql) throws Exception {
		
		validarSessionFactory();
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public List<Object[]> getListDinamicArray(String sql) throws Exception {
		
		validarSessionFactory();
		
		@SuppressWarnings("unchecked")
		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		
		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}
	
	public SimpleJdbcClassImpl getSimpleJdbcClass() {
		return simpleJdbcClass;
	}

	@Override
	public Long totalRegistro(String table) throws Exception {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(1) from ").append(table);
		
		return jdbcTemplate.queryForLong(stringBuilder.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		
		validarSessionFactory();
		
		Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn;
	}

	@Override
	public List<T> findListByQueryDinamic(String query, int first, int maxResults) throws Exception {

		validarSessionFactory();
		
		@SuppressWarnings("unchecked")
		List<T> lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(first).setMaxResults(maxResults).list();
		
		return lista;
	}
}
