package prova;

import java.util.Objects;

public class Candidato {

	private String nome;
	private int idade;
	private String vaga;
	private String estado;

	public Candidato(String nome, int idade, String vaga, String estado) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.vaga = vaga;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getVaga() {
		return vaga;
	}

	public void setVaga(String vaga) {
		this.vaga = vaga;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int hashCode() {
		return Objects.hash(estado, idade, nome, vaga);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidato outro = (Candidato) obj;
		return Objects.equals(estado, outro.estado) && idade == outro.idade && Objects.equals(nome, outro.nome)
				&& Objects.equals(vaga, outro.vaga);
	}
}