package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

import java.util.Locale;

// 8: implementa a interface Persistivel
public class Entrega implements Persistivel{
    
    public static final double TAXA_ADMINISTRATIVA = 0.10; // 1.5: constante primitiva com valor na declaração, usada em Frota.orcamento()
    
    // 1.1: atributos private (encapsulamento)
    // 5: agregação — Veiculo e Motorista existem independentemente da Entrega e são recebidos pelo construtor
    private final Veiculo veiculo;
    private final Motorista motorista;
    // 4: composição — a Carga é criada dentro do construtor da Entrega e não existe fora dela
    private final Carga carga; 
    private final double distanciaKm;
    private final double valor; 
    
    // 3: construtor declarado | 5: construtor da agregação, usado em Frota.atribuirEntrega()
    public Entrega(Veiculo veiculo, Motorista motorista, String descricaoCarga, double pesoCarga, double distanciaKm, double valor) throws EntregaInvalidaException{
    
        if(veiculo == null || motorista == null){
            throw new IllegalArgumentException("Veículo e motorista são obrigatórios.");
        }
        if(distanciaKm <= 0){
            throw new IllegalArgumentException("Valor inválido, a distância precisa ser maior que zero");
        }
        this.veiculo = veiculo;
        this.motorista = motorista;
        // 4: composição — o construtor de Carga é usado aqui
        this.carga = new Carga(descricaoCarga, pesoCarga);
        this.distanciaKm = distanciaKm;
        this.valor = valor;
    }
    
    //2.1 Métodos (argumentos variados) varargs(...)//
    public double calcularCustosAdicionais( double... custosExtras){
        double totalCustos = 0;
        for(double custo : custosExtras){
            totalCustos += custo;
        }
        return totalCustos;
    }
    
     public Veiculo getVeiculo() {
        return veiculo;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public Carga getCarga() {
        return carga;
    }
    
    public double getDistanciaKm() {
        return distanciaKm;
    }

    public double getValor() {
        return valor;
    }
    
    // 2.2: método estático (não é o main), necessário e usado em FrotaApp para exibir valores em R$
    public static String formatarValor(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
    
    // 13: converte a entrega numa linha de texto para gravar em historico.txt | 8: método da interface Persistivel
    @Override
    public String paraLinha() {
        return String.join(";", veiculo.getPlaca(), motorista.getCpf(), carga.getDescricao(),
                String.valueOf(carga.getPeso()), String.valueOf(distanciaKm), String.valueOf(valor));
    }
    
    @Override
    public String toString() {
        return carga.getDescricao() + " (" + carga.getPeso() + " kg, " + distanciaKm + " km) - "
                + veiculo.getTipoVeiculo() + " " + veiculo.getPlaca() + " - " + motorista.getNome()
                + " - " + formatarValor(valor);
    }
}
