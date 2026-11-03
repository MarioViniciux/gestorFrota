/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.aplicacao;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.gestorfrotaentrega.modelo.Persistivel;

public abstract class Repositorio<T extends Persistivel> {
    private final Path arquivo;
    
    protected Repositorio(String nomeArquivo) {
        this.arquivo = Path.of(nomeArquivo);
    }
    
    protected abstract T converter(String linha);
    
    public void salvar(List<T> itens) throws IOException {
        List<String> linhas = new ArrayList<>();
        
        for (T item : itens) {
            linhas.add(item.paraLinha());
        }
        
        Files.write(arquivo, linhas);
    }
    
    public ArrayList<T> carregar() throws IOException {
        ArrayList<T> itens = new ArrayList<>();
        
        if (!Files.exists(arquivo)) {
            return itens;
        }
        
        for (String linha : Files.readAllLines(arquivo)) {
            if (linha.isBlank()) {
                continue;
            }
            
            try {
                itens.add(converter(linha));
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {}
        }
        
        return itens;
    }
}
 