package biblioteca.ui;

import java.util.ArrayList;
import java.util.List;

import logger.Logger;
import biblioteca.Aluno;
import biblioteca.BibliotecaException;
import biblioteca.BibliotecaFacade;
import biblioteca.Emprestimo;
import biblioteca.EmprestimoException;
import biblioteca.Livro;
import biblioteca.TipoNaoEcontradoException;
import biblioteca.Usuario;
import biblioteca.io.PersistenciaException;

public class Biblioteca {

	private BibliotecaFacade facade;
	
	public Biblioteca() {
		this.facade = new BibliotecaFacade();
	}

	public static void main(String[] args) {
		Biblioteca bib = new Biblioteca();
		bib.exibirMenu();
	}
	
	public void exibirMenu(){
		try{
			if(!facade.verificarEmprestimo().equals("")){
				Util.alert(facade.verificarEmprestimo());
			}
			StringBuffer menu = new StringBuffer();
			menu.append("==== SISTEMA DE CONTROLE DE BIBLIOTECA ====\n");
			menu.append("     0 - SAIR\n");
			menu.append("     1 - Menu Cadastro\n");
			menu.append("     2 - Menu Consultar\n");
			menu.append("     3 - Menu Deletar\n");
			menu.append("     4 - Menu Editar\n");
			menu.append("     5 - Menu Empr�stimo\n");
			menu.append("=========================================\n");
			menu.append("Digite a op��o:");
			boolean fim = false;
			
			do{
				String cod;
				switch(Util.lerInteiro(menu.toString())){
				case 0:
					Util.alert("At� a pr�xima!");
					fim = true;
					break;
				case 1:
					int opcao = Util.lerInteiro("Cadastrar:\n1-Usuario\n2-Livro");
					if(opcao==1)
						cadastrarUsuario();
					else if(opcao==2)
						cadastrarLivro();
					else
						Util.alert("Op��o inv�lida!");
					break;
				case 2:
					opcao = Util.lerInteiro("Digite:\n1-Listar Usuarios\n2-Listar Livros\n" +
							"3-Listar Empr�stimos\n4-Pesquisar");
					if(opcao==1)
						listarUsuarios();
					else if(opcao==2)	
						listarLivros();
					else if(opcao==3)
						listarEmprestimos();
					else if(opcao==4){
						cod = Util.lerString("Digite a matr�cula do usu�rio, o c�digo do livro ou o ID do empr�stimo:");
						consultar(cod);
					}else
						Util.alert("Op��o inv�lida!");
					break;
				case 3:
					deletar();
					break;
				case 4:
					editar();
					break;
				case 5:
					opcao = Util.lerInteiro("Digite:\n1-Empr�stimo\n2-Devolu��o");
					if(opcao==1)
						emprestimo();
					else if(opcao==2)
						devolucao();
					else
						Util.alert("Op��o inv�lida!");
					break;
				default:
					Util.alert("Op��o inv�lida!");
				}
			}while(!fim);
		}catch(Throwable e){
			Logger.getInstance().log(e);
			Util.alert("Erro do sistema. Chame o suporte!");
		}
	}

	private void listarLivros() {
		StringBuilder msg = new StringBuilder();
		try {
			List<Livro> us = facade.listarLivros();
			for(Livro l: us){
				msg.append(l.toString()+"\n");
			}
			if(!msg.toString().equals(""))
				Util.alert(msg.toString());
			else
				Util.alert("Nenhum encontrado!");
		} catch (PersistenciaException e) {
			Logger.getInstance().log(e);
			msg.append("Erro ao recuperar livros. Ligue para o suporte!\n");
		}
		Util.alert(msg.toString());
		
	}

	private void listarUsuarios() {
		StringBuilder msg = new StringBuilder();
		try {
			int op = Util.lerInteiro("Listar?\nDigite:\n1-Aluno\n2-Professor\n3-Funcion�rio");
			if(op == 2){
				List<Usuario> us = facade.listarUsuarios();
				for(Usuario u: us){
					if(u.getTipo().equals("PROFESSOR"))
						msg.append(u.toString()+"\n");
				}
			}else if(op == 3){
				List<Usuario> us = facade.listarUsuarios();
				for(Usuario u: us){
					if(u.getTipo().equals("FUNCIONARIO"))
						msg.append(u.toString()+"\n");
				}
			}else if(op == 1){
				List<Aluno> al = facade.listarAlunos();
				for(Aluno a: al){
					msg.append(a.toString()+"\n");
				}
			}
			if(!msg.toString().equals(""))
				Util.alert(msg.toString());
			else
				Util.alert("Nenhum encontrado!");
		} catch (PersistenciaException e) {
			Logger.getInstance().log(e);
			msg.append("Erro ao recuperar usuarios. Ligue para o suporte!\n");
		}		
	}
	
