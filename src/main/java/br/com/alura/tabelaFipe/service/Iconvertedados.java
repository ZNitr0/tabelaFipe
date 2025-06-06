package br.com.alura.tabelaFipe.service;

import java.util.List;

public interface Iconvertedados {
    <T> T obterDados(String Json, Class<T> classe);

    <T> List<T>obterLista(String Json, Class<T> classe);
}
