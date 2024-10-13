package prova;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Academy_CandidatesCSV {

	private static String nomeArquivo = "src/prova/Academy_Candidates.csv";

	public static void adicionarCandidato(Candidato candidato) {

		try {

			boolean arquivoExiste = new File(nomeArquivo).exists();
			FileWriter escritor = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1, true);
			if (!arquivoExiste) {
				escritor.write("Nome;Idade;Vaga;Estado\n");
			}
			escritor.write(candidato.getNome() + ";" + candidato.getIdade() + ";" + candidato.getVaga() + ";"
					+ candidato.getEstado() + "\n");
			escritor.flush();
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Candidato> lerCandidatosCSV() {

		List<Candidato> lista = new ArrayList<>();

		try {

			BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
			String linha;
			boolean primeiraLinha = true;
			while ((linha = leitor.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				String[] partes = linha.split(";");
				String nome = partes[0].trim();
				int idade = Integer.parseInt(partes[1].replaceAll("[^0-9]", "").trim());
				String vaga = partes[2].trim();
				String estado = partes[3].trim();

				Candidato candidato = new Candidato(nome, idade, vaga, estado);

				lista.add(candidato);
			}
			leitor.close();
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Erro ao converter a idade para número: " + e.getMessage());
		}
		return lista;
	}

	public static List<Candidato> separarPorVaga(List<Candidato> todosCandidatos, String vagaDesejada) {

		List<Candidato> separados = new ArrayList<>();

		for (Candidato candidato : todosCandidatos) {
			if (candidato.getVaga().equalsIgnoreCase(vagaDesejada)) {
				separados.add(candidato);
			}
		}
		return separados;
	}
}