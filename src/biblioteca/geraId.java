package biblioteca;

import java.util.List;

import biblioteca.io.PersistenciaException;

public class geraId {
	private BibliotecaFacade facade;

	public geraId() {
		facade = new BibliotecaFacade();
	}
	
	public int getIdAleatorio() {
		int i = (int)(Math.random()*10000000);
		try {
			List<Emprestimo> emps = facade.listarEmprestimos();
			if(emps!=null){
				for(Emprestimo e: emps){
					if(e.getId() == i){
						i = (int)(Math.random()*10000000);
					}
				}
			}
		} catch (PersistenciaException e1) {
			
		}
		
		return i;
	}
}
