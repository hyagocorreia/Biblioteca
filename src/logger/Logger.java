package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static File logsDir;
	
	private static SimpleDateFormat format;

	static {
		format = new SimpleDateFormat("EEEEEEEEE, d MMM yyyy HH:mm:ss Z");
		logsDir = new File(System.getProperty("user.home")+
				System.getProperty("file.separator")+"BibData"+System.getProperty("file.separator")+"Logs"+
				System.getProperty("file.separator"));

		if (!logsDir.exists()) {
			logsDir.mkdir();
		}
	}

	private File errosFile;

	private static Logger singleton;
	
	public static Logger getInstance(){
		if(singleton == null){
			singleton = new Logger();
		}
		return singleton;
	}
	
	private Logger(){
		errosFile = new File(logsDir.getPath() + System.getProperty("file.separator")+"erros.log");	
	}
	
	public void log(String msg){
		try {
			FileWriter out = new FileWriter(errosFile, true);
			out.write(cabecalho(msg));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String msg, Throwable exception) {
		try {
			FileWriter out = new FileWriter(errosFile, true);
			out.write(cabecalho(msg));
			exception.printStackTrace(new PrintWriter(out));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void log(Throwable exception) {
		log(null,exception);
	}

	private String cabecalho(String msg){
		StringBuilder msgErro = new StringBuilder();
		msgErro.append(">>> "+format.format(new Date())+"\n");
		if(msg != null)
			msgErro.append("MSG: "+msg+"\n");
		return msgErro.toString();		
	}

}
