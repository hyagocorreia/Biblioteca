package biblioteca;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import biblioteca.io.Persistencia;
import biblioteca.io.PersistenciaArquivo;
import biblioteca.io.PersistenciaException;

public class BibliotecaFacade {
	private Persistencia persistencia;
	private VerificadorDeEmprestimo verify;
	
	public BibliotecaFacade(){
		persistencia = new PersistenciaArquivo();
		verify = new VerificadorDeEmprestimo();
	}
	
	public Usuario criarUsuario(String nome, String telefone, String matricula, String email, String tipo, String endereco)
			throws PersistenciaException,BibliotecaException{
		String upNome = nome.toUpperCase();
		Usuario u =  new Usuario(upNome, telefone, matricula, email,tipo, endereco);
		persistencia.salvar(u);
		return u;
	}
	
	public Aluno criarAluno(String nome, String telefone, String matricula,
			String email, String tipo, String endereco, String curso, String periodo) throws PersistenciaException, BibliotecaException {
		String upNome = nome.toUpperCase();
		Aluno u =  new Aluno(upNome, telefone, matricula, email,tipo, endereco, curso, periodo);
		persistencia.salvarAluno(u);
		return u;
	}
	
	public Livro criarLivro(String edicao, String titulo, String tipo, String autor, String codigo)
			throws PersistenciaException,BibliotecaException{
		String upEdicao = edicao.toUpperCase();
		String upTitulo = titulo.toUpperCase();
		String upTipo = tipo.toUpperCase();
		String upAutor = autor.toUpperCase();
		String upCodigo = codigo.toUpperCase();
		Livro l =  new Livro(upEdicao,upTitulo,upTipo,upAutor,upCodigo);
		persistencia.salvar(l);
		return l;
	}

	public List<Usuario> listarUsuarios() throws PersistenciaException{
		List<Usuario> users = persistencia.recuperarUsuarios();
		return users;
	}
	public List<Livro> listarLivros() throws PersistenciaException{
		List<Livro> livros = persistencia.recuperarLivros();
		return livros;
	}

	public List<Aluno> listarAlunos() throws PersistenciaException {
		List<Aluno> alunos = persistencia.recuperarAlunos();
		return alunos;
	}
	
	public List<Emprestimo> listarEmprestimos() throws PersistenciaException{
		List<Emprestimo> emps = persistencia.recuperarEmprestimos();
		return emps;
	}
	
	public boolean deletar(String matricula) throws PersistenciaException, TipoNaoEcontradoException{
		List<Usuario> usuarios = persistencia.recuperarUsuarios();
		for(Usuario u: usuarios){
			if(u.getMatricula().equals(matricula)){
				return persistencia.deletarUsuario(u);
			}
		}
		
		List<Aluno> alunos = persistencia.recuperarAlunos();
		for(Aluno a: alunos){
			if(a.getMatricula().equals(matricula)){
				return persistencia.deletarAluno(a);
			}
		}
		
		List<Livro> livros = persistencia.recuperarLivros();
		String upCodigo = matricula.toUpperCase();
		for(Livro l: livros){
			if(l.getCodigo().equals(upCodigo)){
				return persistencia.deletarLivro(l);
			}
		}
		throw new TipoNaoEcontradoException("Matricula ou Código não Encontrado!");
	}

	public boolean testarMatricula(String mat) throws PersistenciaException{
		List<Aluno> alunos = persistencia.recuperarAlunos();
		List<Usuario> usuarios = persistencia.recuperarUsuarios();
		for(Aluno a: alunos){
			if(a.getMatricula().equals(mat)) return true;
		}
		for(Usuario u: usuarios){
			if(u.getMatricula().equals(mat)) return true;
		}
		
		return false;
	}
	
	public List<String> testarCodigos(List<String> codigos) throws PersistenciaException{
		List<Livro> livros = persistencia.recuperarLivros();
		List<String> codValidos = new ArrayList<String>();
		for(Livro l:livros){
			for(String s: codigos){
				if(l.getCodigo().equals(s))
					codValidos.add(s);
			}		
		}
		return codValidos;
	}
	
	public Emprestimo criarEmprestimo(String matricula, List<String> codigos) 
			throws PersistenciaException, EmprestimoException, TipoNaoEcontradoException{
		Emprestimo emp =  new Emprestimo();
		List<Aluno> alunos = persistencia.recuperarAlunos();
		List<Usuario> usuarios = persistencia.recuperarUsuarios();
		for(Aluno a: alunos){
			if(a.getMatricula().equals(matricula)){
				emp.setUsuario(matricula);
			}
		}
		for(Usuario u: usuarios){
			if(u.getMatricula().equals(matricula)){
				emp.setUsuario(matricula);
			}
		}
		
		StringBuffer msg = new StringBuffer();
		int cont = 0;
		for(String s1: codigos){
			for(String s2: testarCodigos(codigos)){
				if(!s1.equals(s2)){
					cont++;
					msg.append(s1+"\n");
				}
			}
		}
		
		if(emp.getUsuario().equals(matricula) && cont==0){
			for(String cod: codigos){
				emp.addLivro(cod);
			}
			persistencia.salvar(emp);
		}else if(!emp.getUsuario().equals(matricula))
			throw new TipoNaoEcontradoException("Usuário não cadastrado no sistema!");
		else
			throw new TipoNaoEcontradoException("Código(s) inválido(s):\n"+msg.toString());
		return emp;
	}

	public Aluno getAluno(String cod) throws TipoNaoEcontradoException, PersistenciaException {
		List<Aluno> alunos = persistencia.recuperarAlunos();
		for(Aluno a: alunos){
			if(a.getMatricula().equals(cod)){
				return a;
			}
		}
		throw new TipoNaoEcontradoException("Matricula não encontrada!");
	}
	
	public Usuario getUsuario(String cod) throws PersistenciaException, TipoNaoEcontradoException{
		List<Usuario> users = persistencia.recuperarUsuarios();
		for(Usuario u: users){
			if(u.getMatricula().equals(cod)){
				return u;
			}
		}
		throw new TipoNaoEcontradoException("Matricula não encontrada!");
	}
	
	public Livro getLivro(String codi) throws PersistenciaException, TipoNaoEcontradoException{
		List<Livro> livros = persistencia.recuperarLivros();
		String cod = codi.toUpperCase();
		for(Livro l: livros){
			if(l.getCodigo().equals(cod)){
				return l;
			}
		}
		throw new TipoNaoEcontradoException("Código não encontrado!");
	}
	
	public String verificar() throws PersistenciaException, ParseException{
		return verify.verificar();
	}

	public Object getTipo(String codi) throws PersistenciaException, TipoNaoEcontradoException {
		String cod = codi.toUpperCase();
		List<Aluno> alunos = persistencia.recuperarAlunos();
		for(Aluno a: alunos){
			if(a.getMatricula().equals(cod)){
				return a;
			}
		}
		
		List<Usuario> users = persistencia.recuperarUsuarios();
		for(Usuario u: users){
			if(u.getMatricula().equals(cod)){
				return u;
			}
		}
		
		List<Livro> livros = persistencia.recuperarLivros();
		for(Livro l: livros){
			if(l.getCodigo().equals(cod)){
				return l;
			}
		}
		throw new TipoNaoEcontradoException("Matrícula ou Código não encontrado!");
	}
}
