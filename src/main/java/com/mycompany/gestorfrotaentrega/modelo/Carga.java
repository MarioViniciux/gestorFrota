package com.mycompany.gestorfrotaentrega.modelo;

/*Cumprimento do encapsulamento 1.1 e 1.2 atributos (string, int
*float)*/
public class Carga {
private int n_rastreio;
private String descricao;
private float peso; 

public Carga (String descricao, int n_rastreio, float peso){
    setDescricao (descricao);
    setN_rastreio (n_rastreio);
    setPeso (peso);
}

public String getDescricao(){
    return descricao;
}

//Permite alterar a descrição após a criação//
public void setDescricao(String descricao){
    if(descricao == null || descricao.trim().isEmpty()){
        throw new IllegalArgumentException("A Descrição de carga é obrigatória");   
    }
    this.descricao = descricao.trim();
}

public int getN_rastreio( int n_rastreio){
    return n_rastreio;
}

//Permite alterar o número de rastreio após a criação//
public void setN_rastreio(int n_rastreio){
    if(n_rastreio <= 0){
        throw new IllegalArgumentException("O número de rastreio precisa ser válido (n > 0)");
    }
    this.n_rastreio = n_rastreio;
}

public float getPeso(){
    return peso;
}

//Permite alterar o peso após a criação//
public void setPeso(float peso){
    if(peso <= 0){
        throw new IllegalArgumentException("O peso da carga precisa ser válido (n > 0)"); 
    }
    this.peso = peso;
}

    
}

