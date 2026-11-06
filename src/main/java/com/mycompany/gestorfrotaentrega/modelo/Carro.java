package com.mycompany.gestorfrotaentrega.modelo;

/**
 * REQUISITO DO PROFESSOR (herança):
 * Carro ESPECIALIZA Veiculo ("é um" Veiculo). Herda placa, modelo, combustível, consumo,
 * relatório etc., e acrescenta apenas o que é próprio de um carro de entregas leves.
 */
public class Carro extends Veiculo {

    // Custo de desgaste (pneus, freios, revisões) por km, em reais.
    // static final = constante compartilhada por todos os carros. Valor ilustrativo.
    private static final double CUSTO_DESGASTE_POR_KM = 0.15;

    // Atributos específicos do carro (privados: encapsulamento).
    private int numeroPortas;
    private double volumePortaMalas; // em litros

    /**
     * REQUISITO (sobrecarga de construtores) - versão COMPLETA.
     * super(...) chama o construtor da classe-pai para preencher os dados comuns.
     */
    public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,
                 double potencia, double consumo, int numeroPortas, double volumePortaMalas) {
        super(placa, modelo, motor, combustivel, potencia, consumo); // sempre a 1ª linha
        setNumeroPortas(numeroPortas);           // usa o setter para validar
        setVolumePortaMalas(volumePortaMalas);
        carregarSistemasDoManual();              // monta o checklist deste tipo de veículo
    }

    /**
     * REQUISITO (sobrecarga de construtores) - versão SIMPLES.
     * Assume motor não informado, potência 0, 4 portas e 400 L de porta-malas.
     */
    public Carro(String placa, String modelo, TipoCombustivel combustivel, double consumo) {
        this(placa, modelo, "Não informado", combustivel, 0.0, consumo, 4, 400.0);
    }

    /** Implementação do método abstrato do pai. */
    @Override
    public String getTipoVeiculo() { return "Carro"; }

    /**
     * Implementação do método abstrato do pai: define o manual de manutenção do carro.
     * Itens criados só com o nome são "checagens simples" (apto/reprovado manualmente).
     * Itens com (nome, km, meses) são periódicos: vencem sozinhos ao atingir um dos limites.
     */
    @Override
    public void carregarSistemasDoManual() {
        limparSistemas(); // evita duplicar sistemas se o método for chamado de novo

        SistemaVeiculo freio = new SistemaVeiculo("Sistema de Freio");
        freio.add(new ItemChecagem("Pastilhas de freio"));
        freio.add(new ItemChecagem("Discos de freio"));
        adicionarSistema(freio);

        SistemaVeiculo eletrico = new SistemaVeiculo("Sistema Elétrico");
        eletrico.add(new ItemChecagem("Bateria"));
        eletrico.add(new ItemChecagem("Luzes"));
        eletrico.add(new ItemChecagem("Motor de arranque"));
        adicionarSistema(eletrico);

        SistemaVeiculo rodas = new SistemaVeiculo("Rodas e Pneus");
        rodas.add(new ItemChecagem("Pneus"));
        rodas.add(new ItemChecagem("Alinhamento"));
        rodas.add(new ItemChecagem("Balanceamento"));
        adicionarSistema(rodas);

        // Itens periódicos: (nome, limite em km, limite em meses)
        SistemaVeiculo periodicos = new SistemaVeiculo("Manutenções Periódicas");
        periodicos.add(new ItemChecagem("Óleo do Motor", 10000, 12));
        periodicos.add(new ItemChecagem("Filtro de Óleo", 10000, 12));
        periodicos.add(new ItemChecagem("Filtro de Ar", 15000, 12));
        periodicos.add(new ItemChecagem("Correia dentada", 60000, 48));
        adicionarSistema(periodicos);
    }

    /** Custeavel: o desgaste de um carro é uma taxa fixa por km. */
    @Override
    public double calcularCustoManutencaoPorKm() { return CUSTO_DESGASTE_POR_KM; }

    /** Custeavel: custo da viagem = combustível (método do pai) + desgaste proporcional aos km. */
    @Override
    public double calcularCustoViagem(double km) {
        return calcularCustoCombustivel(km) + km * calcularCustoManutencaoPorKm();
    }

    /** Sobrescreve o gancho do pai para incluir dados do carro no relatório. */
    @Override
    protected void exibirDadosEspecificos() {
        System.out.printf("Portas: %d | Porta-malas: %.0f L%n", numeroPortas, volumePortaMalas);
    }

    // ===== Getters e setters com validação =====

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) {
        if (numeroPortas < 2) { // um carro tem no mínimo 2 portas
            throw new IllegalArgumentException("Número de portas inválido.");
        }
        this.numeroPortas = numeroPortas;
    }

    public double getVolumePortaMalas() { return volumePortaMalas; }
    public void setVolumePortaMalas(double volumePortaMalas) {
        if (volumePortaMalas < 0) {
            throw new IllegalArgumentException("Volume do porta-malas inválido.");
        }
        this.volumePortaMalas = volumePortaMalas;
    }
}
