/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.modelo;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

        
public class Motorista extends Pessoa{
    
    //ATRIBUTOS
    private String numeroCNH;
    private Set<CategoriaCNH> tipoCNH;
    private LocalDate validadeCNH;
    private boolean ativo;
    
    //CONSTRUTOR
    public Motorista(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail, String pNumeroCNH, Set<CategoriaCNH> pTipoCNH, LocalDate pValidadeCNH, boolean pAtivo) {
        super(pNome, pIdade, pCpf, pTelefone, pEmail);
        
        setNumeroCNH(pNumeroCNH);
        
        //Verifica se o conjunto de categorias foi informado e se possui categorias vaalidas
        if(pTipoCNH == null || pTipoCNH.isEmpty() || pTipoCNH.contains(null)){
            throw new IllegalArgumentException("O motorista deve ter pelo menos uma categoria valida.");
        }
        
        //Cria uma copia com as categorias informadas
        this.tipoCNH = new HashSet<>(pTipoCNH); 
        
        setValidadeCNH(pValidadeCNH);
        setAtivo(pAtivo); 
    }
    
    //SETTERS
    public void setNumeroCNH(String numeroCNH){
        
        //Verifica se a CNH tem 11 digitos
        if(numeroCNH == null || !numeroCNH.matches("\\d{11}")){
            throw new IllegalArgumentException("A CNH deve conte 11 digitos.");
        }
        this.numeroCNH = numeroCNH;
    }
    
    public void setValidadeCNH(LocalDate validadeCNH){
        
        //Verifica a validade da CNH
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
    
    public Set<CategoriaCNH> getTiposCNH(){
        return new HashSet<>(tipoCNH);
    }
    
    public LocalDate getValidadeCNH(){
        return this.validadeCNH;
    }
    
    public boolean isAtivo(){
        return this.ativo;
    }
    
    //Verificacao de CNH vencida
    public boolean cnhVencida(){
        if(this.validadeCNH == null){
            throw new IllegalStateException("Validade da CNH nao informada.");
        }     
        //o isBefore retorna falso se a data passar da validade
        return this.validadeCNH.isBefore(LocalDate.now());
        
    }
     
    //Verifica se o motorista esta ativo e tem CNH valida
    public boolean estaDisponivel(){
        return this.ativo && !cnhVencida();
    }
    
    //GERENCIAMENTO DE CATEGORIAS
    
    public void adicionarCategoria(CategoriaCNH categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria invalida.");
        }
        //Adiciona categoria ao conjunto
        tipoCNH.add(categoria);
    }
    
    public void removerCategoria(CategoriaCNH categoria){
        if(categoria == null){
            throw new IllegalStateException("Categoria invalida.");
        }
        
        //Impede que o motorista fique sem nenhuma categoria
        if(tipoCNH.size() == 1 && tipoCNH.contains(categoria)){     
            throw new IllegalArgumentException("O motorista deve possuir pelo menos uma categoria.");
        }
        
        //Remove a categoria informada
        tipoCNH.remove(categoria);
    }

    public boolean possuirCat(CategoriaCNH categoria){
        //Verifica se o motorista tem a categoria informada
        return tipoCNH.contains(categoria);                    //retorna true ou false
    }
    
}