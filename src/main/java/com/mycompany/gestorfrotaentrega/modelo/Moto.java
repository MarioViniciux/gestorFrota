package com.mycompany.gestorfrotaentrega.modelo;

/**
 * REQUISITO DO PROFESSOR (herança):
 * Moto ESPECIALIZA Veiculo, voltada para entregas rápidas em área urbana.
 * Tem desgaste menor por km e um manual de manutenção com itens próprios (corrente, coroa...).
 */
public class Moto extends Veiculo {

    // Custo de desgaste por km (menor que o do carro). Valor ilustrativo.
    private static final double CUSTO_DESGASTE_POR_KM = 0.08;

    private int cilindradas;     // em cc (centímetros cúbicos)
    private boolean possuiBau;   // indica se a moto tem baú para carga
    private double capacidadeCargaKg; // carga máxima permitida, em kg

    /** Capacidade assumida quando não informada (cadastros antigos e construtor simples). */
    public static final double CAPACIDADE_PADRAO_KG = 30;

    /** REQUISITO (sobrecarga de construtores) - versão COMPLETA. */
    public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,
                double potencia, double consumo, int cilindradas, boolean possuiBau,
                double capacidadeCargaKg) {
        super(placa, modelo, motor, combustivel, potencia, consumo); // dados comuns no pai
        setCilindradas(cilindradas);   // valida
        this.possuiBau = possuiBau;
        setCapacidadeCargaKg(capacidadeCargaKg);
        carregarSistemasDoManual();    // checklist específico de moto
    }

    /** Versão sem capacidade de carga: assume {@link #CAPACIDADE_PADRAO_KG}. */
    public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,
                double potencia, double consumo, int cilindradas, boolean possuiBau) {
        this(placa, modelo, motor, combustivel, potencia, consumo, cilindradas, possuiBau,
                CAPACIDADE_PADRAO_KG);
    }

    /**
     * REQUISITO (sobrecarga de construtores) - versão SIMPLES.
     * Assume gasolina, motor/potência não informados e moto com baú.
     */
    public Moto(String placa, String modelo, int cilindradas, double consumo) {
        this(placa, modelo, "Não informado", TipoCombustivel.GASOLINA, 0.0, consumo, cilindradas, true);
    }

    @Override
    public String getTipoVeiculo() { return "Moto"; }

    /** Manual de manutenção da moto (menos sistemas que o caminhão, intervalos mais curtos). */
    @Override
    public void carregarSistemasDoManual() {
        limparSistemas();

        SistemaVeiculo freio = new SistemaVeiculo("Sistema de Freio");
        freio.add(new ItemChecagem("Pastilhas de freio", 15000, 18));
        freio.add(new ItemChecagem("Disco de freio", 40000, 48));
        adicionarSistema(freio);

        SistemaVeiculo eletrico = new SistemaVeiculo("Sistema Elétrico");
        eletrico.add(new ItemChecagem("Bateria", 40000, 24));
        eletrico.add(new ItemChecagem("Luzes", 50000, 36));
        adicionarSistema(eletrico);

        SistemaVeiculo rodas = new SistemaVeiculo("Rodas e Pneus");
        rodas.add(new ItemChecagem("Pneu dianteiro", 25000, 36));
        rodas.add(new ItemChecagem("Pneu traseiro", 15000, 24));
        adicionarSistema(rodas);

        // Itens periódicos: (nome, limite em km, limite em meses). Óleo de moto vence cedo (3.000 km).
        SistemaVeiculo periodicos = new SistemaVeiculo("Manutenções Periódicas");
        periodicos.add(new ItemChecagem("Óleo do Motor", 3000, 6));
        periodicos.add(new ItemChecagem("Kit relação (corrente e coroa)", 20000, 24));
        periodicos.add(new ItemChecagem("Filtro de Ar", 12000, 12));
        adicionarSistema(periodicos);
    }

    /** Custeavel: taxa fixa de desgaste por km. */
    @Override
    public double calcularCustoManutencaoPorKm() { return CUSTO_DESGASTE_POR_KM; }

    /** Custeavel: combustível + desgaste (mesma estrutura do carro, com taxa própria). */
    @Override
    public double calcularCustoViagem(double km) {
        return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());
    }

    /** Persistência: cilindradas e baú; a carga máxima vai no fim da linha. */
    @Override
    protected String camposEspecificos() { return cilindradas + ";" + possuiBau; }

    @Override
    protected String camposFinais() { return String.valueOf(capacidadeCargaKg); }

    /** Gancho do pai: acrescenta cilindradas e baú ao relatório. */
    @Override
    protected void exibirDadosEspecificos() {
        System.out.printf("Cilindradas: %d cc | Baú: %s | Carga máx.: %.0f kg%n",
                cilindradas, possuiBau ? "sim" : "não", capacidadeCargaKg);
    }

    // ===== Getters e setters =====

    public int getCilindradas() { return cilindradas; }
    public void setCilindradas(int cilindradas) {
        if (cilindradas <= 0) {
            throw new IllegalArgumentException("Cilindradas deve ser maior que zero.");
        }
        this.cilindradas = cilindradas;
    }

    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
    public void setCapacidadeCargaKg(double capacidadeCargaKg) {
        if (capacidadeCargaKg <= 0) {
            throw new IllegalArgumentException("Capacidade de carga deve ser maior que zero.");
        }
        this.capacidadeCargaKg = capacidadeCargaKg;
    }

    // Para boolean, o padrão Java é "is" no getter.
    public boolean isPossuiBau() { return possuiBau; }
    public void setPossuiBau(boolean possuiBau) { this.possuiBau = possuiBau; }
}
