package com.mycompany.gestorfrotaentrega.modelo; // 9: pacote 'modelo' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

// 11: exceção criada para o sistema (estende Exception); levantada em Frota.atribuirEntrega() e tratada em FrotaApp.dialogoEntrega()
public class EntregaInvalidaException extends Exception {
    // 3: construtor declarado
    public EntregaInvalidaException (String mensagem){
        super(mensagem);
    }
    
}
