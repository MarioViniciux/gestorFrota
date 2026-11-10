package com.mycompany.gestorfrotaentrega.modelo;

import java.util.Locale;

public class Entrega implements Persistivel{
    
    public static final double TAXA_ADMINISTRATIVA = 0.10;
    
    private final Veiculo veiculo;
    private final Motorista motorista;
    private final Carga carga; 
    private final double distanciaKm;
    private final double valor; 
    
    public Entrega(Veiculo veiculo, Motorista motorista, String destino, String descricaoCarga, float pesoCarga, double distanciaKm, double valor)throws EntregaInvalidaException{
    
        if(veiculo == null || motorista ==null){
            throw new EntregaInvalidaException("Veículo e motorista são obrigatórios.");
        }
        if(destino == null || destino.trim().isEmpty()){
            throw new EntregaInvalidaException("O destino não pode ser vazio");
        }
        if(distanciaKm <= 0){
            throw new EntregaInvalidaException("Valor inválido, a distância precisa ser maior que zero");
        }
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.carga = new Carga(descricaoCarga, pesoCarga);
        this.distanciaKm = distanciaKm;
        this.valor  = valor;
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
    
    public static String formatarValor(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
    
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
