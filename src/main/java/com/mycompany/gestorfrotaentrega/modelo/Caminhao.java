package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/**
 * REQUISITO DO PROFESSOR (herança):
 * Caminhao ESPECIALIZA Veiculo. Os atributos de carga (CMT, tara, PBTC, volume do baú)
 * saíram da classe genérica Veiculo, pois só fazem sentido para caminhões.
 */
// 10: classe concreta da classe abstrata Veiculo (implementa todos os métodos abstratos)
public class Caminhao extends Veiculo {

    // Custo de desgaste por km = base + (valor por eixo x número de eixos).
    // Quanto mais eixos, mais pneus e freios para desgastar. Valores ilustrativos.
    private static final double CUSTO_BASE_POR_KM = 0.60;
    private static final double CUSTO_POR_EIXO_POR_KM = 0.10;

    // 1.1: atributos private (encapsulamento)
    private double volumeBau;    // capacidade do baú em m³
    private double cmt;          // Capacidade Máxima de Tração, em kg
    private final double tara;   // REQUISITO (final): peso do veículo vazio, não muda nunca
    private double pbtc;         // Peso Bruto Total Combinado = tara + carga atual (kg)
    private int numeroEixos;     // influencia o custo de manutenção

    /**
     * REQUISITO (sobrecarga de construtores) - versão COMPLETA.
     */
    // 3: construtor declarado | 7.1: polimorfismo de sobrecarga (2 construtores)
    public Caminhao(String placa, String modelo, String motor, TipoCombustivel combustivel,
                    double potencia, double consumo, double volumeBau, double cmt,
                    double tara, int numeroEixos) {
        super(placa, modelo, motor, combustivel, potencia, consumo); // dados comuns no pai

        // Regra de negócio: o caminhão precisa ter tara positiva e CMT maior que a tara,
        // senão não sobraria capacidade de carga.
        if (tara <= 0 || cmt <= tara) {
            throw new IllegalArgumentException("Tara deve ser positiva e menor que a CMT.");
        }
        this.tara = tara;   // atributo final: só pode ser atribuído aqui, uma única vez
        this.pbtc = tara;   // sem carga, o peso bruto é igual à tara
        this.cmt = cmt;
        setVolumeBau(volumeBau);
        setNumeroEixos(numeroEixos);
        carregarSistemasDoManual();
    }

    /**
     * REQUISITO (sobrecarga de construtores) - versão SIMPLES.
     * Assume diesel, 3 eixos (6x4), motor e potência não informados.
     */
    // 7.1: polimorfismo de sobrecarga de construtor
    public Caminhao(String placa, String modelo, double consumo, double volumeBau,
                    double cmt, double tara) {
        this(placa, modelo, "Não informado", TipoCombustivel.DIESEL, 0.0, consumo,
                volumeBau, cmt, tara, 3);
    }

    @Override
    public String getTipoVeiculo() { return "Caminhão"; }