	private void listarEmprestimos(){
		StringBuilder msg = new StringBuilder();
		try {
			List<Emprestimo> emps = facade.listarEmprestimos();
			for(Emprestimo e: emps){
				msg.append(e.toString()+"\n");
			}
			if(!msg.toString().equals(""))
				Util.alert(msg.toString());
			else
				Util.alert("Nenhum encontrado!");
		} catch (PersistenciaException e) {
			Logger.getInstance().log(e);
			msg.append("Erro ao recuperar livros. Ligue para o suporte!\n");
		}
		Util.alert(msg.toString());
	}
	
	private void cadastrarLivro() {
		String titulo = Util.lerString("Titulo:");
		String autor = Util.lerString("Autor:");
		int tipoInt = Util.lerInteiro("Tipo:\n1-Livro\n2-TCC\n3-Peri�dico");
		
		String tipo = null;
		if(tipoInt > 3 && tipoInt < 1){
			while(tipoInt > 3 && tipoInt < 1){
				tipoInt = Util.lerInteiro("Tente novamente!\n1-Livro\n2-TCC\n3-Peri�dico");
			}
		}else{
			if(tipoInt == 1)
				tipo = "LIVRO";
			else if(tipoInt == 2)
				tipo = "TCC";
			else if(tipoInt == 3)
				tipo = "PERIODICO";
		}
		
		String edicao = Util.lerString("Edi��o:");
		String codigo = Util.lerString("Codigo:");
		Livro l = null;
		
		try{
			l = facade.criarLivro(edicao,titulo,tipo,autor,codigo);
			if(l != null)
				Util.alert("Livro cadastrado com sucesso!\n"+l.toString());
			else
				Util.alert("ERRO!");
		} catch (BibliotecaException e1) {
			Util.alert(e1.getMessage());
		} catch(PersistenciaException e2){
			Util.alert("Erro na grava��o do arquivo! Tente novamente ou chame o suporte.");
			Logger.getInstance().log(e2);
		}
	}

	private void cadastrarUsuario() {
		String nome = Util.lerString("Nome:");
		String matricula = Util.lerString("Matricula:");
		String telefone = Util.lerString("Telefone:");
		String endereco = Util.lerString("Endere�o:");
		String email = Util.lerString("E-mail:");
		
		int tipoInt = Util.lerInteiro("Tipo:\n1-ALUNO\n2-PROFESSOR\n3-FUNCION�RIO");
		Usuario u = null;
		String tipo = "";
		
		try{
			while(facade.testarMatricula(matricula)){
				matricula = Util.lerString("Matr�cula j� cadastrada no sistema!\n" +
						"Tente novamente!\n Matr�cula:");
			}
			if(tipoInt > 3 && tipoInt < 1){
				while(tipoInt > 3 && tipoInt < 1){
					tipoInt = Util.lerInteiro("Tente novamente!\n1-ALUNO\n2-PROFESSOR\n3-FUNCION�RIO");
				}
			}else{
				if(tipoInt == 1){
					tipo = "ALUNO";
					String curso = Util.lerString("Curso: ");
					String periodo = Util.lerString("Periodo: ");
					u = facade.criarAluno(nome, telefone, matricula, email, tipo, endereco, curso, periodo);
				}else if(tipoInt == 2){
					tipo = "PROFESSOR";
					u = facade.criarUsuario(nome, telefone, matricula, email, tipo, endereco);
				}else if(tipoInt == 3){
					tipo = "FUNCIONARIO";
					u = facade.criarUsuario(nome, telefone, matricula, email, tipo, endereco);
				}
			}
			if(u != null)
				Util.alert("Usuario cadastrado com sucesso!\n"+u.toString());
			else
				Util.alert("ERRO!");
		} catch (BibliotecaException e1) {
			Util.alert(e1.getMessage());
		} catch(PersistenciaException e2){
			Util.alert("Erro na grava��o do arquivo! Tente novamente ou chame o suporte.");
			Logger.getInstance().log(e2);
		}
		
	}
	
