package com.mycompany.gestorfrotaentrega.modelo;

/**
 * Item individual de checagem/manutenção de um sistema do veículo.
 */
public class ItemChecagem {

    private String nome;
    private boolean apto;
    private double kilometrosDeUso;
    private int tempoDeUsoMeses;
    private Double limiteKm;      // pode ser vazio (null) 
    private Integer limiteMeses;  // pode ser vazio (null)

    /** Checagem simples de estado (sem controle de limites). */
    public ItemChecagem(String nome) {
        this.nome = nome;
        this.apto = true;
        this.kilometrosDeUso = 0;
        this.tempoDeUsoMeses = 0;
    }

    /** Item de manutenção periódica, com limites por km e por tempo. */
    public ItemChecagem(String nome, double limiteKm, int limiteMeses) {
        this(nome);
        this.limiteKm = limiteKm;
        this.limiteMeses = limiteMeses;
    }

    // Altera a aptidão do item individualmente.
    public void marcarApto(boolean statusApto) {
        this.apto = statusApto;
    }

    /*
      Acumula o uso do item e o reprova automaticamente
      ao atingir limiteKm ou limiteMeses.
    */
    public void registrarUso(double km, int meses) {
        if (km < 0 || meses < 0) {
            System.out.println("Aviso: valores de uso menores que zero, ignorados em '" + nome + "'.");
            return;
        }
        this.kilometrosDeUso += km;
        this.tempoDeUsoMeses += meses;

        boolean venceuKm = limiteKm != null && kilometrosDeUso >= limiteKm;
        boolean venceuTempo = limiteMeses != null && tempoDeUsoMeses >= limiteMeses;
        
        if ((venceuKm || venceuTempo) && apto) {
            apto = false;
            String motivo = venceuKm && venceuTempo ? "quilometragem e tempo"
                    : venceuKm ? "quilometragem" : "tempo";
            System.out.println(" atenção " + nome + " venceu por " + motivo
                    + " (" + String.format("%.0f", kilometrosDeUso) + " km / "
                    + tempoDeUsoMeses + " meses). Manutenção necessária.");
        }
    }

    /** Zera o uso acumulado e restaura a aptidão. */
    public void concluirManutencao() {
        this.kilometrosDeUso = 0;
        this.tempoDeUsoMeses = 0;
        this.apto = true;
    }

    /** Indica se o item é de manutenção periódica. */
    public boolean possuiLimites() {
        return limiteKm != null || limiteMeses != null;
    }

    // Getters e Setters
    public String getNome() {
        return nome; 
    }
    
    public void setNome(String nome) {
        this.nome = nome; 
    }

    public boolean isApto() {
        return apto; 
    }
    
    public void setApto(boolean apto) {
        this.apto = apto; 
    }

    public double getKilometrosDeUso() {
        return kilometrosDeUso; 
    }
    
    public void setKilometrosDeUso(double kilometrosDeUso) { 
        this.kilometrosDeUso = kilometrosDeUso; 
    }

    public int getTempoDeUsoMeses() {
        return tempoDeUsoMeses; 
    }
    public void setTempoDeUsoMeses(int tempoDeUsoMeses) {
        this.tempoDeUsoMeses = tempoDeUsoMeses; 
    }

    public Double getLimiteKm() {
        return limiteKm; 
    }
    
    public void setLimiteKm(Double limiteKm) {
        this.limiteKm = limiteKm; 
    }

    public Integer getLimiteMeses() {
        return limiteMeses; 
    }
    
    public void setLimiteMeses(Integer limiteMeses) {
        this.limiteMeses = limiteMeses; 
    }

    @Override
    public String toString() {
        String status = apto ? "APTO" : "REPROVADO";
        if (!possuiLimites()) {
            return String.format("%-42s [%s]", nome, status);
        }
        return String.format("%-42s [%s] uso: %.0f/%.0f km | %d/%d meses",
                nome, status, kilometrosDeUso, limiteKm, tempoDeUsoMeses, limiteMeses);
    }
}
