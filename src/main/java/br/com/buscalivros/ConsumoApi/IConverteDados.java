package br.com.BuscaLivros.consumoApi;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}