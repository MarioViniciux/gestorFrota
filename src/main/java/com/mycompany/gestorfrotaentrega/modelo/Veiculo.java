package com.mycompany.gestorfrotaentrega.modelo;

import java.util.ArrayList;
import java.util.List;

public class Veiculo {

    private String tipo;
    private String motor;
    private String combustivel;
    private double potencia;
    private double volumeBau;
    private double cmt;   // Capacidade Máxima de Tração
    private double consumo;
    private double tara;  // peso do veículo sem carga
    private double pbtc;  // Peso Bruto Total Combinado
    private List<SistemaVeiculo> sistemas;

    
   
    public Veiculo(String tipo, String motor, String combustivel, double potencia,
                   double volumeBau, double cmt, double consumo, double tara) {
        this.tipo = tipo;
        this.motor = motor;
        this.combustivel = combustivel;
        this.potencia = potencia;
        this.volumeBau = volumeBau;
        this.cmt = cmt;
        this.consumo = consumo;
        this.tara = tara;
        this.pbtc = tara;
        this.sistemas = new ArrayList<>();
        carregarSistemasDoManual();
    }

    /** Monta os sistemas e itens definidos no manual de manutenção técnica. */
    public void carregarSistemasDoManual() {
        sistemas.clear();

        SistemaVeiculo freio = new SistemaVeiculo("Sistema de Freio");
        freio.add(new ItemChecagem("Lonas de freio"));
        freio.add(new ItemChecagem("Secador de ar"));
        sistemas.add(freio);

        SistemaVeiculo eletrico = new SistemaVeiculo("Sistema Elétrico");
        eletrico.add(new ItemChecagem("Baterias"));
        eletrico.add(new ItemChecagem("Fusíveis e relés"));
        eletrico.add(new ItemChecagem("Motor de arranque"));
        eletrico.add(new ItemChecagem("Diodo"));
        eletrico.add(new ItemChecagem("Luzes"));
        eletrico.add(new ItemChecagem("Transparência das lanternas"));
        eletrico.add(new ItemChecagem("Controle remoto"));
        eletrico.add(new ItemChecagem("Alarme"));
        sistemas.add(eletrico);

        SistemaVeiculo rodas = new SistemaVeiculo("Rodas e Pneus");
        rodas.add(new ItemChecagem("Pneu"));
        rodas.add(new ItemChecagem("Rodas"));
        rodas.add(new ItemChecagem("Alinhamento"));
        rodas.add(new ItemChecagem("Balanceamento"));
        sistemas.add(rodas);

        SistemaVeiculo combustivelSis = new SistemaVeiculo("Sistema de Combustível");
        combustivelSis.add(new ItemChecagem("Bomba de combustível"));
        combustivelSis.add(new ItemChecagem("Boia"));
        sistemas.add(combustivelSis);

        SistemaVeiculo arrefecimento = new SistemaVeiculo("Sistema de Arrefecimento e Acessórios");
        arrefecimento.add(new ItemChecagem("Sistema de arrefecimento"));
        sistemas.add(arrefecimento);
        
        SistemaVeiculo acessorio = new SistemaVeiculo("Acessórios");
        acessorio.add(new ItemChecagem("Palheta do limpador"));
        sistemas.add(acessorio);

        SistemaVeiculo lubrificacao = new SistemaVeiculo("Lubrificação, Filtros e Manutenções Periódicas");
        lubrificacao.add(new ItemChecagem("Óleo do Motor", 40000, 12));
        lubrificacao.add(new ItemChecagem("Óleo da Caixa de Mudança", 40000, 24));
        lubrificacao.add(new ItemChecagem("Óleo do Eixo Traseiro", 40000, 24));
        lubrificacao.add(new ItemChecagem("Óleo da Direção Assistiva", 20000, 12));
        lubrificacao.add(new ItemChecagem("Óleo da Bomba para Basculação da Cabine", 20000, 12));
        sistemas.add(lubrificacao);
        
        SistemaVeiculo filtro = new SistemaVeiculo("Filtros");
        filtro.add(new ItemChecagem("Filtro de Ar", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Combustível", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Cabine", 10000, 6));
        filtro.add(new ItemChecagem("Filtro de Óleo", 10000, 6));
        sistemas.add(filtro);
        
        SistemaVeiculo periodicogeral = new SistemaVeiculo("Periodicos Gerais");
        periodicogeral.add(new ItemChecagem("Correia de Transmissão", 30000, 18));
        periodicogeral.add(new ItemChecagem("Limpeza do Ar Condicionado", 15000, 12));
        sistemas.add(periodicogeral);
        
    }

