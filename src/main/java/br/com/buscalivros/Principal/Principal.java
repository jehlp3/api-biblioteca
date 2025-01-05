package br.com.BuscaLivros.principal;


import br.com.BuscaLivros.consumoApi.ConsumoApi;
import br.com.BuscaLivros.consumoApi.ConverteDados;
import br.com.BuscaLivros.consumoApi.RespostaApi;
import br.com.BuscaLivros.model.Autor;
import br.com.BuscaLivros.model.DadosLivro;
import br.com.BuscaLivros.model.Livro;
import br.com.BuscaLivros.repository.AutorRepository;
import br.com.BuscaLivros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Principal {
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private List<DadosLivro> dadosSeries = new ArrayList<>();
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1  - Buscar Livro pelo Título
                    2  - Listar Livros Registrados
                    3  - Listar Autores Registrados
                    4  - Listar Autores Vivos em um Determinado Ano
                    5  - Listar Livros em um Determinado Idíoma
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    //Case01
    private void buscarLivro() {
        System.out.println("Digite o nome do livro para busca:");
        String nomeLivro = leitura.nextLine();

        var livroExistente = livroRepository.findByTituloContainingIgnoreCase(nomeLivro);
        if (livroExistente.isPresent()) {
            System.out.println("Livro encontrado no banco de dados:");
            System.out.println(livroExistente.get());
            return;
        }

        DadosLivro dados = getDadosLivro(nomeLivro);
        if (dados == null) {
            System.out.println("Livro não encontrado!!!");
            return;
        }

        salvarLivro(dados);
        System.out.println("Livro salvo no banco de dados.");
        System.out.println(dados);
    }

    private DadosLivro getDadosLivro(String nomeLivro) {
        String enderecoBusca = ENDERECO + nomeLivro.replace(" ", "+");
        System.out.println(enderecoBusca);
        var json = consumo.obterDados(enderecoBusca);


        if (json.contains("{\"count\":0,\"next\":null,\"previous\":null,\"results\":[]}")) {
            return null;
        } else {
            RespostaApi resposta = conversor.obterDados(json, RespostaApi.class);

            List<DadosLivro> livros = resposta.getResults();

            if (!livros.isEmpty()) {
                DadosLivro dados = livros.get(0);
                return dados;
            } else {
                return null;
            }
        }
    }

    private void salvarLivro(DadosLivro dadosLivro) {
        Livro livro = new Livro();
        livro.setTitulo(dadosLivro.titulo());
        livro.setDownloads(dadosLivro.downloads());
        livro.setLingua(dadosLivro.idiomas().get(0));

        //System.out.println(dadosLivro.autores().get(0).name());
        Autor autor ;
        var autorExistente = autorRepository.findByNome(dadosLivro.autores().get(0).name());

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();

        } else {
            autor = new Autor();
            autor.setNome(dadosLivro.autores().get(0).name());
            autor.setAnoNascimento(dadosLivro.autores().get(0).birthYear());
            autor.setAnoMorte(dadosLivro.autores().get(0).deathYear());
            autorRepository.save(autor);

        }
        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    //Case02
    private void listarLivrosRegistrados() {
        listarItens("Livros Registrados", livroRepository.findAll());
    }

    //Case03
    private void listarAutoresRegistrados() {
        listarItens("Autores Registrados", autorRepository.findAll());
    }

    //Case04
    private void listarAutoresVivosPorAno() {
        System.out.println("Informe o ano que deseja saber se o autor está vivo: ");
        int ano = leitura.nextInt();
        listarItens("Autores vivos na data de " + ano, autorRepository.findByAnoMorteGreaterThan(ano));
    }

    //Case05
    private void listarLivrosPorIdioma() {
        System.out.println("Informe a sigla do idioma (pt = Português, en = Inglês, es = Espanhol, zh = Chinês):");
        String lingua = leitura.nextLine().trim().toLowerCase();

        Map<String, String> idiomas = Map.of(
                "pt", "Português",
                "en", "Inglês",
                "es", "Espanhol",
                "zh", "Chinês"
        );

        String idiomaCompleto = idiomas.getOrDefault(lingua, "Desconhecido");

        if (idiomaCompleto.equals("Desconhecido")) {
            System.out.println("Sigla de idioma não definida ou inválida!!!");
            return;
        }

        List<Livro> livrosPorIdioma = livroRepository.livrosPorIdioma(lingua);

        listarItens("Livros no idioma " + idiomaCompleto, livrosPorIdioma);
    }



    private <T> void listarItens(String titulo, List<T> itens) {
        if (itens.isEmpty()) {
            System.out.println("------A pesquisa não retornou dados------");
        } else {
            System.out.println("------------" + titulo + "------------");
            itens.forEach(System.out::println);
            System.out.println("------------------------------------------");
        }
    }

}