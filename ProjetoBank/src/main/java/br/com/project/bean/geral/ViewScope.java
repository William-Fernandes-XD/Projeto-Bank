package br.com.project.bean.geral;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

public class ViewScope implements Scope{
	
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	@Override
	public Object get(String nome, ObjectFactory<?> objetoFactory) {
		
		Object instancia = getViewMap().get(nome);
		
		if(instancia == null) {
			
			instancia = objetoFactory.getObject();
			getViewMap().put(nome, instancia);
		}
		
		return instancia;
	}

	@Override
	public String getConversationId() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes requestAttributes = new FacesRequestAttributes(facesContext);
		
		return requestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void registerDestructionCallback(String nome, Runnable runnable) {
		
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		
		if(callbacks != null) {
			
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object remove(String nome) {
		
		Object instancia = getViewMap().remove(nome);
		
		if(instancia != null) {
			
			Map<String, Runnable> callback = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			
			if(callback != null) {
				
				callback.remove(nome);
			}
		}
		return instancia;
	}

	@Override
	public Object resolveContextualObject(String nome) {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes requestAttributes = new FacesRequestAttributes(facesContext);
		
		return requestAttributes.resolveReference(nome);
	}
	
	// 
	private Map<String, Object> getViewMap(){
		
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<>(); 
	}
}
