package biblioteca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import logger.Logger;

import biblioteca.io.PersistenciaException;

public class Emprestimo {
	private String matUsuario;
	private List<String> codLivros;
	private BibliotecaFacade facade;
	private String data, dataEntrega;
	private static SimpleDateFormat format;
	private Calendar c, cEntrega;
	private int id;
	
	@SuppressWarnings("static-access")
	public Emprestimo(){
		id = new geraId().getIdAleatorio();
		codLivros = new ArrayList<String>();
		facade = new BibliotecaFacade();
		format = new SimpleDateFormat("dd/MM/yyyy");
		c = Calendar.getInstance();
		cEntrega = Calendar.getInstance();
		data = format.format(c.getTime());
		cEntrega.add(c.DAY_OF_MONTH,7);
		dataEntrega = format.format(cEntrega.getTime());
		matUsuario = new String();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public String getUsuario() {
		return matUsuario;
	}

	public void setUsuario(String matUsuario) {
		this.matUsuario = matUsuario;
	}

	public List<String> getCodLivros() {
		return codLivros;
	}

	public void setCodLivros(List<String> codLivros) {
		this.codLivros = codLivros;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void addLivro(String cod) throws EmprestimoException{
		for(String s: codLivros){
			if(s.equals(cod))
				throw new EmprestimoException("Código do Livro já adicionado no emprestimo!");
		}
		codLivros.add(cod);
	}

	public Livro removeLivro(String cod) throws EmprestimoException, PersistenciaException{
		for(String s: codLivros){
			if(s.equals(cod)){
				for(Livro l : facade.listarLivros()){
					codLivros.remove(cod);
					return l;
				}
			}
		}
		throw new EmprestimoException("Código do Livro não encontrado no emprestimo!");
	}
	
	public String toString() {
		List<Livro> livros;
		List<Usuario> usuarios;
		List<Aluno> alunos;
		StringBuilder msg = new StringBuilder();
		String nomeUsuario = "";
		
		try {
			livros = facade.listarLivros();
			usuarios = facade.listarUsuarios();
			alunos = facade.listarAlunos();
			for(Usuario u: usuarios){
				if(u.getMatricula().equals(matUsuario))
					nomeUsuario = u.getNome();
			}
			for(Aluno a: alunos){
				if(a.getMatricula().equals(matUsuario))
					nomeUsuario = a.getNome();
			}
			for(Livro l: livros){
				for(String s: codLivros){
					if(l.getCodigo().equals(s))
						msg.append("\n=>"+l.getTitulo()+" - "+l.getCodigo()+"\n");
				}
			}
		} catch (PersistenciaException e) {
			Logger.getInstance().log(e);
			msg.append("Erro ao recuperar livros. Ligue para o suporte \n");
			return msg.toString();
		}

		return "_______________________\nEMPRÉSTIMO\nID: "+id+"O usuário "+nomeUsuario+" - "+matUsuario+"\nAlugou os livros: "
				+msg.toString()+"Em "+ data + "\nDevolução em "+ dataEntrega;
	}	
}
