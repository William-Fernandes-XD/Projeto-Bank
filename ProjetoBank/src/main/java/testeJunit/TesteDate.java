package testeJunit;

import java.util.Calendar;

import org.junit.Test;

import br.com.project.report.util.DateUtils;

public class TesteDate {

	@Test
	public void getDate() {
		System.out.println(DateUtils.getDateAtualReportName());
	}
	
	@Test
	public void formateDateSql() {
		
		System.out.println(DateUtils.formatDateSql(Calendar.getInstance().getTime()));
	}
}
