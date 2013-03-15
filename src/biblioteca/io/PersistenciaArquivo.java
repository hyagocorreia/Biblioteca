package biblioteca.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import biblioteca.Aluno;
import biblioteca.Emprestimo;
import biblioteca.Livro;
import biblioteca.Usuario;

public class PersistenciaArquivo implements Persistencia {
	
	private static File dataDir;
	static {
		URL url = PersistenciaArquivo.class.getResource("/");
		File classesDir = new File(url.getPath());
		dataDir = new File(classesDir.getParentFile().getPath() + "/data");

		if (!dataDir.exists()) {
			dataDir.mkdir();
		}
	}

	private File usuarioFile, alunoFile, livroFile, emprestimoFile;
	String camUsuarios,camAlunos,camLivros,camEmprestimos;
	public PersistenciaArquivo() {
		camUsuarios = "/usuarios.dat";
		camAlunos = "/alunos.dat";
		camLivros = "/livros.dat";
		camEmprestimos = "/emprestimos.dat";
		usuarioFile = new File(dataDir.getPath() + camUsuarios);
		alunoFile = new File(dataDir.getPath() + camAlunos);
		livroFile = new File(dataDir.getPath() + camLivros);
		emprestimoFile = new File(dataDir.getPath() + camEmprestimos);
	}

	public void salvarAluno(Aluno udat) throws PersistenciaException {
		StringBuilder line = new StringBuilder();
		line.append(udat.getNome() + ";" + udat.getTelefone() + ";" + udat.getTipo() + 
				";" + udat.getMatricula() + ";" + udat.getEmail() +  ";" + udat.getEndereco() + ";"
				+ udat.getCurso() +";" + udat.getPeriodo() + "\n");
		
		try {
			FileWriter out = new FileWriter(alunoFile, true);
			out.write(line.toString());
			out.close();
		} catch (IOException e) {
			throw new PersistenciaException(e);
		}
	}
	
	public void salvar(Usuario udat) throws PersistenciaException {
		StringBuilder line = new StringBuilder();
		line.append(udat.getNome() + ";" + udat.getTelefone() + ";" + udat.getTipo() + 
				";" + udat.getMatricula() + ";" + udat.getEmail() + ";" + udat.getEndereco() + "\n");
		
		try {
			FileWriter out = new FileWriter(usuarioFile, true);
			out.write(line.toString());
			out.close();
		} catch (IOException e) {
			throw new PersistenciaException(e);
		}
	}
	
	public void salvar(Livro livro) throws PersistenciaException {
		StringBuilder line = new StringBuilder();
		line.append(livro.getEdicao() + ";" + livro.getTitulo() + ";" + livro.getTipo() + ";" +
		livro.getAutor()+ ";" + livro.getCodigo()+"\n");
		
		try {
			FileWriter out = new FileWriter(livroFile, true);
			out.write(line.toString());
			out.close();
		} catch (IOException e) {
			throw new PersistenciaException(e);
		}
	}

	public void salvar(Emprestimo emp) throws PersistenciaException {
		StringBuilder line = new StringBuilder();
		line.append(emp.getId() + ";" + emp.getUsuario() + ";" + emp.getData()+ ";" + emp.getDataEntrega());
		for(String s: emp.getCodLivros()){
			line.append(";"+s);
		}
		line.append("\n");
		
		try {
			FileWriter out = new FileWriter(emprestimoFile, true);
			out.write(line.toString());
			out.close();
		} catch (IOException e) {
			throw new PersistenciaException(e);
		}
	}
	
