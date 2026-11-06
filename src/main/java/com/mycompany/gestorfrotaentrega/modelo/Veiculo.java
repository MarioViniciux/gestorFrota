package com.mycompany.gestorfrotaentrega.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * REQUISITO DO PROFESSOR (classe abstrata + herança):
 * Veiculo concentra tudo o que é COMUM a Carro, Caminhao e Moto, e deixa ABSTRATO
 * o que muda de um tipo para outro. Por ser abstrata, não pode ser instanciada
 * (não existe "new Veiculo(...)"): só as filhas concretas podem ser criadas.
 *
 * Também implementa as interfaces Custeavel e Manutenivel. Como a classe é abstrata,
 * ela pode deixar alguns métodos dessas interfaces sem corpo, para as filhas implementarem.
 */
public abstract class Veiculo implements Custeavel, Manutenivel {

    // ===================== ATRIBUTOS =====================

    // REQUISITO (atributo final): a placa identifica o veículo e NUNCA muda depois de criado.
    // Por ser final, só pode receber valor no construtor e não tem setter.
    private final String placa;

    // REQUISITO (encapsulamento): todos os atributos são PRIVATE. O acesso de fora é feito
    // apenas por getters/setters, e os setters validam os valores recebidos.
    private String modelo;               // ex.: "Volvo FH 540 6x4"
    private String motor;                // descrição do motor
    private TipoCombustivel combustivel; // REQUISITO (enum): tipo de combustível, não mais String
    private double potencia;             // potência em cv
    private double consumo;              // km rodados por unidade de combustível (km/l, km/kWh...)
    private double quilometragem;        // odômetro: soma de tudo que o veículo já rodou

    // Lista de sistemas (freio, elétrico, pneus...). Cada sistema agrupa itens de checagem.
    // É final porque a lista em si nunca é trocada; só seu conteúdo muda.
    private final List<SistemaVeiculo> sistemas = new ArrayList<>();

    // ===================== CONSTRUTORES =====================

    /**
     * REQUISITO (sobrecarga de construtores) - versão COMPLETA.
     * Valida a placa aqui e delega o restante para os setters, que já validam cada campo.
     */
    public Veiculo(String placa, String modelo, String motor, TipoCombustivel combustivel,
                   double potencia, double consumo) {
        // Placa é obrigatória: não aceita null nem texto vazio/só espaços.
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("A placa é obrigatória.");
        }
        // Normaliza: remove espaços e coloca em maiúsculas ("abc1d23 " vira "ABC1D23").
        // Assim, a busca e a checagem de duplicidade não dependem de como o usuário digitou.
        this.placa = placa.trim().toUpperCase();

        // Os setters aplicam as validações (modelo obrigatório, consumo > 0, etc.).
        setModelo(modelo);
        setMotor(motor);
        setCombustivel(combustivel);
        setPotencia(potencia);
        setConsumo(consumo);

