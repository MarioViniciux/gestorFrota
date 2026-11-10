package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/**
 * REQUISITO DO PROFESSOR (enum):
 * O combustível deixa de ser uma String livre ("Diesel", "diesel ", "DIESEL"...) e passa a
 * ser um conjunto FECHADO de valores. Isso evita erros de digitação e permite que cada
 * valor carregue dados próprios (descrição, unidade de medida e preço de referência).
 *
 * Em Java, um enum é uma classe especial: cada constante abaixo é um objeto único,
 * criado uma única vez pelo construtor do enum.
 */
// 1.3: enumeração criada para o sistema, usada como atributo de Veiculo
public enum TipoCombustivel {

    // Cada constante chama o construtor: (descrição, unidade de medida, preço por unidade em R$).
    // Os preços são valores de REFERÊNCIA, usados nos cálculos de custo; ajuste quando necessário.
    GASOLINA("Gasolina", "L", 6.20),
    ETANOL("Etanol", "L", 4.30),
    DIESEL("Diesel", "L", 6.10),
    GNV("Gás Natural Veicular", "m³", 4.90),
    ELETRICO("Elétrico", "kWh", 0.90); // o ';' encerra a lista de constantes

    // Nome amigável para exibir em relatórios.
    private final String descricao;
    // Unidade em que o combustível é medido (litros, metros cúbicos, kWh...).
    private final String unidade;
    // Preço por unidade, em reais. Usado em Veiculo.calcularCustoCombustivel().
    private final double precoReferencia;

    // Construtor de enum é sempre privado (implicitamente); só é chamado pelas constantes acima.
    TipoCombustivel(String descricao, String unidade, double precoReferencia) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.precoReferencia = precoReferencia;
    }

    // Apenas getters: os campos são final, então o enum é imutável.
    public String getDescricao() { return descricao; }
    public String getUnidade() { return unidade; }
    public double getPrecoReferencia() { return precoReferencia; }

    /** Faz System.out.println(combustivel) mostrar "Diesel" em vez de "DIESEL". */
    @Override
    public String toString() { return descricao; }
}
