/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.aplicacao; // 9: pacote 'aplicacao' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.gestorfrotaentrega.modelo.Persistivel;

// 10: classe abstrata (concretas: RepositorioMotoristas e RepositorioVeiculos)
public abstract class Repositorio<T extends Persistivel> {
    // 1.1: atributo private (encapsulamento)
    private final Path arquivo;
    
    protected Repositorio(String nomeArquivo) {
        this.arquivo = Path.of(nomeArquivo);
    }
    
    // 10: método abstrato (não é construtor, get nem set)
    protected abstract T converter(String linha);
    
    // 13: grava os dados em arquivo de texto
    public void salvar(List<T> itens) throws IOException {
        List<String> linhas = new ArrayList<>();
        
        for (T item : itens) {
            linhas.add(item.paraLinha());
        }
        
        Files.write(arquivo, linhas);
    }
    
    // 13: recupera os dados gravados no arquivo
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
 