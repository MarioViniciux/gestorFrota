/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)


// 6.2: classe pai concreta com o método exibirDados() (Motorista não o sobrescreve) | 7.3: superclasse concreta de Motorista
public class Pessoa {
    
    //Atributos privados
    // 1.1: atributos private (encapsulamento)
    private String nome;
    private int idade;
    private final String cpf; // 6.1: atributo final em superclasse concreta (Pessoa)
    private String telefone;
    private String email;
    
    //Construtor
    // 3: construtor declarado (chamado por Motorista via super(...))
    public Pessoa(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail){
        setNome(pNome);
        setIdade(pIdade);
        if (pCpf == null || !pCpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("O cpf deve conter 11 digitos.");
        }
        this.cpf = pCpf;
        setTelefone(pTelefone);
        setEmail(pEmail);  
    }
    
    //Métodos setters
    
    public void setNome(String nome){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("O nome nao pode ser vazio.");
        }
        this.nome = nome;
    }
    
    public void setIdade(int idade){
        if(idade<=0){
            throw new IllegalArgumentException("A idade deve ser maior que zero.");
        }
        this.idade = idade;
    }
    
    public void setTelefone(String telefone){
        if(telefone == null || telefone.trim().isEmpty()){
            throw new IllegalArgumentException("O telefone nao pode ser vazio.");
        }
        this.telefone = telefone;
    }
    
    public void setEmail(String email){
        if(email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")){
            throw new IllegalArgumentException("Email invalido.");
        }
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
    // 6.2: método x do acoplamento dinâmico: definido só na classe pai e chamado a partir de Motorista (ver FrotaApp)
    public String exibirDados(){
        return "Nome: " + this.nome +
               "\nIdade: " + this.idade +
               "\nCPF: " + this.cpf +
               "\nTelefone: " + this.telefone +
               "\nEmail: " + this.email;
    }
} 