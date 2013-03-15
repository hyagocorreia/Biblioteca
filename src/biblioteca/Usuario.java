package biblioteca;


public class Usuario{
	
	private String nome, telefone, matricula, email, tipo, endereco;

	public Usuario(String nome, String telefone, String matricula,String email, String tipo,String endereco) {
		this.nome = nome;
		this.telefone = telefone;
		this.matricula = matricula;
		this.email = email;
		this.tipo = tipo;
		this.endereco = endereco;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String toString() {
		return "____________________\nUSUÁRIO" +
				"\nNome: " + nome +
				"\nEmail: " + email +
				"\nTelefone: "+ telefone +
				"\nEndereço: "+ endereco +
				"\nMatricula: " + matricula+ 
				"\nTipo: " + tipo;
	}	
}
