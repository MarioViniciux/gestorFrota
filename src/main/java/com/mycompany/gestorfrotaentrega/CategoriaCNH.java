/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega;

public enum CategoriaCNH {
    A("Motocicletas"),
    B("Automoveis"),
    C("Veiculos de carga (PBT > 3500 kg)"),
    E("Veiculos de carga articulada (PBT > 6000 kg)");
    
//Esse final impede que a descrição de cada constante seja  alterado apos a inicializacao
    private final String descricao;
    
    CategoriaCNH(String pDescricao){
        this.descricao  = pDescricao;
    }
    public String getDescricao(){
        return this.descricao;
    }
}