    /**
     * Sistemas definidos no manual de manutenção técnica do caminhão (o mesmo que já
     * existia na classe Veiculo original). Itens com (nome, km, meses) são periódicos:
     * são reprovados automaticamente ao atingir um dos dois limites.
     */
    // 10: implementação do método abstrato carregarSistemasDoManual() de Veiculo
    @Override
    public void carregarSistemasDoManual() {
        limparSistemas();

        SistemaVeiculo freio = new SistemaVeiculo("Sistema de Freio");
        freio.add(new ItemChecagem("Lonas de freio", 80000, 18));
        freio.add(new ItemChecagem("Secador de ar", 100000, 24));
        adicionarSistema(freio);

        SistemaVeiculo eletrico = new SistemaVeiculo("Sistema Elétrico");
        eletrico.add(new ItemChecagem("Baterias", 100000, 24));
        eletrico.add(new ItemChecagem("Fusíveis e relés", 200000, 60));
        eletrico.add(new ItemChecagem("Motor de arranque", 300000, 72));
        eletrico.add(new ItemChecagem("Diodo", 300000, 72));
        eletrico.add(new ItemChecagem("Luzes", 100000, 36));
        eletrico.add(new ItemChecagem("Transparência das lanternas", 150000, 48));
        eletrico.add(new ItemChecagem("Controle remoto", 200000, 60));
        eletrico.add(new ItemChecagem("Alarme", 200000, 60));
        adicionarSistema(eletrico);

        SistemaVeiculo rodas = new SistemaVeiculo("Rodas e Pneus");
        rodas.add(new ItemChecagem("Pneu", 80000, 36));
        rodas.add(new ItemChecagem("Rodas", 300000, 96));
        rodas.add(new ItemChecagem("Alinhamento", 20000, 12));
        rodas.add(new ItemChecagem("Balanceamento", 20000, 12));
        adicionarSistema(rodas);

        SistemaVeiculo combustivelSis = new SistemaVeiculo("Sistema de Combustível");
        combustivelSis.add(new ItemChecagem("Bomba de combustível", 250000, 60));
        combustivelSis.add(new ItemChecagem("Boia", 250000, 72));
        adicionarSistema(combustivelSis);

        SistemaVeiculo arrefecimento = new SistemaVeiculo("Sistema de Arrefecimento");
        arrefecimento.add(new ItemChecagem("Sistema de arrefecimento", 100000, 24));
        adicionarSistema(arrefecimento);

        SistemaVeiculo acessorio = new SistemaVeiculo("Acessórios");
        acessorio.add(new ItemChecagem("Palheta do limpador", 30000, 12));
        adicionarSistema(acessorio);

        // Óleos: (nome, limite em km, limite em meses)
        SistemaVeiculo lubrificacao = new SistemaVeiculo("Lubrificação, Filtros e Manutenções Periódicas");
        lubrificacao.add(new ItemChecagem("Óleo do Motor", 40000, 12));
        lubrificacao.add(new ItemChecagem("Óleo da Caixa de Mudança", 40000, 24));
        lubrificacao.add(new ItemChecagem("Óleo do Eixo Traseiro", 40000, 24));
        lubrificacao.add(new ItemChecagem("Óleo da Direção Assistiva", 20000, 12));
        lubrificacao.add(new ItemChecagem("Óleo da Bomba para Basculação da Cabine", 20000, 12));
        adicionarSistema(lubrificacao);

        // Filtros: trocados a cada 10.000 km ou 6 meses
        SistemaVeiculo filtro = new SistemaVeiculo("Filtros");
        filtro.add(new ItemChecagem("Filtro de Ar", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Combustível", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Cabine", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Óleo", 10000, 6));
        adicionarSistema(filtro);

        SistemaVeiculo periodicoGeral = new SistemaVeiculo("Periódicos Gerais");
        periodicoGeral.add(new ItemChecagem("Correia de Transmissão", 30000, 18));
        periodicoGeral.add(new ItemChecagem("Limpeza do Ar Condicionado", 15000, 12));
        adicionarSistema(periodicoGeral);
    }

    /**
     * Atualiza o PBTC (tara + carga) e alerta se ultrapassar a CMT.
     * O alerta apenas avisa: o PBTC é atualizado mesmo com excesso, e a decisão
     * de liberar ou não a viagem fica para quem usa a classe (veja isExcessoDePeso()).
     *
     * @param pesoCargaKilos peso da carga em kg (valor negativo é ignorado)
     */
    // 7.1: polimorfismo de sobrecarga (carregarVeiculo com 1 e com 2 parâmetros)
    public void carregarVeiculo(double pesoCargaKilos) {
        if (pesoCargaKilos < 0) {
            System.out.println("Aviso: peso de carga negativo ignorado.");
            return;
        }
        this.pbtc = tara + pesoCargaKilos; // substitui (não soma) a carga anterior
        if (pbtc > cmt) {
            System.out.printf("[ALERTA] PBTC (%.1f kg) ultrapassa a CMT (%.1f kg) em %.1f kg!%n",
                    pbtc, cmt, pbtc - cmt);
        }
    }

    /**
     * REQUISITO (sobrecarga de métodos): mesmo nome, dois parâmetros.
     * Carrega a carga principal mais uma carga extra, reaproveitando a versão de 1 parâmetro.
     */
    // 7.1: polimorfismo de sobrecarga (versão com carga extra)
    public void carregarVeiculo(double pesoCargaKilos, double cargaExtraKilos) {
        carregarVeiculo(pesoCargaKilos + cargaExtraKilos);
    }

    /** Carga atual transportada (PBTC menos a tara), em kg. */
    public double getCargaAtual() { return pbtc - tara; }

    /** true se o peso bruto atual ultrapassa a capacidade máxima de tração. */
    public boolean isExcessoDePeso() { return pbtc > cmt; }

    /** Custeavel: base + valor por eixo (ex.: 3 eixos = 0,60 + 3 x 0,10 = R$ 0,90/km). */
    @Override
    public double calcularCustoManutencaoPorKm() {
        return CUSTO_BASE_POR_KM + CUSTO_POR_EIXO_POR_KM * numeroEixos;
    }

    /** Custeavel: combustível (método do pai) + desgaste proporcional aos km. */
    @Override
    public double calcularCustoViagem(double km) {
        return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());
    }

    /** Persistência: volume do baú, CMT, tara e número de eixos. */
    @Override
    protected String camposEspecificos() {
        return String.join(";", String.valueOf(volumeBau), String.valueOf(cmt),
                String.valueOf(tara), String.valueOf(numeroEixos));
    }

    /** Gancho do pai: acrescenta eixos, baú e pesos (tara/PBTC/CMT) ao relatório. */
    @Override
    protected void exibirDadosEspecificos() {
        System.out.printf("Eixos: %d | Volume do baú: %.1f m³%n", numeroEixos, volumeBau);
        System.out.printf("Tara: %.1f kg | PBTC: %.1f kg | CMT: %.1f kg%n", tara, pbtc, cmt);
    }

    // ===== Getters e setters =====

    public double getVolumeBau() { return volumeBau; }
    public void setVolumeBau(double volumeBau) {
        if (volumeBau < 0) {
            throw new IllegalArgumentException("Volume do baú inválido.");
        }
        this.volumeBau = volumeBau;
    }

    public double getCmt() { return cmt; }
    public void setCmt(double cmt) {
        // Mesma regra do construtor: a CMT precisa continuar maior que a tara.
        if (cmt <= tara) {
            throw new IllegalArgumentException("A CMT deve ser maior que a tara.");
        }
        this.cmt = cmt;
    }

    public double getTara() { return tara; } // sem setter: atributo final
    public double getPbtc() { return pbtc; } // sem setter: só muda por carregarVeiculo()

    public int getNumeroEixos() { return numeroEixos; }
    public void setNumeroEixos(int numeroEixos) {
        if (numeroEixos < 2) {
            throw new IllegalArgumentException("Um caminhão precisa de ao menos 2 eixos.");
        }
        this.numeroEixos = numeroEixos;
    }
}
