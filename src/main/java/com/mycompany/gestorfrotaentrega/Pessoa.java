/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega;


public class Pessoa {
    
    //Atributos privados
    private String nome;
    private int idade;
    private String cpf;
    private String telefone;
    private String email;
    
    //Construtor
    public Pessoa(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail){
        setNome(pNome);
        setIdade(pIdade);
        setCpf(pCpf);
        setTelefone(pTelefone);
        setEmail(pEmail);  
    }
    
    //Métodos setters
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setIdade(int idade){
        if(idade<0){
            throw new IllegalArgumentException("A idade nao pode ser negativa.");
        }
        this.idade = idade;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    //Métodos getters
    
    public String getNome(){
        return this.nome;
    }
    
    public int getIdade(){
        return this.idade;
    }
    
    public String getCpf(){
        return this.cpf;
    }
    
    public String getTelefone(){
        return this.telefone;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    //Método que exibe os dados coletados
    public String exibirDados(){
        return "Nome: " + this.nome +
               "\nIdade: " + this.idade +
               "\nCPF: " + this.cpf +
               "\nTelefone: " + this.telefone +
               "\nEmail: " + this.email;
    }
} 