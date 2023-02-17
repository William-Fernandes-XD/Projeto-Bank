package br.com.projeto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {

	String descricaoCampo(); // descrição que aparece na tela
	String campoConsulta(); // campo do banco
	int principal() default 10000; // posição que irá aparecer
}