	private void deletar(){
		int opcao = Util.lerInteiro("Deseja deletar:\n1-Aluno\n2-Professor\n3-Funcion�rio\n4-Livro");
		String matricula = "";
		if(opcao<1 && opcao>4){
			while(opcao<1 && opcao>4){
				opcao = Util.lerInteiro("Tente novamente:\n1-Aluno\n2-Professor\n3-Funcion�rio\n4-Livro");
			}
		}else{
			if(opcao == 1){
				try {
					matricula = Util.lerString("Digite a matr�cula para que possamos deletar:");
					facade.deletar(matricula);
					Util.alert("Aluno deletado com sucesso!");
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}else if(opcao == 2 || opcao == 3){
				try {
					matricula = Util.lerString("Digite a matr�cula para que possamos deletar:");
					facade.deletar(matricula);
					Util.alert("Usuario deletado com sucesso!");
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}else if(opcao == 4){
				try {
					String codigo = Util.lerString("Digite o C�digo para que possamos deletar:");
					codigo.toUpperCase();
					facade.deletar(codigo);
					Util.alert("Livro deletado com sucesso!");
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}
		}
	}
	
	private void editar(){
		int opcao = Util.lerInteiro("Deseja Editar:\n1-Aluno\n2-Professor e Funcion�rio\n3-Livro");
		String matricula = "";
		if(opcao<1 || opcao>3){
			while(opcao<1 && opcao>3){
				opcao = Util.lerInteiro("Tente novamente:\n1-Aluno\n2-Professor e Funcion�rio\n3-Livro");
			}
		}else{
			if(opcao==1){
				try{
					boolean b = true;
					while(b){
						matricula = Util.lerString("Digite a matr�cula para que possamos editar:");
						Aluno a = facade.getAluno(matricula);
						Aluno x = null;
						int opd = Util.lerInteiro(a.toString()+"\n� esse que deseja editar?\n1-Sim 2-N�o");
						if(opd == 2){
							opcao = 0;
						}else if(opd<1 || opd>2){
							Util.alert("Op��o inv�lida!");
							while(opd<1 || opd>2){
								opd = Util.lerInteiro(a.toString()+"\nTente novamente!\n� esse que deseja editar?\n1-Sim 2-N�o");
							}
						}else if(opd == 1){
							b = false;
							String nome = a.getNome(), telefone = a.getTelefone(), email = a.getEmail(),
									tipo = a.getTipo(), curso = a.getCurso(), periodo = a.getPeriodo(),
									endereco = a.getEndereco();
							
							nome = Util.lerString("Nome:",a.getNome());
							endereco = Util.lerString("Endere�o:",a.getEndereco());
							telefone = Util.lerString("Telefone:",a.getTelefone());
							email = Util.lerString("E-mail:",a.getEmail());
							curso = Util.lerString("Curso:",a.getCurso());
							periodo = Util.lerString("Periodo:",a.getPeriodo());
							facade.deletar(matricula);
							x = facade.criarAluno(nome, telefone, matricula, email, tipo, endereco, curso, periodo);
						}else if(opd==2){}
						else
							Util.alert("Op��o inv�lida!");
						if(x != null)
							Util.alert("Edi��o feita com sucesso!\n"+x.toString());
					}
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				} catch (BibliotecaException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}else if(opcao == 2){
				try{
					boolean b = true;
					while(b){
						matricula = Util.lerString("Digite a matr�cula para que possamos editar:");
						Usuario a = facade.getUsuario(matricula);
						Usuario x = null;
						int opd = Util.lerInteiro(a.toString()+"\n� esse que deseja editar?\n1-Sim 2-N�o");
						if(opd == 2){
							opcao = 0;
						}else if(opd<1 || opd>2){
							Util.alert("Op��o inv�lida!");
							while(opd<1 || opd>2){
								opd = Util.lerInteiro(a.toString()+"\nTente novamente!\n� esse que deseja editar?\n1-Sim 2-N�o");
							}
						}else if(opd == 1){
							b = false;
							String nome = a.getNome(), telefone = a.getTelefone(), email = a.getEmail(),
									tipo = a.getTipo(),	endereco = a.getEndereco();
							
							nome = Util.lerString("Nome:",a.getNome());
							endereco = Util.lerString("Endere�o:",a.getEndereco());
							telefone = Util.lerString("Telefone:",a.getTelefone());
							email = Util.lerString("E-mail:",a.getEmail());						
							facade.deletar(matricula);
							x = facade.criarUsuario(nome, telefone, matricula, email, tipo, endereco);
						}else if(opd==2){}
						else
							Util.alert("Op��o inv�lida!");
						if(x != null)
							Util.alert("Edi��o feita com sucesso!\n"+x.toString());
					}
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				} catch (BibliotecaException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}else if(opcao == 3){
				try{
					boolean b = true;
					String codigo;
					while(b){
						codigo = Util.lerString("Digite o c�digo para que possamos editar:");
						Livro a = facade.getLivro(codigo);
						String titulo = a.getTitulo(), edicao = a.getEdicao(), autor = a.getAutor(),
								tipo = a.getTipo();
						Livro x = null;
						int opd = Util.lerInteiro(a.toString()+"\n� esse que deseja editar?\n1-Sim 2-N�o");
						if(opd<1 || opd>2){
							Util.alert("Op��o inv�lida!");
							while(opd<1 || opd>2){
								opd = Util.lerInteiro(a.toString()+"\nTente novamente!\n� esse que deseja editar?\n1-Sim 2-N�o");
							}
						}else if (opd == 1){
							b = false;
							titulo = Util.lerString("T�tulo:",a.getTitulo());
							edicao = Util.lerString("Edi��o:",a.getEdicao());
							autor = Util.lerString("Autor:",a.getAutor());
							tipo = Util.lerString("Tipo:",a.getTipo());
							facade.deletar(codigo);
							x = facade.criarLivro(edicao, titulo, tipo, autor, codigo);	
						}else if(opd==2){}
						else
							Util.alert("Op��o inv�lida!");
						if(x != null)
							Util.alert("Livro editado com sucesso!\n"+x.toString());
					}
				} catch(PersistenciaException e2){
					Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
					Logger.getInstance().log(e2);
				} catch (TipoNaoEcontradoException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				} catch (BibliotecaException e) {
					Util.alert(e.getMessage());
					Logger.getInstance().log(e);
				}
			}
		}
	}
	
	private void consultar(String cod){
		try {
			Util.alert(facade.getTipo(cod).toString());
		} catch(PersistenciaException e2){
			Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
			Logger.getInstance().log(e2);
		} catch (TipoNaoEcontradoException e) {
			Util.alert(e.getMessage());
			Logger.getInstance().log(e);
		}
	}
	
	private void emprestimo() {
		String matricula = Util.lerString("Digite a matr�cula do usu�rio:");
		List<String> codigos = new ArrayList<String>();
		boolean b = true;
		String cod = Util.lerString("Digite o c�digo do livro:");
		String codi = cod.toUpperCase();
		codigos.add(codi);
		while(b){
			int opcao = Util.lerInteiro("Deseja adicionar outro livro?\n1-Sim 2-N�o:");
			if(opcao == 1){
				cod = Util.lerString("Digite o c�digo do livro:");
				codigos.add(cod);
			}else if(opcao == 2){
				b = false;
			}else{
				Util.alert("Op��o inv�lida!");
			}
		}
		try {
			Util.alert(facade.criarEmprestimo(matricula, codigos).toString());
		} catch (PersistenciaException e1) {
			Util.alert("Erro na leitura do arquivo! Tente novamente ou chame o suporte.");
			Logger.getInstance().log(e1);
		} catch (EmprestimoException e2) {
			Util.alert(e2.getMessage());
			Logger.getInstance().log(e2);
		} catch (TipoNaoEcontradoException e3) {
			Util.alert(e3.getMessage());
			Logger.getInstance().log(e3);
		}
	}
	
	private void devolucao() {
		int codigo = Util.lerInteiro("Digite o c�digo do empr�stimo");
		try {
			if(facade.deletarEmprestimo(codigo)){
				
			}
		} catch (PersistenciaException e) {
			Util.alert(e.getMessage());
			Logger.getInstance().log(e);
		} catch (TipoNaoEcontradoException e) {
			Util.alert(e.getMessage());
			Logger.getInstance().log(e);
		}
	}

}
