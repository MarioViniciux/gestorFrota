/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega;
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
        this.numeroCNH = numeroCNH;
    }
    
    public void setCategoriaCNH(CategoriaCNH tipoCNH){
        this.tipoCNH = tipoCNH;
    }
    
    public void setValidadeCNH(LocalDate validadeCNH){
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
        
        //o isBefore retorna falso se a data passar da validade
        return this.validadeCNH.isBefore(LocalDate.now());
        
    }
}

