package br.com.BuscaLivros.consumoApi;

import br.com.BuscaLivros.model.DadosLivro;

import java.util.List;

public class RespostaApi {
    private int count;
    private String next;
    private String previous;
    private List<DadosLivro> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<DadosLivro> getResults() {
        return results;
    }

    public void setResults(List<DadosLivro> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "RespostaAPI{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}