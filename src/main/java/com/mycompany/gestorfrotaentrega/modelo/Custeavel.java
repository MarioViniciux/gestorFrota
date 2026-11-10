package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/**
 * REQUISITO DO PROFESSOR (interface):
 * Contrato de tudo aquilo que GERA CUSTO operacional. A interface diz O QUE deve ser
 * calculado, sem dizer COMO: cada classe que a implementa escolhe a fórmula.
 *
 * Quem USA a interface (ex.: RepositorioVeiculos) só precisa saber que o objeto é
 * "Custeavel", sem se preocupar se é um Carro, uma Moto ou um Caminhão.
 */
// 8: interface implementada por Veiculo (e suas filhas); sem get/set nem construtores
public interface Custeavel {

    /**
     * Custo total de uma viagem.
     *
     * @param km distância da viagem em quilômetros (não pode ser negativa)
     * @return custo em reais (combustível + desgaste)
     */
    double calcularCustoViagem(double km);

    /**
     * Custo médio de manutenção/desgaste por quilômetro rodado.
     *
     * @return reais por km
     */
    double calcularCustoManutencaoPorKm();

    /**
     * Método DEFAULT (Java 8+): já vem implementado na interface e as classes
     * podem usá-lo sem escrever nada. Aqui, custo de rodar 1 km, derivado do
     * método abstrato acima.
     */
    default double calcularCustoPorKm() {
        return calcularCustoViagem(1.0);
    }
}
