/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.modelo;

import java.time.LocalDate;

public class Motorista extends Pessoa{
    
    private String numeroCNH;
    private CategoriaCNH tipoCNH;
    private LocalDate validadeCNH;
    private boolean ativo;

    public Motorista(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail, String pNumeroCNH, CategoriaCNH pTipoCNH, LocalDate pValidadeCNH, boolean pAtivo) {
        super(pNome, pIdade, pCpf, pTelefone, pEmail);
        
        setNumeroCNH(pNumeroCNH);
        setCategoriaCNH(pTipoCNH);
        setValidadeCNH(pValidadeCNH);
        setAtivo(pAtivo); 
    }
    
    //SETTERS
    public void setNumeroCNH(String numeroCNH){
        if(numeroCNH == null || !numeroCNH.matches("\\d{11}")){
            throw new IllegalArgumentException("A CNH deve conte 11 digitos.");
        }
        this.numeroCNH = numeroCNH;
    }
    
    public void setCategoriaCNH(CategoriaCNH tipoCNH){
        if(tipoCNH == null){
            throw new IllegalArgumentException("A categoria da CNH e obrigatoria");
        }
        this.tipoCNH = tipoCNH;
    }
    
    public void setValidadeCNH(LocalDate validadeCNH){
        if(validadeCNH == null){
            throw new IllegalArgumentException("A validade da CNH e obrigatoria");
        }
        this.validadeCNH = validadeCNH;
    }
    
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }
    
    //GETTERS
    
    public String getNumeroCNH(){
        return this.numeroCNH;
    }
    
    public CategoriaCNH getTipoCNH(){
        return this.tipoCNH;
    }
    
    public LocalDate getValidadeCNH(){
        return this.validadeCNH;
    }
    
    public boolean isAtivo(){
        return this.ativo;
    }
    
    //VERIFICACAO DE CNH VENCIDA
    public boolean cnhVencida(){
        if(this.validadeCNH == null){
            throw new IllegalStateException("Validade da CNH nao informada.");
        }     
        //o isBefore retorna falso se a data passar da validade
        return this.validadeCNH.isBefore(LocalDate.now());
        
    }
     
    //VERIFICA SE O MOTORISTA ESTA ATIVO E TEM TEM CNH VALIDA
    public boolean estaDisponivel(){
        return this.ativo && !cnhVencida();
    }
}

