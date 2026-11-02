package com.mycompany.gestorfrotaentrega;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupa os itens de checagem de um sistema do veículo (freio, elétrico etc.).
 */
public class SistemaVeiculo {

    private String nomeSistema;
    private List<ItemChecagem> itens;

    public SistemaVeiculo(String nomeSistema) {
        this.nomeSistema = nomeSistema;
        this.itens = new ArrayList<>();
    }

    public void add(ItemChecagem item) {
        this.itens.add(item);
    }

    /** Retorna false se qualquer item estiver reprovado ou vencido. */
    public boolean isSistemaApto() {
        for (ItemChecagem item : itens) {
            if (!item.isApto()) {
                return false;
            }
        }
        return true;
    }

    public String getNomeSistema() {
        return nomeSistema; 
    }

    public List<ItemChecagem> getItens() {
        return itens; 
    }
}
