package com.mycompany.gestorfrotaentrega.modelo;

/*Cumprimento do encapsulamento 1.1 e 1.2 atributos (string, int
*float)*/
public class Carga {
    private final String descricao;
    private final double peso; 

    public Carga (String descricao, float peso){
        if(descricao == null || descricao.trim().isEmpty()){
            throw new IllegalArgumentException("A Descrição de carga é obrigatória");   
        }
        if(peso <= 0){
            throw new IllegalArgumentException("O peso da carga precisa ser válido (n > 0)"); 
        }
        this.descricao = descricao.trim();
        this.peso = peso;
    }

    public String getDescricao(){
        return descricao;
    }
    
    public double getPeso(){
        return peso;
    }
}

