/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

// 1.3: enumeração criada para o sistema, usada em Motorista e Frota
public enum CategoriaCNH {
    A("Veiculos de duas ou tres rodas"),
    B("Automoveis de ate 3500 kg"),
    C("Veiculos de transporte de carga acima de 3500 kg"),
    D("Veiculos de transporte de passageiros"),
    E("Veiculos de carga articulada (PBT > 6000 kg)");
    
//Esse 'final' impede que a descrição seja reatribuída apos a inicializacao
    private final String descricao;
    
    CategoriaCNH(String pDescricao){
        this.descricao  = pDescricao;
    }
    public String getDescricao(){
        return this.descricao;
    }
}
