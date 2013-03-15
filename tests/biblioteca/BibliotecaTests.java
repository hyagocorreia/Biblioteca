package biblioteca;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import biblioteca.io.PersistenciaException;

public class BibliotecaTests {

	private BibliotecaFacade facade = new BibliotecaFacade();
	Usuario usuario = new Usuario("HYAGO", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
			"PROFESSOR", "Rua A");
	Aluno aluno = new Aluno("HYAGO", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
			"PROFESSOR", "Rua A", "SI", "5º");
	Livro livro = new Livro("EDICAO", "TITULO", "TIPO", "AUTOR", "CODIGO");
	
	Emprestimo emp = new Emprestimo();
	@Test
	public void testCriarUsuario() throws PersistenciaException, BibliotecaException {
		Usuario u = facade.criarUsuario("Hyago", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		assertEquals(usuario.getMatricula(),u.getMatricula());
		assertEquals(usuario.getEmail(),u.getEmail());
		assertEquals(usuario.getEndereco(),u.getEndereco());
		assertEquals(usuario.getNome(),u.getNome());
		assertEquals(usuario.getTelefone(),u.getTelefone());
		assertEquals(usuario.getTipo(),u.getTipo());
	}

	@Test
	public void testCriarAluno() throws PersistenciaException, BibliotecaException {
		Aluno u = facade.criarAluno("Hyago", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A", "SI", "5º");
		assertEquals(aluno.getMatricula(),u.getMatricula());
		assertEquals(aluno.getCurso(),u.getCurso());
		assertEquals(aluno.getEmail(),u.getEmail());
		assertEquals(aluno.getEndereco(),u.getEndereco());
		assertEquals(aluno.getNome(),u.getNome());
		assertEquals(aluno.getPeriodo(),u.getPeriodo());
		assertEquals(aluno.getTelefone(),u.getTelefone());
		assertEquals(aluno.getTipo(),u.getTipo());
		assertEquals(aluno.getTelefone(),u.getTelefone());
		assertEquals(aluno.getTipo(),u.getTipo());
	}

	@Test
	public void testCriarLivro() throws PersistenciaException, BibliotecaException {
		Livro l = facade.criarLivro("edicao", "titulo", "tipo", "autor", "codigo");
		assertEquals(livro.getAutor(), l.getAutor());
		assertEquals(livro.getCodigo(), l.getCodigo());
		assertEquals(livro.getEdicao(), l.getEdicao());
		assertEquals(livro.getTipo(), l.getTipo());
		assertEquals(livro.getTitulo(), l.getTitulo());
	}

	@Test
	public void testCriarEmprestimo() throws PersistenciaException, EmprestimoException, TipoNaoEcontradoException {
		List<String> codigos = new ArrayList<String>();
		codigos.add("1234");
		Emprestimo e = facade.criarEmprestimo("81011193", codigos);
		emp.setUsuario("81011193");
		emp.setCodLivros(codigos);
		assertEquals(emp.getUsuario(), e.getUsuario());
		assertEquals(emp.getCodLivros(), e.getCodLivros());
	}
	
	@Test
	public void testListarUsuarios() throws PersistenciaException {
		List<Usuario> users = facade.listarUsuarios();
		for(Usuario u: users){
			assertEquals(u.getMatricula(), usuario.getMatricula());
		}
	}

	@Test
	public void testListarLivros() throws PersistenciaException {
		List<Livro> livros = facade.listarLivros();
		
		for(Livro l: livros){
			assertEquals(l.getCodigo(), livro.getCodigo());
		}
	}

	@Test
	public void testListarAlunos() throws PersistenciaException {
		List<Aluno> alunos = facade.listarAlunos();
		
		for(Aluno u: alunos){
			assertEquals(u.getMatricula(), aluno.getMatricula());
		}
	}
	
	@Test
	public void testListarEmprestimos() throws PersistenciaException {
		List<Emprestimo> emps = facade.listarEmprestimos();
		emp.setUsuario("81011193");
		for(Emprestimo e: emps){
			assertEquals(e.getUsuario(), emp.getUsuario());
		}
	}

	@Test
	public void testDeletar() {
		
	}

	@Test
	public void testTestarMatricula() {
		
	}

	@Test
	public void testTestarCodigos() {
		
	}

	@Test
	public void testGetAluno() {
	
	}

	@Test
	public void testGetUsuario() {
	
	}

	@Test
	public void testGetLivro() {

	}

	@Test
	public void testVerificar() {
	
	}

	@Test
	public void testGetTipo() {

	}
}
