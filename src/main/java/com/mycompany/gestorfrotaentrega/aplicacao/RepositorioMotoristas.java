/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorfrotaentrega.aplicacao;

import com.mycompany.gestorfrotaentrega.modelo.CategoriaCNH;
import com.mycompany.gestorfrotaentrega.modelo.Motorista;

import java.time.LocalDate;
import java.util.Set;
import java.util.ArrayList;
import java.io.IOException;

public class RepositorioMotoristas extends Repositorio<Motorista>{
    
    public RepositorioMotoristas(){
        super("motoristas.txt");
    }
    
    @Override
    protected Motorista converter(String linha){
        
        String[] dados = linha.split(";");
        
        //Recupera as categorias da CNH
        Set<CategoriaCNH> tiposCNH = Motorista.lerCategorias(dados[6]);
                
        //Cria um objeto Motorista com os dados fornecidos
        return new Motorista(
                dados[0],
                Integer.parseInt(dados[1]),
                dados[2],
                dados[3],
                dados[4],
                dados[5],
                tiposCNH,
                LocalDate.parse(dados[7]),
                Boolean.parseBoolean(dados[8])
        );
    }

    public void cadastrar(Motorista novoMotorista) throws IOException{
        
        ArrayList<Motorista> motoristas = carregar();
        
        for(Motorista motorista : motoristas) {
            
            if(motorista.getCpf().equals(novoMotorista.getCpf())){
                throw new IllegalArgumentException("Ja existe motorista com esse CPF");
            }
            
            //Verifica se o numero da CNH ja esta cadastrada
            if(motorista.getNumeroCNH().equals(novoMotorista.getNumeroCNH())){
                throw new IllegalArgumentException("Ja existe um motorista com esse numero de CNH");
           }
         }
        // Adiciona o novo motorista na lista
        motoristas.add(novoMotorista);

        // Salva a lista atualizada no arquivo
        salvar(motoristas);
    }

    /** Remove o motorista do arquivo pelo CPF. @return true se existia e foi removido */
    public boolean remover(String cpf) throws IOException {
        ArrayList<Motorista> motoristas = carregar();
        boolean removido = motoristas.removeIf(m -> m.getCpf().equals(cpf));
        if (removido) {
            salvar(motoristas);
        }
        return removido;
    }
}