        this.quilometragem = 0; // veículo novo no sistema começa com odômetro zerado
    }

    /**
     * REQUISITO (sobrecarga de construtores) - versão REDUZIDA.
     * Chama a versão completa via this(...), preenchendo motor e potência com valores padrão.
     */
    public Veiculo(String placa, String modelo, TipoCombustivel combustivel, double consumo) {
        this(placa, modelo, "Não informado", combustivel, 0.0, consumo);
    }

    // ===================== MÉTODOS ABSTRATOS =====================
    // Sem corpo: TODA classe filha concreta é obrigada a implementá-los.

    /** Nome do tipo do veículo ("Carro", "Caminhão", "Moto"), usado em relatórios. */
    public abstract String getTipoVeiculo();

    /**
     * Monta os sistemas e itens do manual de manutenção. Cada tipo de veículo tem
     * um manual diferente, por isso o método é abstrato.
     */
    public abstract void carregarSistemasDoManual();

    // Os dois métodos abaixo vêm da interface Custeavel e continuam abstratos aqui.
    // O @Override + abstract deixa explícito que a decisão fica para as filhas.

    /** Custo da viagem: a fórmula muda conforme o tipo (eixos, cilindrada, etc.). */
    @Override
    public abstract double calcularCustoViagem(double km);

    /** Custo de manutenção por km: valor diferente para carro, moto e caminhão. */
    @Override
    public abstract double calcularCustoManutencaoPorKm();

    // ===================== CÁLCULO DE CUSTOS (comuns a todos) =====================

    /**
     * Custo de combustível de uma viagem, usando o preço de referência do enum.
     * Delega para a versão sobrecarregada logo abaixo.
     */
    public double calcularCustoCombustivel(double km) {
        return calcularCustoCombustivel(km, combustivel.getPrecoReferencia());
    }

    /**
     * REQUISITO (sobrecarga de métodos): mesmo nome, parâmetros diferentes.
     * Esta versão permite informar o preço do combustível (ex.: preço real do posto).
     *
     * Fórmula: (km / consumo) * preço  ->  litros gastos x preço por litro.
     */
    public double calcularCustoCombustivel(double km, double precoPorUnidade) {
        validarKm(km);
        if (precoPorUnidade < 0) {
            throw new IllegalArgumentException("Preço do combustível não pode ser negativo.");
        }
        return (km / consumo) * precoPorUnidade;
    }

    /**
     * REQUISITO (sobrecarga de métodos): viagem COM pedágio.
     * Reaproveita calcularCustoViagem(km), que é abstrato: cada tipo calcula do seu jeito
     * e aqui apenas somamos o pedágio (polimorfismo).
     */
    public double calcularCustoViagem(double km, double pedagio) {
        if (pedagio < 0) {
            throw new IllegalArgumentException("Pedágio não pode ser negativo.");
        }
        return calcularCustoViagem(km) + pedagio;
    }

    /**
     * Validação reutilizada pelos cálculos. É "protected" para que as filhas também possam usar.
     */
    protected void validarKm(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Quilometragem não pode ser negativa.");
        }
    }

    // ===================== MANUTENÍVEL (implementação da interface) =====================

    /** Permite que as filhas montem sua lista de sistemas (protected: só subclasses usam). */
    protected void adicionarSistema(SistemaVeiculo sistema) {
        sistemas.add(sistema);
    }

    /** Esvazia a lista antes de recarregar o manual, evitando sistemas duplicados. */
    protected void limparSistemas() {
        sistemas.clear();
    }

    /** O veículo só é apto se TODOS os itens de TODOS os sistemas estiverem aptos. */
    @Override
    public boolean isApto() {
        for (SistemaVeiculo sistema : sistemas) {
            if (!sistema.isSistemaApto()) {
                return false; // basta um sistema pendente para bloquear o veículo
            }
        }
        return true;
    }

    /**
     * Soma a rodagem ao odômetro e repassa o uso aos itens de manutenção periódica.
     * Itens sem limites (ex.: "Luzes") são ignorados, pois não vencem por km/tempo.
     */
    @Override
    public void rodar(double km, int meses) {
        if (km < 0 || meses < 0) {
            System.out.println("Aviso: valores de rodagem negativos ignorados.");
            return; // não altera nada em caso de valor inválido
        }
        quilometragem += km;
        for (SistemaVeiculo sistema : sistemas) {
            for (ItemChecagem item : sistema.getItens()) {
                if (item.possuiLimites()) {
                    item.registrarUso(km, meses); // pode reprovar o item automaticamente
                }
            }
        }
    }

    /** Busca um item pelo nome, ignorando maiúsculas/minúsculas. Retorna null se não existir. */
    @Override
    public ItemChecagem buscarItem(String nomeItem) {
        for (SistemaVeiculo sistema : sistemas) {
            for (ItemChecagem item : sistema.getItens()) {
                if (item.getNome().equalsIgnoreCase(nomeItem)) {
                    return item;
                }
            }
        }
        return null; // percorreu tudo e não achou
    }

    /** Monta a lista de itens reprovados/vencidos, percorrendo todos os sistemas. */
    @Override
    public List<ItemChecagem> listarItensPendentes() {
        List<ItemChecagem> pendentes = new ArrayList<>();
        for (SistemaVeiculo sistema : sistemas) {
            for (ItemChecagem item : sistema.getItens()) {
                if (!item.isApto()) {
                    pendentes.add(item);
                }
            }
        }
        return pendentes;
    }

    /**
     * Gancho (hook) para POLIMORFISMO: por padrão não imprime nada. Cada filha sobrescreve
     * para mostrar seus dados específicos (portas, eixos, cilindradas...) dentro do relatório.
     */
    protected void exibirDadosEspecificos() { }

    /** Imprime o relatório completo: dados gerais, dados do tipo e situação de cada sistema. */
    @Override
    public void exibirRelatorioChecagem() {
        System.out.println("==================================================================");
        System.out.println("        RELATÓRIO DE CHECAGEM - " + getTipoVeiculo().toUpperCase());
        System.out.println("==================================================================");
        System.out.printf("Placa: %s | Modelo: %s | Motor: %s%n", placa, modelo, motor);
        System.out.printf("Combustível: %s | Potência: %.1f cv | Consumo: %.1f km/%s%n",
                combustivel, potencia, consumo, combustivel.getUnidade());
        System.out.printf("Quilometragem: %.0f km%n", quilometragem);
        exibirDadosEspecificos(); // chamada polimórfica: executa a versão da filha
        System.out.println("Situação geral: " + (isApto() ? "APTO" : "NÃO APTO (BLOQUEADO)"));
        System.out.println("------------------------------------------------------------------");

        for (SistemaVeiculo sistema : sistemas) {
            System.out.println("\n> " + sistema.getNomeSistema().toUpperCase()
                    + " - " + (sistema.isSistemaApto() ? "OK" : "PENDENTE"));
            for (ItemChecagem item : sistema.getItens()) {
                System.out.println("   - " + item); // usa ItemChecagem.toString()
            }
        }
        System.out.println("==================================================================\n");
    }

    /** Resumo de uma linha, ex.: "Moto [GHI7J89] Honda CG 160 - APTO". */
    @Override
    public String toString() {
        return String.format("%s [%s] %s - %s", getTipoVeiculo(), placa, modelo,
                isApto() ? "APTO" : "BLOQUEADO");
    }

    // ===================== GETTERS E SETTERS (encapsulamento com validação) =====================

    // Sem setter: atributo final, definido uma única vez no construtor.
    public String getPlaca() { return placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo é obrigatório.");
        }
        this.modelo = modelo.trim();
    }

    public String getMotor() { return motor; }
    // Motor é opcional: se vier null, usa o texto padrão.
    public void setMotor(String motor) { this.motor = (motor == null) ? "Não informado" : motor; }

    public TipoCombustivel getCombustivel() { return combustivel; }
    public void setCombustivel(TipoCombustivel combustivel) {
        if (combustivel == null) {
            throw new IllegalArgumentException("O tipo de combustível é obrigatório.");
        }
        this.combustivel = combustivel;
    }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) {
        if (potencia < 0) {
            throw new IllegalArgumentException("Potência não pode ser negativa.");
        }
        this.potencia = potencia;
    }

    public double getConsumo() { return consumo; }
    public void setConsumo(double consumo) {
        // Precisa ser > 0, pois é usado como divisor em calcularCustoCombustivel (evita divisão por zero).
        if (consumo <= 0) {
            throw new IllegalArgumentException("Consumo deve ser maior que zero.");
        }
        this.consumo = consumo;
    }

    // Sem setter: o odômetro só aumenta pelo método rodar().
    public double getQuilometragem() { return quilometragem; }

    // Devolve uma visão SOMENTE LEITURA: quem chama não consegue adicionar/remover sistemas
    // por fora, o que preserva o encapsulamento.
    public List<SistemaVeiculo> getSistemas() { return Collections.unmodifiableList(sistemas); }
}
