package br.com.projeto.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Permissao {

	ADMIN("admin", "administrador"),
	USER("user", "defaultUser"),
	CADASTRO_ACESS("cadastro_acessar", "acess cadastro"),
	FINANCER_ACESS("financerAcessar", "acess financer"),
	MESSAGER_ACESS("message_acessar", "Mensagem recebida - acessar"),
   
	BAIRRO_ACESS("bairro_acessar", "Bairro - Acesso"),
	BAIRRO_NEW("bairro_new","Bairro - Novo"),
	BAIRRO_EDIT("bairro_edit", "Bairro - Editar"),
	BAIRRO_DELETE("bairro_delete", "Bairro - Deletar");
	
	private String valor = "";
	private String descricao = "";
	
	private Permissao(String valor, String descricao) {
		
		this.valor = valor;
		this.descricao = descricao;
	}
	
	private Permissao() {
		
	}
	
	// retorno da lista de enums 
	
	public static List<Permissao> getListPermissoes(){
		
		List<Permissao> permissoes = new ArrayList<>();
		
		for(Permissao permissao : Permissao.values()) {
			permissoes.add(permissao);
		}
		
		Collections.sort(permissoes, new Comparator<Permissao>() {

			@SuppressWarnings("removal")
			@Override
			public int compare(Permissao o1, Permissao o2) {
				return new Integer(o1.ordinal()).compareTo(o2.ordinal());
			}
			
		});
		
		return permissoes;
	}
	
	@Override
	public String toString() {
		
		return getValor();
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getValor() {
		return valor;
	}
}
