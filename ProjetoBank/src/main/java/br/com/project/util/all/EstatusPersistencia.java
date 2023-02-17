package br.com.project.util.all;

public enum EstatusPersistencia {

	ERRO("Erro"),
	SUCESSO("Sucesso"),
	OBJETO_REFERENCIADO("Esse objeto não pode ser apagado por conter referências");
	
	private String nome;
	
	private EstatusPersistencia(String s) {
		this.nome = s;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
