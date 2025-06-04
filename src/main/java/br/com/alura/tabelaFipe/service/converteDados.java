package br.com.alura.tabelaFipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class converteDados implements Iconvertedados {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String Json, Class<T> classe) {
        return null;
    }
}