	@SuppressWarnings("resource")
	public List<Aluno> recuperarAlunos() throws PersistenciaException {
		List<Aluno> usuarios = new ArrayList<Aluno>();
		if (!alunoFile.exists())
			return usuarios;

		FileInputStream in;
		int nlinha = 0;
		try {
			in = new FileInputStream(alunoFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String linha;
			while ((linha = reader.readLine()) != null) {
				nlinha++;
				String array[] = linha.split(";");
				int i;
				Aluno udat = new Aluno("","","","","","","","");
				for(i = 0; i<array.length;++i){
					if(i == 0){
						udat.setNome(array[i]);
					}else if(i==1){
						udat.setTelefone(array[i]);
					}else if(i==2){
						udat.setTipo(array[i]);
					}else if(i==3){
						udat.setMatricula(array[i]);
					}else if(i==4){
						udat.setEmail(array[i]);
					}else if(i==5){
						udat.setEndereco(array[i]);
					}else if(i==6){
						udat.setCurso(array[i]);
					}else if(i==7){
						udat.setPeriodo(array[i]);
					}else{
						throw new PersistenciaException("Erro no formato do arquivo!\n Arquivo: "+usuarioFile.toString()+"\n Linha: "+nlinha);
					}
				}

				usuarios.add(udat);
			}
			reader.close();
		} catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+usuarioFile.toString()+", na linha:"+nlinha,e);
		}
		return usuarios;
	}
	
	@SuppressWarnings("resource")
	public List<Usuario> recuperarUsuarios() throws PersistenciaException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (!usuarioFile.exists())
			return usuarios;

		FileInputStream in;
		int nlinha = 0;
		try {
			in = new FileInputStream(usuarioFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String linha;
			while ((linha = reader.readLine()) != null) {
				nlinha++;
				String array[] = linha.split(";");
				int i;
				Usuario udat = new Usuario("","","", "","","");
				for(i = 0; i<array.length;++i){
					if(i == 0){
						udat.setNome(array[i]);
					}else if(i==1){
						udat.setTelefone(array[i]);
					}else if(i==2){
						udat.setTipo(array[i]);
					}else if(i==3){
						udat.setMatricula(array[i]);
					}else if(i==4){
						udat.setEmail(array[i]);
					}else if(i==5){
						udat.setEndereco(array[i]);
					}else{
						throw new PersistenciaException("Erro no formato do arquivo!\n Arquivo: "+usuarioFile.toString()+"\n Linha: "+nlinha);
					}
				}

				usuarios.add(udat);
			}
			reader.close();
		} catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+usuarioFile.toString()+", na linha:"+nlinha,e);
		}
		return usuarios;
	}

	@SuppressWarnings({"resource" })
	public List<Livro> recuperarLivros() throws PersistenciaException {
		List<Livro> livros = new ArrayList<Livro>();
		if (!livroFile.exists())
			return livros;

		FileInputStream in;
		int nlinha = 0;
		try {
			in = new FileInputStream(livroFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String linha;
			while ((linha = reader.readLine()) != null) {
				nlinha++;
				String array[] = linha.split(";");
				int i;
				Livro livro = new Livro("", "", "", "","");
				for(i = 0; i<array.length;++i){
					if(i == 0){
						livro.setEdicao(array[i]);
					}else if(i==1){
						livro.setTitulo(array[i]);
					}else if(i==2){
						livro.setTipo(array[i]);
					}else if(i==3){
						livro.setAutor(array[i]);
					}else if(i==4){
						livro.setCodigo(array[i]);
					}else{
						throw new PersistenciaException("Erro no formato do arquivo!\n Arquivo: "+livroFile.toString()+"\n Linha: "+nlinha);
					}
				}

				livros.add(livro);
			}
			reader.close();
		} catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+usuarioFile.toString()+", na linha:"+nlinha,e);
		}
		return livros;
	}

	@SuppressWarnings("resource")
	public List<Emprestimo> recuperarEmprestimos() throws PersistenciaException {
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		if (!emprestimoFile.exists())
			return emprestimos;

		FileInputStream in;
		int nlinha = 0;
		try {
			in = new FileInputStream(emprestimoFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String linha;
			while ((linha = reader.readLine()) != null) {
				nlinha++;
				String array[] = linha.split(";");
				int i;
				Emprestimo emp = new Emprestimo();
				for(i = 0; i<array.length;++i){
					if(i==0)
						emp.setId(Integer.parseInt(array[i]));
					else if(i == 1)
						emp.setUsuario(array[i]);
					else if(i==2)
						emp.setData(array[i]);
					else if(i==3)
						emp.setDataEntrega(array[i]);
					else if(i>3){
						for(String s: array){
							emp.addLivro(s);
						}
					}else
						throw new PersistenciaException("Erro no formato do arquivo!\n Arquivo: "+emprestimoFile.toString()+"\n Linha: "+nlinha);
				}

				emprestimos.add(emp);
			}
			reader.close();
		} catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+emprestimoFile.toString()+", na linha:"+nlinha,e);
		}
		return emprestimos;
	}
	
	public boolean deletarUsuario(Usuario udat) throws PersistenciaException{
		List<Usuario> usuarios = recuperarUsuarios();
		int nLinha = 0;
		try {
			FileWriter out;
			File file = usuarioFile;  
			file.delete();
			usuarioFile = new File(dataDir.getPath() + camUsuarios);
			for(Usuario u: usuarios){
				if(!u.getMatricula().equals(udat.getMatricula())){
					StringBuilder line = new StringBuilder();
					line.append(u.getNome() + ";" + u.getTelefone() + ";" + u.getTipo() + 
							";" + u.getMatricula() + ";" + u.getEmail() + "\n");
					
					out = new FileWriter(usuarioFile, true);
					out.write(line.toString());
					out.close();
				}else{
					nLinha++;
				}
			}
			return nLinha > 0;
		}catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+usuarioFile.toString()+", na linha:"+nLinha,e);
		}
	}
	
	public boolean deletarAluno(Aluno aluno) throws PersistenciaException{
		List<Aluno> alunos = recuperarAlunos();
		int nLinha = 0;
		try {
			FileWriter out;
			File file = alunoFile;
			file.delete();
			alunoFile = new File(dataDir.getPath() + camAlunos);
			for(Aluno a: alunos){
				if(!a.getMatricula().equals(aluno.getMatricula())){
					StringBuilder line = new StringBuilder();
					line.append(a.getNome() + ";" + a.getTelefone() + ";" + a.getTipo() + 
							";" + a.getMatricula() + ";" + a.getEmail() + ";" + a.getCurso() +
							";" + a.getPeriodo() + "\n");
					
					out = new FileWriter(alunoFile, true);
					out.write(line.toString());
					out.close();
				}else{
					nLinha++;
				}
			}
			return nLinha > 0;
		}catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+usuarioFile.toString()+", na linha:"+nLinha,e);
		}
	}
	
	public boolean deletarLivro(Livro livro) throws PersistenciaException{
		List<Livro> livros = recuperarLivros();
		int nLinha = 0;
		try {
			FileWriter out;
			File file = livroFile;
			file.delete();
			livroFile = new File(dataDir.getPath() + camLivros);
			for(Livro l: livros){
				if(!l.getTitulo().equals(livro.getTitulo()) && !l.getEdicao().equals(livro.getEdicao())){
					StringBuilder line = new StringBuilder();
					line.append(livro.getEdicao() + ";" + livro.getTitulo() + ";" + livro.getTipo() + ";" + livro.getAutor()+ ";"+ livro.getCodigo()+"\n");
					out = new FileWriter(livroFile, true);
					out.write(line.toString());
					out.close();
				}else{
					nLinha++;
				}
			}
			return nLinha > 0;
		}catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+livroFile.toString()+", na linha:"+nLinha,e);
		}
	}
	
	public boolean deletarEmprestimo(Emprestimo e) throws PersistenciaException {
		List<Emprestimo> emprestimos = recuperarEmprestimos();
		int nLinha = 0;
		try {
			FileWriter out;
			File file = emprestimoFile;
			file.delete();
			emprestimoFile = new File(dataDir.getPath() + camEmprestimos);
			for(Emprestimo emp: emprestimos){
				if(emp.getId()!=e.getId()){
					StringBuilder line = new StringBuilder();
					line.append(emp.getId() + ";" + emp.getUsuario() + ";" + emp.getData()+ ";" + emp.getDataEntrega());
					for(String s: emp.getCodLivros()){
						line.append(";"+s);
					}
					line.append("\n");
					out = new FileWriter(emprestimoFile, true);
					out.write(line.toString());
					out.close();
				}else{
					nLinha++;
				}
			}
			return nLinha > 0;
		}catch (Exception ex) {
			throw new PersistenciaException("Erro ao ler o arquivo "+emprestimoFile.toString()+", na linha:"+nLinha,ex);
		}

	}
	
	@SuppressWarnings("unused")
	public int recuperarQtdeEmprestimos() throws PersistenciaException{
		FileInputStream in;
		if (!emprestimoFile.exists())
			return 0;
		int nlinha = 0;
		try {
			in = new FileInputStream(emprestimoFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String linha;
			while ((linha = reader.readLine()) != null) {
				nlinha++;
			}
			reader.close();
		} catch (Exception e) {
			throw new PersistenciaException("Erro ao ler o arquivo "+emprestimoFile.toString()+", na linha:"+nlinha,e);
		}
		return nlinha;
	}
}
