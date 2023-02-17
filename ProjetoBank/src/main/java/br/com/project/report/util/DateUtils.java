package br.com.project.report.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils implements Serializable{

	private static final long serialVersionUID = 1L;

	public static String getDateAtualReportName() {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(Calendar.getInstance().getTime());
	}
	
	public static String formatDateSql(Date date) {
		
		StringBuffer retorno = new StringBuffer();
		
		retorno.append("'");
		retorno.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
		retorno.append("'");
		
		return retorno.toString();
	}
}
