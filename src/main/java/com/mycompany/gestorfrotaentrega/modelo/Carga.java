package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

/* 1.1: encapsulamento (atributos private final) */
public class Carga {
    // 1.1: atributos private (encapsulamento)
    private final String descricao;
    private final double peso; 

    // 3: construtor declarado | 4: composição — usado dentro do construtor de Entrega (a Carga só existe como parte da Entrega)
    public Carga (String descricao, double peso){
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

