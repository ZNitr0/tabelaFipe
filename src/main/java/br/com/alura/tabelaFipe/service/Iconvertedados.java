package br.com.alura.tabelaFipe.service;

public interface Iconvertedados {
    <T> T obterDados(String Json, Class<T> classe);
}
