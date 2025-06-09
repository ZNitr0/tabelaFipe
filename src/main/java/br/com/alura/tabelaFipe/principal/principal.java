package br.com.alura.tabelaFipe.principal;

import br.com.alura.tabelaFipe.model.Dados;
import br.com.alura.tabelaFipe.model.Modelos;
import br.com.alura.tabelaFipe.model.Veiculo;
import br.com.alura.tabelaFipe.service.consumoApi;
import br.com.alura.tabelaFipe.service.converteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class principal {
        private Scanner leitura = new Scanner(System.in);
        private consumoApi consumo = new consumoApi();
        private converteDados conversor = new converteDados();

        private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

        public void exibeMenu() {
            var menu = """
            *** OPÇÕES ***
            Carro
            Moto
            Caminhão
            
            Digite uma das opções para consulta:
            
            """;

            System.out.println(menu);
            var opcao = leitura.nextLine();
            String endereco;

            if (opcao.toLowerCase().contains("carr")){
                endereco = URL_BASE + "carros/marcas";
            } else if (opcao.toLowerCase().contains("mot")) {
                endereco = URL_BASE + "motos/marcas";
            } else {
                endereco = URL_BASE + "caminhoes/marcas";
            }

            var json = consumo.obterDados(endereco);
            System.out.println(json);
//            conversor de dados
            var marcas = conversor.obterLista(json, Dados.class);
            marcas.stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);

            System.out.println("Informe o codigo da marca que deseja consultar: ");
            var codigoMarca = leitura.nextLine();

            endereco = endereco + "/" + codigoMarca + "/modelos";
            json = consumo.obterDados(endereco);
            var modeloLista = conversor.obterDados(json, Modelos.class);

            System.out.println("\nModelos desta marca:");
            modeloLista.modelos().stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);

            // código omitido

            System.out.println("\nDigite uma parte do nome do carro para ser buscado:");
            var nomeVeiculo = leitura.nextLine();

            List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                    .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                    .collect(Collectors.toList());

            System.out.println("\nModelos filtrados:");
            modelosFiltrados.forEach(System.out::println);

            System.out.println("digite o codigo do modelo para buscar as avaliações:");
            var codigoModelo = leitura.nextLine();

            endereco = endereco + "/" + codigoModelo + "/anos";
            json = consumo.obterDados(endereco);
            List<Dados>Anos = conversor.obterLista(json, Dados.class);
            List<Veiculo> veiculos = new ArrayList<>();

            for (int i = 0; i < Anos.size(); i++) {
                var enderecoAnos = endereco + "/" + Anos.get(i).codigo();
                json = consumo.obterDados(enderecoAnos);
                Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
                veiculos.add(veiculo);
            }

            System.out.println("\nModelos filtrados:");
            veiculos.forEach(System.out::println);

        }
    }
