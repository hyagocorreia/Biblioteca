package biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import biblioteca.io.PersistenciaException;

public class BibliotecaTests {

	private BibliotecaFacade facade = new BibliotecaFacade();
	
	@Test
	public void testCriarUsuario() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		Usuario u = facade.criarUsuario("Hyago", "8753-7774", "81011192", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		Usuario usuario = new Usuario("HYAGO", "8753-7774", "81011192", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		assertEquals(usuario.getMatricula(),u.getMatricula());
		assertEquals(usuario.getEmail(),u.getEmail());
		assertEquals(usuario.getEndereco(),u.getEndereco());
		assertEquals(usuario.getNome(),u.getNome());
		assertEquals(usuario.getTelefone(),u.getTelefone());
		assertEquals(usuario.getTipo(),u.getTipo());
		facade.deletar("81011192");
	}

	@Test
	public void testCriarAluno() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		Aluno u = facade.criarAluno("Hyago", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"ALUNO", "Rua A", "SI", "5º");
		Aluno aluno = new Aluno("HYAGO", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"ALUNO", "Rua A", "SI", "5º");
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
		facade.deletar("81011193");
	}

	@Test
	public void testCriarLivro() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		Livro l = facade.criarLivro("edicao", "titulo", "tipo", "autor", "1234");
		Livro livro = new Livro("EDICAO", "TITULO", "TIPO", "AUTOR", "1234");
		assertEquals(livro.getAutor(), l.getAutor());
		assertEquals(livro.getCodigo(), l.getCodigo());
		assertEquals(livro.getEdicao(), l.getEdicao());
		assertEquals(livro.getTipo(), l.getTipo());
		assertEquals(livro.getTitulo(), l.getTitulo());
		facade.deletar("1234");
	}
	
	@Test
	public void testListarUsuarios() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		facade.criarUsuario("Hyago", "8753-7774", "81011192", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		Usuario usuario = new Usuario("HYAGO", "8753-7774", "81011192", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		List<Usuario> users = facade.listarUsuarios();
		for(Usuario u: users){
			assertEquals(u.getMatricula(), usuario.getMatricula());
		}
		facade.deletar("81011192");
	}

	@Test
	public void testListarLivros() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		facade.criarLivro("edicao", "titulo", "tipo", "autor", "1234");
		List<Livro> livros = facade.listarLivros();
		Livro livro = new Livro("EDICAO", "TITULO", "TIPO", "AUTOR", "1234");
		for(Livro l: livros){
			assertEquals(l.getCodigo(), livro.getCodigo());
		}
		facade.deletar("1234");
	}

	@Test
	public void testListarAlunos() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		facade.criarAluno("Hyago", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"ALUNO", "Rua A", "SI", "5º");
		List<Aluno> alunos = facade.listarAlunos();
		Aluno aluno = new Aluno("HYAGO", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"ALUNO", "Rua A", "SI", "5º");
		for(Aluno u: alunos){
			assertEquals(u.getMatricula(), aluno.getMatricula());
		}
		facade.deletar("81011193");
	}
	
	@Test
	public void testDeletar() throws PersistenciaException{
		try {
			assertTrue(facade.deletar("81011193"));
		} catch (TipoNaoEcontradoException e1) {
			System.out.println("DEU CERTO!\n"+e1.getMessage());
		}
		try {
			assertTrue(facade.deletar("81011192"));
		} catch (TipoNaoEcontradoException e1) {
			System.out.println("DEU CERTO!\n"+e1.getMessage());
		}
		try {
			assertTrue(facade.deletar("1234"));
		} catch (TipoNaoEcontradoException e1) {
			System.out.println("DEU CERTO!\n"+e1.getMessage());
		}
		try {
			assertFalse(facade.deletar("81011193"));
		} catch (TipoNaoEcontradoException e) {
			System.out.println("DEU CERTO!\n"+e.getMessage());
		}
		try {
			assertFalse(facade.deletar("81011192"));
		} catch (TipoNaoEcontradoException e) {
			System.out.println("DEU CERTO!\n"+e.getMessage());
		}
		try {
			assertFalse(facade.deletar("1234"));
		} catch (TipoNaoEcontradoException e) {
			System.out.println("DEU CERTO!\n"+e.getMessage());
		}
	}

	@Test
	public void testGetAluno() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		Aluno u = facade.criarAluno("Hyago", "8753-7774", "81011193", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A", "SI", "5º");
		Aluno a = facade.getAluno("81011193");
		assertEquals(u.getMatricula(), a.getMatricula());
		try {
			assertEquals("Código não encontrado!", facade.getAluno("31232231"));
		} catch (TipoNaoEcontradoException e) {
			System.out.println("DEU CERTO!\n"+e.getMessage());
		}
		facade.deletar("81011193");
	}

	@Test
	public void testGetUsuario() throws PersistenciaException, BibliotecaException, TipoNaoEcontradoException {
		Usuario u = facade.criarUsuario("Hyago", "8753-7774", "81011192", "hyago.correia@dce.ufpb.br", 
				"PROFESSOR", "Rua A");
		Usuario a = facade.getUsuario("81011192");
		assertEquals(u.getMatricula(), a.getMatricula());
		try {
			assertEquals("Código não encontrado!", facade.getAluno("31232231"));
		} catch (TipoNaoEcontradoException e) {
			System.out.println("DEU CERTO!\n"+e.getMessage());
		}
		facade.deletar("81011192");
	}

	@Test
	public void testGetLivro() {

	}

}
