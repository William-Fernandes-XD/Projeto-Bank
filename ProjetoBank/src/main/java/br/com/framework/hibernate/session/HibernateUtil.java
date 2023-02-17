package br.com.framework.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.framework.implementacao.crud.VariavelConexaoUtil;

/* Responsável por estabelecer conexão com o banco de dados */

@SuppressWarnings("deprecation")
@ApplicationScoped
public class HibernateUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = buildSessionFactory();
	
	/* Responsável por ler o arquivo de configuração hibernate.cfg.xml */
	private static SessionFactory buildSessionFactory() {
		
		try {
			
			if(sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			
			return sessionFactory;
		}catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao ler configurações de hibernate");
		}
	}
	
	/* Retorna nosso sessionFactory */
	public static SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}
	
	/* Retorna a sessão do sessionFactory */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public static Session openSession() {
		
		if(sessionFactory == null) {
			buildSessionFactory();
		}
		
		return sessionFactory.openSession();
	}
	
	/* Obtém a connection do provedor de conexões configurado */
	public static Connection getConnectionProvider() throws Exception {
		
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	
	// Retorna a conexão datasource do tomcat
	public static Connection getConnection() throws Exception{
		
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATASOURCE);
		
		return ds.getConnection();
	}
	
	// Retorna o dataSource
	public DataSource getDataSourceJndi() throws Exception{
		
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATASOURCE);
	}
}
