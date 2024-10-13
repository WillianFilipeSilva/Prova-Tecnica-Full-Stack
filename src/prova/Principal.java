package prova;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Principal {

	public static void main(String[] args) {

		List<Candidato> todosCandidatos = Academy_CandidatesCSV.lerCandidatosCSV();

		List<Candidato> candidatosQA = Academy_CandidatesCSV.separarPorVaga(todosCandidatos, "QA");

		List<Candidato> candidatosMobile = Academy_CandidatesCSV.separarPorVaga(todosCandidatos, "Mobile");

		List<Candidato> candidatosWeb = Academy_CandidatesCSV.separarPorVaga(todosCandidatos, "Web");

		int totalCandidatos = todosCandidatos.size();

		double porcentagemQA = calcularPorcentagem(candidatosQA.size(), totalCandidatos);

		double porcentagemMobile = calcularPorcentagem(candidatosMobile.size(), totalCandidatos);

		double porcentagemWeb = calcularPorcentagem(candidatosWeb.size(), totalCandidatos);

		double idadeMediaQA = calcularIdadeMedia(candidatosQA);

		int idadeMaisVelhaMobile = encontrarIdadeMaisVelha(candidatosMobile);

		int idadeMaisNovaWeb = encontrarIdadeMaisNova(candidatosWeb);

		int somaIdadesQA = calcularSomaIdades(candidatosQA);

		int numeroEstadosDistintos = contarEstadosDistintos(todosCandidatos);

		criarArquivoOrdenado(todosCandidatos, "Sorted_Academy_Candidates.csv");

		String instrutorQA = encontrarInstrutorQA(todosCandidatos);

		String instrutorMobile = encontrarInstrutorMobile(todosCandidatos);

		exibirResultados(porcentagemQA, porcentagemMobile, porcentagemWeb, idadeMediaQA, idadeMaisVelhaMobile,
				idadeMaisNovaWeb, somaIdadesQA, numeroEstadosDistintos, instrutorQA, instrutorMobile);
	}

	public static double calcularPorcentagem(int quantidade, int total) {

		if (total == 0) {
			return 0.0;
		}
		return (quantidade * 100.0) / total;
	}

	public static double calcularIdadeMedia(List<Candidato> candidatos) {

		if (candidatos.isEmpty()) {
			return 0.0;
		}
		int soma = 0;
		for (Candidato candidato : candidatos) {
			soma += candidato.getIdade();
		}
		return (double) soma / candidatos.size();
	}

	public static int encontrarIdadeMaisVelha(List<Candidato> candidatos) {

		if (candidatos.isEmpty()) {
			return 0;
		}
		int max = candidatos.get(0).getIdade();
		for (Candidato candidato : candidatos) {
			if (candidato.getIdade() > max) {
				max = candidato.getIdade();
			}
		}
		return max;
	}

	public static int encontrarIdadeMaisNova(List<Candidato> candidatos) {

		if (candidatos.isEmpty()) {
			return 0;
		}
		int min = candidatos.get(0).getIdade();
		for (Candidato candidato : candidatos) {
			if (candidato.getIdade() < min) {
				min = candidato.getIdade();
			}
		}
		return min;
	}

	public static int calcularSomaIdades(List<Candidato> candidatos) {

		int soma = 0;

		for (Candidato candidato : candidatos) {
			soma += candidato.getIdade();
		}
		return soma;
	}

	public static int contarEstadosDistintos(List<Candidato> candidatos) {

		Set<String> estados = new HashSet<>();

		for (Candidato candidato : candidatos) {
			estados.add(candidato.getEstado());
		}
		return estados.size();
	}

	public static void criarArquivoOrdenado(List<Candidato> todosCandidatos, String nomeArquivoOrdenado) {

		List<Candidato> candidatosOrdenados = new ArrayList<>(todosCandidatos);

		Collections.sort(candidatosOrdenados, new Comparator<Candidato>() {
			public int compare(Candidato candidato1, Candidato candidato2) {
				return candidato1.getNome().compareToIgnoreCase(candidato2.getNome());
			}
		});
		try {
			FileWriter escritor = new FileWriter(nomeArquivoOrdenado, StandardCharsets.ISO_8859_1);
			escritor.write("Nome;Idade;Vaga;Estado\n");

			for (Candidato candidato : candidatosOrdenados) {
				escritor.write(candidato.getNome() + ";" + candidato.getIdade() + ";" + candidato.getVaga() + ";"
						+ candidato.getEstado() + "\n");
			}
			escritor.close();

			System.out.println("Arquivo '" + nomeArquivoOrdenado + "' criado com sucesso.");
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo ordenado: " + e.getMessage());
		}
	}

	public static String encontrarInstrutorQA(List<Candidato> candidatos) {

		for (Candidato candidato : candidatos) {
			if (candidato.getVaga().equalsIgnoreCase("QA") && candidato.getEstado().equalsIgnoreCase("SC")
					&& ehQuadradoPerfeito(candidato.getIdade()) && candidato.getIdade() >= 18
					&& candidato.getIdade() <= 30 && ehPalindromo(pegarPrimeiroNome(candidato.getNome()))) {
				return candidato.getNome();
			}
		}
		return "Instrutor de QA não encontrado.";
	}

	public static String encontrarInstrutorMobile(List<Candidato> candidatos) {

		for (Candidato candidato : candidatos) {
			if (candidato.getVaga().equalsIgnoreCase("Mobile") && candidato.getEstado().equalsIgnoreCase("PI")
					&& candidato.getIdade() >= 30 && candidato.getIdade() <= 40 && candidato.getIdade() % 2 == 0
					&& pegarSobrenome(candidato.getNome()).startsWith("C")) {
				return candidato.getNome();
			}
		}
		return "Instrutor de Mobile não encontrado.";
	}

	public static boolean ehQuadradoPerfeito(int numero) {

		int raiz = (int) Math.sqrt(numero);
		return raiz * raiz == numero;
	}

	public static boolean ehPalindromo(String str) {

		String limpa = str.toLowerCase().replaceAll("\\s+", "");
		int len = limpa.length();

		for (int i = 0; i < len / 2; i++) {
			if (limpa.charAt(i) != limpa.charAt(len - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	public static String pegarPrimeiroNome(String nomeCompleto) {

		String[] partes = nomeCompleto.split(" ");
		return partes[0];
	}

	public static String pegarSobrenome(String nomeCompleto) {

		String[] partes = nomeCompleto.split(" ");
		return partes[partes.length - 1];
	}

	public static void exibirResultados(

			double porcentagemQA, double porcentagemMobile, double porcentagemWeb, double idadeMediaQA,
			int idadeMaisVelhaMobile, int idadeMaisNovaWeb, int somaIdadesQA, int numeroEstadosDistintos,
			String instrutorQA, String instrutorMobile) {

		System.out.println("Proporção de candidatos por vaga:");
		System.out.printf("- QA: %.2f%%\n", porcentagemQA);
		System.out.printf("- Mobile: %.2f%%\n", porcentagemMobile);
		System.out.printf("- Web: %.2f%%\n", porcentagemWeb);
		System.out.println();

		System.out.printf("Idade média dos candidatos de QA: %.2f anos\n", idadeMediaQA);
		System.out.println();

		System.out.printf("Idade do candidato mais velho de Mobile: %d anos\n", idadeMaisVelhaMobile);
		System.out.println();

		System.out.printf("Idade do candidato mais novo de Web: %d anos\n", idadeMaisNovaWeb);
		System.out.println();

		System.out.printf("Soma das idades dos candidatos de QA: %d anos\n", somaIdadesQA);
		System.out.println();

		System.out.printf("Número de estados distintos presentes entre os candidatos: %d\n", numeroEstadosDistintos);
		System.out.println();

		System.out.println("Nome do instrutor de QA descoberto: " + instrutorQA);
		System.out.println("Nome do instrutor de Mobile descoberto: " + instrutorMobile);
	}
}