/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

import java.time.LocalDate;
import java.util.Set;
import java.util.EnumSet;

        
// 6.2: classe filha, não possui exibirDados() | 7.3: subtipo concreto de Pessoa (pai e filho concretos)
// 8: implementa a interface Persistivel
public class Motorista extends Pessoa implements Persistivel{
    
    //ATRIBUTOS
    // 1.1: atributos private (encapsulamento)
    private String numeroCNH;
    // 1.3: atributo baseado na enumeração CategoriaCNH, criada para o sistema
    private Set<CategoriaCNH> tiposCNH;
    private LocalDate validadeCNH;
    private boolean ativo;
    
    //CONSTRUTOR
    // 3: construtor declarado (usa super(...) de Pessoa)
    public Motorista(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail, String pNumeroCNH, Set<CategoriaCNH> pTipoCNH, LocalDate pValidadeCNH, boolean pAtivo) {
        super(pNome, pIdade, pCpf, pTelefone, pEmail);
        
        setNumeroCNH(pNumeroCNH);
        
        //Verifica se o conjunto de categorias foi informado e se possui categorias vaalidas
        if(pTipoCNH == null || pTipoCNH.isEmpty() || pTipoCNH.contains(null)){
            throw new IllegalArgumentException("O motorista deve ter pelo menos uma categoria valida.");
        }
        
        //Cria uma copia das categorias utilizando EnumSet
        this.tiposCNH = EnumSet.copyOf(pTipoCNH); 
        
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
        return EnumSet.copyOf(tiposCNH);
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
        tiposCNH.add(categoria);
    }
    
    public void removerCategoria(CategoriaCNH categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria invalida.");
        }
        
        //Impede que o motorista fique sem nenhuma categoria
        if(tiposCNH.size() == 1 && tiposCNH.contains(categoria)){     
            throw new IllegalArgumentException("O motorista deve possuir pelo menos uma categoria.");
        }
        
        //Remove a categoria informada
        tiposCNH.remove(categoria);
    }

    public boolean possuirCategoria(CategoriaCNH categoria){
        //Verifica se o motorista tem a categoria informada
        return tiposCNH.contains(categoria);                    //retorna true ou false
    }
    
    // 13: converte o motorista numa linha de texto para gravar em motoristas.txt | 8: método da interface Persistivel
    @Override
    public String paraLinha(){
        
        //Constroi uma sequencia de texto
        StringBuilder categoriasTexto = new StringBuilder();
        
        //Percorre todasa as categorias de motorista
        for(CategoriaCNH categoria : tiposCNH){
            if(categoriasTexto.length()>0){
                categoriasTexto.append(",");
            }
        categoriasTexto.append(categoria.name());
        }
        
        //Junta todos os dados do motorista em uma unica linha
       return getNome() + ";" +
               getIdade() + ";" +
               getCpf() + ";" +
               getTelefone() + ";" + 
               getEmail() + ";" + 
               numeroCNH + ";" + 
               categoriasTexto + ";" + 
               validadeCNH + ";" +
               ativo;
        }
    
        // 2.2: método estático (não é o main), usado em RepositorioMotoristas.converter()
        //Converte o texto das categorias em um conjunto de categorias CNH
        public static Set<CategoriaCNH>lerCategorias(String texto){
            
            //Cria um conjunto EnumSet inicialmente vazio para armazenar as categorias
            Set<CategoriaCNH> categorias = EnumSet.noneOf(CategoriaCNH.class);
            
            //Separa as categorias utilizando uma virgula
            String[] partes = texto.split(",");
            
            //Percorre cada categoria encontrada no texto
            for(String parte : partes){
                
                //Converte o texto para uma constante do enum CategoriaCNH
                categorias.add(CategoriaCNH.valueOf(parte));
            }
            
            //Retorna o conjunto de categorias convertido
            return categorias;
        }
}