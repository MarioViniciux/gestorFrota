package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/**
 * REQUISITO DO PROFESSOR (herança):
 * Carro ESPECIALIZA Veiculo ("é um" Veiculo). Herda placa, modelo, combustível, consumo,
 * relatório etc., e acrescenta apenas o que é próprio de um carro de entregas leves.
 */
// 10: classe concreta da classe abstrata Veiculo (implementa todos os métodos abstratos)
public class Carro extends Veiculo {

    // Custo de desgaste (pneus, freios, revisões) por km, em reais.
    // static final = constante compartilhada por todos os carros. Valor ilustrativo.
    private static final double CUSTO_DESGASTE_POR_KM = 0.15;

    // Atributos específicos do carro (privados: encapsulamento).
    // 1.1: atributos private (encapsulamento)
    private int numeroPortas;
    private double volumePortaMalas; // em litros
    private double capacidadeCargaKg; // carga máxima permitida, em kg

    /** Capacidade assumida quando não informada (cadastros antigos e construtor simples). */
    // 1.5: constante primitiva (double) com valor na declaração, usada em outra classe (FrotaApp)
    public static final double CAPACIDADE_PADRAO_KG = 500;

    /**
     * REQUISITO (sobrecarga de construtores) - versão COMPLETA.
     * super(...) chama o construtor da classe-pai para preencher os dados comuns.
     */
    // 3: construtor declarado | 7.1: polimorfismo de sobrecarga (3 construtores)
    public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,
                 double potencia, double consumo, int numeroPortas, double volumePortaMalas,
                 double capacidadeCargaKg) {
        super(placa, modelo, motor, combustivel, potencia, consumo); // sempre a 1ª linha
        setNumeroPortas(numeroPortas);           // usa o setter para validar
        setVolumePortaMalas(volumePortaMalas);
        setCapacidadeCargaKg(capacidadeCargaKg);
        carregarSistemasDoManual();              // monta o checklist deste tipo de veículo
    }

    /** Versão sem capacidade de carga: assume {@link #CAPACIDADE_PADRAO_KG}. */
    // 7.1: polimorfismo de sobrecarga de construtor
    public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,
                 double potencia, double consumo, int numeroPortas, double volumePortaMalas) {
        this(placa, modelo, motor, combustivel, potencia, consumo, numeroPortas, volumePortaMalas,
                CAPACIDADE_PADRAO_KG);
    }

    /**
     * REQUISITO (sobrecarga de construtores) - versão SIMPLES.
     * Assume motor não informado, potência 0, 4 portas e 400 L de porta-malas.
     */
    // 7.1: polimorfismo de sobrecarga de construtor
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
    // 10: implementação do método abstrato carregarSistemasDoManual() de Veiculo
    @Override
    public void carregarSistemasDoManual() {
        limparSistemas(); // evita duplicar sistemas se o método for chamado de novo

        SistemaVeiculo freio = new SistemaVeiculo("Sistema de Freio");
        freio.add(new ItemChecagem("Pastilhas de freio", 30000, 24));
        freio.add(new ItemChecagem("Discos de freio", 60000, 48));
        adicionarSistema(freio);

        SistemaVeiculo eletrico = new SistemaVeiculo("Sistema Elétrico");
        eletrico.add(new ItemChecagem("Bateria", 60000, 36));
        eletrico.add(new ItemChecagem("Luzes", 80000, 48));
        eletrico.add(new ItemChecagem("Motor de arranque", 150000, 96));
        adicionarSistema(eletrico);

        SistemaVeiculo rodas = new SistemaVeiculo("Rodas e Pneus");
        rodas.add(new ItemChecagem("Pneus", 40000, 48));
        rodas.add(new ItemChecagem("Alinhamento", 10000, 12));
        rodas.add(new ItemChecagem("Balanceamento", 10000, 12));
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
        return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());
    }

    /** Persistência: portas e porta-malas; a carga máxima vai no fim da linha. */
    @Override
    protected String camposEspecificos() { return numeroPortas + ";" + volumePortaMalas; }

    @Override
    protected String camposFinais() { return String.valueOf(capacidadeCargaKg); }

    /** Sobrescreve o gancho do pai para incluir dados do carro no relatório. */
    @Override
    protected void exibirDadosEspecificos() {
        System.out.printf("Portas: %d | Porta-malas: %.0f L | Carga máx.: %.0f kg%n",
                numeroPortas, volumePortaMalas, capacidadeCargaKg);
    }

    // ===== Getters e setters com validação =====

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) {
        if (numeroPortas < 2) { // um carro tem no mínimo 2 portas
            throw new IllegalArgumentException("Número de portas inválido.");
        }
        this.numeroPortas = numeroPortas;
    }

    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
    public void setCapacidadeCargaKg(double capacidadeCargaKg) {
        if (capacidadeCargaKg <= 0) {
            throw new IllegalArgumentException("Capacidade de carga deve ser maior que zero.");
        }
        this.capacidadeCargaKg = capacidadeCargaKg;
    }

    public double getVolumePortaMalas() { return volumePortaMalas; }
    public void setVolumePortaMalas(double volumePortaMalas) {
        if (volumePortaMalas < 0) {
            throw new IllegalArgumentException("Volume do porta-malas inválido.");
        }
        this.volumePortaMalas = volumePortaMalas;
    }
}
