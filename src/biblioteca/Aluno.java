package biblioteca;


public class Aluno extends Usuario {
	
	private String curso, periodo;
	
	public Aluno(String nome, String telefone, String matricula, String email, String tipo, String endereco, String curso,String periodo) {
		super(nome, telefone, matricula, email, tipo, endereco);
		this.curso = curso;
		this.periodo = periodo;
	}
	
	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String toString() {
		return super.toString()+"\nCurso: " + curso + "\nPeriodo: " + periodo +"\n";
	}
}
