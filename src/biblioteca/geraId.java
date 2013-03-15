package biblioteca;

import biblioteca.io.Persistencia;
import biblioteca.io.PersistenciaArquivo;
import biblioteca.io.PersistenciaException;

public class GeraId {
	private Persistencia persistencia;

	public GeraId() {
		persistencia = new PersistenciaArquivo();
	}
	
	public int getIdAleatorio() throws PersistenciaException {
		int qtde = getQtdeEmprestimos();
		
		return qtde++;
	}
	
	public int getQtdeEmprestimos() throws PersistenciaException{
		return persistencia.recuperarQtdeEmprestimos();
	}
}