    /** Atualiza o PBTC (tara + carga) e alerta se ultrapassar a CMT. */
    public void carregarVeiculo(double pesoCargaKilos) {
        if (pesoCargaKilos < 0) {
            System.out.println("Aviso: peso de carga negativo ignorado.");
            return;
        }
        this.pbtc = tara + pesoCargaKilos;
        if (pbtc > cmt) {
            System.out.printf("[ALERTA] PBTC (%.1f kg) ultrapassa a CMT (%.1f kg) em %.1f kg!%n",
                    pbtc, cmt, pbtc - cmt);
        }
    }

    /** O veículo só é apto se TODOS os itens de TODOS os sistemas estiverem aptos. */
    public boolean isApto() {
        for (SistemaVeiculo sistema : sistemas) {
            if (!sistema.isSistemaApto()) {
                return false;
            }
        }
        return true;
    }

    /** Incrementa a rodagem nos itens de manutenção periódica (os que possuem limites). */
    public void rodar(double km, int meses) {
        for (SistemaVeiculo sistema : sistemas) {
            for (ItemChecagem item : sistema.getItens()) {
                if (item.possuiLimites()) {
                    item.registrarUso(km, meses);
                }
            }
        }
    }

    /** Busca um item pelo nome (ignorando maiúsculas/minúsculas). Retorna null se não existir. */
    public ItemChecagem buscarItem(String nomeItem) {
        for (SistemaVeiculo sistema : sistemas) {
            for (ItemChecagem item : sistema.getItens()) {
                if (item.getNome().equalsIgnoreCase(nomeItem)) {
                    return item;
                }
            }
        }
        return null;
    }

    public void exibirRelatorioChecagem() {
        System.out.println("==================================================================");
        System.out.println("        RELATÓRIO DE CHECAGEM");
        System.out.println("==================================================================");
        System.out.printf("Tipo: %s | Motor: %s | Combustível: %s%n", tipo, motor, combustivel);
        System.out.printf("Potência: %.1f cv | Volume do baú: %.1f m³ | Consumo: %.1f km/l%n",
                potencia, volumeBau, consumo);
        System.out.printf("Tara: %.1f kg | PBTC: %.1f kg | CMT: %.1f kg%n", tara, pbtc, cmt);
        System.out.println("Situação geral: " + (isApto() ? "APTO" : "NÃO APTO (BLOQUEADO)"));
        System.out.println("------------------------------------------------------------------");

        for (SistemaVeiculo sistema : sistemas) {
            System.out.println("\n> " + sistema.getNomeSistema().toUpperCase()
                    + " - " + (sistema.isSistemaApto() ? "OK" : "PENDENTE"));
            for (ItemChecagem item : sistema.getItens()) {
                System.out.println("   - " + item);
            }
        }
        System.out.println("==================================================================\n");
    }

    // Getters e Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getCombustivel() { return combustivel; }
    public void setCombustivel(String combustivel) { this.combustivel = combustivel; }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) { this.potencia = potencia; }

    public double getVolumeBau() { return volumeBau; }
    public void setVolumeBau(double volumeBau) { this.volumeBau = volumeBau; }

    public double getCmt() { return cmt; }
    public void setCmt(double cmt) { this.cmt = cmt; }

    public double getConsumo() { return consumo; }
    public void setConsumo(double consumo) { this.consumo = consumo; }

    public double getTara() { return tara; }
    public void setTara(double tara) { this.tara = tara; }

    public double getPbtc() { return pbtc; }
    public void setPbtc(double pbtc) { this.pbtc = pbtc; }

    public List<SistemaVeiculo> getSistemas() { return sistemas; }
    public void setSistemas(List<SistemaVeiculo> sistemas) { this.sistemas = sistemas; }
}
