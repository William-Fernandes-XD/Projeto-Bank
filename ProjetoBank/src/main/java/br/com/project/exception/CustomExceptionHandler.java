package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.project.util.all.EstatusPersistencia;
import br.com.project.util.all.Mensagens;

public class CustomExceptionHandler extends ExceptionHandlerWrapper{
	
	private ExceptionHandler wrapperd;
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	
	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		
		this.wrapperd = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	// manipula as exceções do JSF
	@Override
	public void handle() throws FacesException {
		
		final Iterator<ExceptionQueuedEvent> iterator =  getUnhandledExceptionQueuedEvents().iterator();
		
		while(iterator.hasNext()) {
			
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			// Recupera a exceção do contexto
			
			Throwable exception = context.getException();
			
			// Aqui trabalha a exceção
			
			try {
				
				requestMap.put("exceptionMessage", exception.getMessage());
				
				if(exception != null && exception.getMessage() != null && exception.getMessage().indexOf("ConstraintViolationException") != -1) {
					
					Mensagens.responseOperation(EstatusPersistencia.OBJETO_REFERENCIADO);
					
				}else if(exception != null && exception.getMessage() != null && exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					
					Mensagens.msgSeverityError("Esse usuário foi atualizado ou exluído e não pode ser encontrado, consulte-o novamente");
					
				}else {
					
					Mensagens.msgSeverityFatal("O sistema se recuperou de um erro inesperado");
					Mensagens.msgSeverityInfo("Você pode cotinuar usando o sitema normalmente");
					Mensagens.msgSeverityWarn("O erro foi causado por:\n" + exception.getMessage() + "");
					
					// Primefaces
					// Acontece quando a página não redireciona
					RequestContext.getCurrentInstance().execute("alert('O sistema se recuperou de um erro inesperado')");
					
				    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O sistema se recuperou de um erro inesperado"));
				    
				    // Renderiza a página e mostra as mensagens de erro
				    //navigationHandler.handleNavigation(facesContext, null, "/error/error.jsf?faces-redirect=true&expired=true");
				}
				facesContext.renderResponse();
			}finally {
				
				SessionFactory sf = HibernateUtil.getSessionFactory();
				
				if(sf.getCurrentSession().getTransaction().isActive()) {
					
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				exception.printStackTrace();
				iterator.remove();
			}
		}
		
		getWrapped().handle();
	}
}
