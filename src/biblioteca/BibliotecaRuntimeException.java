package biblioteca;

import biblioteca.io.PersistenciaException;

@SuppressWarnings("serial")
public class BibliotecaRuntimeException extends RuntimeException {

	public BibliotecaRuntimeException(String message) {
		super(message);
	}

	public BibliotecaRuntimeException(PersistenciaException cause) {
		super(cause);
	}

}
