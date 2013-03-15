package biblioteca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import biblioteca.io.PersistenciaArquivo;
import biblioteca.io.PersistenciaException;

public class VerificadorDeEmprestimo {
	private PersistenciaArquivo pers;
	private Calendar gc;
	private SimpleDateFormat format;
	private String dataAtual;
	
	public VerificadorDeEmprestimo(){
		pers = new PersistenciaArquivo();
		gc = Calendar.getInstance();
		format = new SimpleDateFormat("dd/MM/yyyy");
		dataAtual = format.format(gc.getTime());
	}
	public String verificar() throws PersistenciaException, ParseException{
		List<Emprestimo> emps = pers.recuperarEmprestimos();
		StringBuilder msg = new StringBuilder();
		Calendar c = Calendar.getInstance();
		Date data = (Date) format.parse(dataAtual); 
		c.setTime(data);
		Calendar cal = Calendar.getInstance();
		Date d;
		for(Emprestimo e: emps){
			d = (Date) format.parse(e.getDataEntrega());
			cal.setTime(data);
			if(data.after(d) || data.equals(d)){
				msg.append("Vencem hoje ou já venceram!\n");
				msg.append(e.toString());
			}
		}
		return msg.toString();
		
	}
}
