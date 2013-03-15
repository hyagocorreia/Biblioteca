package biblioteca.io;

import java.util.List;

import biblioteca.Aluno;
import biblioteca.Emprestimo;
import biblioteca.Livro;
import biblioteca.Usuario;

public interface Persistencia {

	public void salvar(Usuario u) throws PersistenciaException;
	public void salvar(Livro l) throws PersistenciaException;
	public void salvarAluno(Aluno u) throws PersistenciaException;
	public void salvar(Emprestimo emp) throws PersistenciaException;
	
	public List<Usuario> recuperarUsuarios() throws PersistenciaException;
	public List<Livro> recuperarLivros() throws PersistenciaException;
	public List<Aluno> recuperarAlunos() throws PersistenciaException;
	public List<Emprestimo> recuperarEmprestimos() throws PersistenciaException; 
	
	public boolean deletarUsuario(Usuario user) throws PersistenciaException;
	public boolean deletarAluno(Aluno a) throws PersistenciaException;
	public boolean deletarLivro(Livro l) throws PersistenciaException;
	public boolean deletarEmprestimo(Emprestimo e) throws PersistenciaException;
	public int recuperarQtdeEmprestimos() throws PersistenciaException;
	
}
