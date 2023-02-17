package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/* Responsável pelas rotinas no projeto como por exemplo salvar um objeto */

@Component
@Transactional
public interface interfaceCrud<T> extends Serializable{

	// Salva os dados
		void save(T obj) throws Exception;
		
		void persist(T obj) throws Exception;
		
		// Salva ou atualiza e retorna o objeto salvo
		T merge(T obj) throws Exception;
		
		// Realiza o update / atualização dos dados
		void update(T obj) throws Exception;
		
		// Relaiza o delete de dados
		void delete(T obj) throws Exception;

	    // Salva ou atualiza
		void saveOrUpdate(T obj) throws Exception;
		
		// Carrega a lista de dados de determinada classe
		List<T> findList(Class<T> classEntity) throws Exception;
		
		// Procura por um objeto no banco
		T findById(Class<T> classEntity, Long id) throws Exception;
		
		List<T> findListByQueryDinamic(String s) throws Exception;
		
		// Execuar update com HQL
		void executeUpdateQueryDinamic(String s) throws Exception;
		
		// Executar update com SQL
		void executeUpdateSQLDDinamic(String s) throws Exception;
		
		// Limpa a sessão do hibernate
		void clearSession() throws Exception;
		
		// Retira um objeto da sessão do hibernate
		void evict(T obj) throws Exception;
		
		Session getSession() throws Exception;
		
		List<?> getListSQLDinamic(String sql) throws Exception;
		
		List<Object[]> getListDinamicArray(String sql) throws Exception;
		
		// JDBC do spring
		JdbcTemplate getJdbcTemplate();
		
		SimpleJdbcTemplate getSimpleJdbcTemplate();
		
		SimpleJdbcInsert getSimpleJdbcInsert();
	    
		// Quantidade de registros em uma tabela
		Long totalRegistro(String table) throws Exception;
		
		Query obterQuery(String query) throws Exception;
		
		// Carregamento por demanda com JSF e Primefaces
		List<T> findListByQueryDinamic(String query, int first, int maxResults) throws Exception;
}
