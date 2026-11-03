package com.mycompany.gestorfrotaentrega.teste;

import com.mycompany.gestorfrotaentrega.modelo.Veiculo;
import com.mycompany.gestorfrotaentrega.modelo.ItemChecagem;

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
    }
}
