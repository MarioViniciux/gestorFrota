package com.mycompany.gestorfrotaentrega.modelo;

import java.util.List;

/**
 * REQUISITO DO PROFESSOR (interface):
 * Contrato de tudo aquilo que pode ser CHECADO, BLOQUEADO e CONSERTADO.
 *
 * Fica separada de Custeavel de propósito (princípio da segregação de interfaces):
 * cada contrato trata de uma única responsabilidade (custo x manutenção).
 */
public interface Manutenivel {

    /** @return true somente se TODOS os itens de checagem estiverem aptos. */
    boolean isApto();

    /**
     * Registra uso (rodagem) nos itens de manutenção periódica.
     *
     * @param km    quilômetros rodados
     * @param meses meses decorridos
     */
    void rodar(double km, int meses);

    /**
     * Busca um item de checagem pelo nome.
     *
     * @return o item, ou null se não existir
     */
    ItemChecagem buscarItem(String nomeItem);

    /** @return lista dos itens reprovados ou vencidos (vazia se estiver tudo ok). */
    List<ItemChecagem> listarItensPendentes();

    /** Imprime no console o relatório completo de checagem. */
    void exibirRelatorioChecagem();

    /**
     * Método DEFAULT: já implementado, evita repetir "!isApto()" pelo código.
     *
     * @return true se existe algo a consertar
     */
    default boolean precisaManutencao() {
        return !isApto();
    }
}
