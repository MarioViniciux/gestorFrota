
package com.mycompany.gestorfrotaentrega.teste;

import com.mycompany.gestorfrotaentrega.modelo.Veiculo;
import com.mycompany.gestorfrotaentrega.modelo.ItemChecagem;
import com.mycompany.gestorfrotaentrega.modelo.Motorista;
import com.mycompany.gestorfrotaentrega.modelo.CategoriaCNH;
import com.mycompany.gestorfrotaentrega.aplicacao.RepositorioMotoristas;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import java.util.ArrayList;
//gestaoveicular
public class Main {

    public static void main(String[] args) {
        // 1. Instancia o veículo (tipo, motor, combustível, potência, baú, CMT, consumo, tara)
        Veiculo caminhao = new Veiculo("Volvo FH 540 6x4", "D13 (12,8 litros, 6 cilindros em linha)", "Diesel",
                540.0, 40.0, 80000.0, 2.2, 26000.0);

        System.out.println(">>> Estado inicial");
        caminhao.exibirRelatorioChecagem();

        // Carga dentro do limite e depois acima do limite (dispara alerta de PBTC)
        System.out.println(">>> Carregando 12.000 kg");
        caminhao.carregarVeiculo(12000);
        System.out.printf("PBTC atual: %.1f kg%n%n", caminhao.getPbtc());

        System.out.println(">>> Carregando 16.000 kg (acima da CMT)");
        caminhao.carregarVeiculo(16000);
        System.out.println();

        // Reprovação manual de um item e bloqueio do veículo
        System.out.println(">>> Reprovando manualmente as lonas de freio");
        ItemChecagem lonas = caminhao.buscarItem("Lonas de freio");
        lonas.marcarApto(false);
        System.out.println("Veículo apto? " + caminhao.isApto());
        caminhao.exibirRelatorioChecagem();

        // Reparo do item
        System.out.println(">>> Realizando manutenção das lonas de freio");
        lonas.concluirManutencao();
        System.out.println("Veículo apto? " + caminhao.isApto() + "\n");

        // Simulação de rodagem para disparar alertas por km e por tempo
        System.out.println(">>> Rodando 5.000 km / 3 meses");
        caminhao.rodar(5000, 3);
        System.out.println("Veículo apto? " + caminhao.isApto() + "\n");

        System.out.println(">>> Rodando mais 5.000 km / 3 meses (óleo e filtros atingem 10.000 km / 6 meses)");
        caminhao.rodar(5000, 3);
        System.out.println("Veículo apto? " + caminhao.isApto() + "\n");

        System.out.println(">>> Rodando mais 6.000 km / 7 meses (ar condicionado e outros itens vencem)");
        caminhao.rodar(6000, 7);
        System.out.println("Veículo apto? " + caminhao.isApto() + "\n");

        caminhao.exibirRelatorioChecagem();

        // Manutenção do óleo do motor
        System.out.println(">>> Trocando o óleo do motor");
        caminhao.buscarItem("Óleo do Motor").concluirManutencao();
        System.out.println("Óleo do Motor apto? " + caminhao.buscarItem("Óleo do Motor").isApto());
    
    
       // TESTE DE CADASTRO E PERSISTENCIA DOS MOTORISTAS

        System.out.println("\n>>> TESTE DE MOTORISTAS");

        // Cria as categorias da CNH
        Set<CategoriaCNH> categorias = new HashSet<>();
        categorias.add(CategoriaCNH.A);
        categorias.add(CategoriaCNH.B);

        // Cria o primeiro motorista
        Motorista motorista1 = new Motorista(
                "Maria",
                25,
                "12345678901",
                "88999999999",
                "maria@email.com",
                "98765432100",
                categorias,
                LocalDate.of(2028, 10, 15),
                true
        );

        // Cria o segundo motorista
        Motorista motorista2 = new Motorista(
                "Joao",
                30,
                "98765432109",
                "88988887777",
                "joao@email.com",
                "12345678909",
                categorias,
                LocalDate.of(2029, 12, 20),
                true
        );

        // Cria o repositorio
        RepositorioMotoristas repositorio = new RepositorioMotoristas();

        try {
            // TESTE 1: Cadastro do primeiro motorista
            System.out.println("\nTeste 1 - Primeiro cadastro:");

            repositorio.cadastrar(motorista1);
            System.out.println("Motorista 1 cadastrado com sucesso!");

            // TESTE 2: Cadastro do segundo motorista
            System.out.println("\nTeste 2 - Segundo cadastro:");

            repositorio.cadastrar(motorista2);
            System.out.println("Motorista 2 cadastrado com sucesso!");

            // TESTE 3: Tentativa de cadastrar novamente o primeiro
            System.out.println("\nTeste 3 - Cadastro duplicado:");

            repositorio.cadastrar(motorista1);

            System.out.println("ERRO: O sistema permitiu um cadastro duplicado!");

        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: Duplicidade identificada!");
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Erro no arquivo: " + e.getMessage());
        }
        
        // TESTE 4: Tentativa de cadastrar CNH duplicada
                // Cria um terceiro motorista com CPF diferente,
                // mas com a mesma CNH de Maria
         Motorista motorista3 = new Motorista(
            "Pedro",
              28,
            "11122233344",
             "88977776666",
            "pedro@email.com",
            "98765432100",
            categorias,
            LocalDate.of(2030, 5, 20),
            true
            );
        
        try {
            System.out.println("\nTeste 4 - CNH duplicada:");

            repositorio.cadastrar(motorista3);

            System.out.println("ERRO: O sistema permitiu uma CNH duplicada!");

            } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: CNH duplicada identificada!");
            System.out.println(e.getMessage());

            } catch (IOException e) {
            System.out.println("Erro no arquivo: " + e.getMessage());
            }

        // TESTE 4: Carregamento dos motoristas
        try {
            System.out.println("\nTeste 4 - Carregamento:");

            ArrayList<Motorista> motoristasCarregados = repositorio.carregar();

            System.out.println("Total de motoristas: " + motoristasCarregados.size());

            for (Motorista motorista : motoristasCarregados) {
                System.out.println("Nome: " + motorista.getNome());
                System.out.println("CPF: " + motorista.getCpf());
                System.out.println("CNH: " + motorista.getNumeroCNH());
                System.out.println("Categorias: " + motorista.getTiposCNH());
                System.out.println("-------------------------");
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar motoristas: " + e.getMessage());
        }
    }
}