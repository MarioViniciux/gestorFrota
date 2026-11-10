package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/**
 * Item individual de checagem/manutenção de um sistema do veículo.
 */
public class ItemChecagem {

    // 1.1: atributos private (encapsulamento)
    // 1.2: na mesma classe: String (nome), int (tempoDeUsoMeses) e float (kilometrosDeUso)
    private String nome;
    private boolean apto;
    private float kilometrosDeUso;
    private int tempoDeUsoMeses;
    private Double limiteKm;      // pode ser vazio (null) 
    private Integer limiteMeses;  // pode ser vazio (null)

    /** Checagem simples de estado (sem controle de limites). */
    // 3: construtor declarado | 7.1: polimorfismo de sobrecarga (construtores com 1 e com 3 parâmetros)
    public ItemChecagem(String nome) {
        this.nome = nome;
        this.apto = true;
        this.kilometrosDeUso = 0;
        this.tempoDeUsoMeses = 0;
    }

    /** Item de manutenção periódica, com limites por km e por tempo. */
    // 7.1: polimorfismo de sobrecarga de construtor
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
        // 7.2: polimorfismo de coerção — cast explícito (float) de uma expressão double, sem função de conversão
        // Coerção explícita (cast): o km chega como double, mas o atributo é float.
        this.kilometrosDeUso = (float) (this.kilometrosDeUso + km);
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

    /**
     * Desgaste do item em % (0 a 100+): o maior entre o uso por km e o uso por tempo.
     * Itens sem limites (checagem simples) não têm desgaste mensurável: devolve -1.
     */
    public double percentualDesgaste() {
        if (!possuiLimites()) {
            return -1;
        }
        double porKm = limiteKm != null && limiteKm > 0 ? kilometrosDeUso * 100.0 / limiteKm : 0;
        double porTempo = limiteMeses != null && limiteMeses > 0 ? tempoDeUsoMeses * 100.0 / limiteMeses : 0;
        return Math.max(porKm, porTempo);
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

    public float getKilometrosDeUso() {
        return kilometrosDeUso;
    }

    public void setKilometrosDeUso(float kilometrosDeUso) {
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
