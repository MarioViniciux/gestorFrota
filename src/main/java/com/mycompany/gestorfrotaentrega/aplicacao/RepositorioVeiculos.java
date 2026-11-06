package com.mycompany.gestorfrotaentrega.aplicacao;

import com.mycompany.gestorfrotaentrega.modelo.Veiculo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gerencia os veículos cadastrados (em memória), usando a PLACA como chave única.
 *
 * Trabalha sempre com o tipo abstrato Veiculo (POLIMORFISMO): a mesma lista guarda
 * Carro, Caminhao e Moto, e o repositório não precisa saber qual é qual.
 * Obs.: diferente de RepositorioMotoristas, este não grava em arquivo (não há IOException).
 */
public class RepositorioVeiculos {

    // Placa -> veículo. LinkedHashMap mantém a ORDEM DE CADASTRO ao listar,
    // e a busca por chave é rápida (não precisa varrer a lista inteira).
    private final Map<String, Veiculo> veiculos = new LinkedHashMap<>();

    /**
     * Cadastra o veículo.
     *
     * @throws IllegalArgumentException se o veículo for nulo ou a placa já estiver cadastrada
     *         (mesmo padrão usado no cadastro de motoristas)
     */
    public void cadastrar(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo nulo não pode ser cadastrado.");
        }
        // A placa já vem normalizada (maiúscula, sem espaços) do construtor de Veiculo.
        if (veiculos.containsKey(veiculo.getPlaca())) {
            throw new IllegalArgumentException(
                    "Já existe um veículo cadastrado com a placa " + veiculo.getPlaca() + ".");
        }
        veiculos.put(veiculo.getPlaca(), veiculo);
    }

    /**
     * Remove um veículo pela placa.
     *
     * @return true se existia e foi removido; false caso contrário
     */
    public boolean remover(String placa) {
        // Normaliza a placa informada igual ao construtor, para achar "abc1d23 " também.
        return placa != null && veiculos.remove(placa.trim().toUpperCase()) != null;
    }

    /** Busca pela placa (ignora maiúsculas e espaços). Retorna null se não encontrar. */
    public Veiculo buscarPorPlaca(String placa) {
        return placa == null ? null : veiculos.get(placa.trim().toUpperCase());
    }

    /** Devolve uma CÓPIA da lista: mexer nela não altera o repositório (encapsulamento). */
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos.values());
    }

    /** Veículos liberados para operar (todos os itens de checagem aptos). */
    public List<Veiculo> listarAptos() {
        List<Veiculo> aptos = new ArrayList<>();
        for (Veiculo v : veiculos.values()) {
            if (v.isApto()) {
                aptos.add(v);
            }
        }
        return aptos;
    }

    /** Veículos bloqueados, que precisam de manutenção (usa o método default de Manutenivel). */
    public List<Veiculo> listarBloqueados() {
        List<Veiculo> bloqueados = new ArrayList<>();
        for (Veiculo v : veiculos.values()) {
            if (v.precisaManutencao()) {
                bloqueados.add(v);
            }
        }
        return bloqueados;
    }

    /**
     * Filtra por subclasse. Uso: {@code listarPorTipo(Moto.class)} retorna List&lt;Moto&gt;.
     * O {@code <T extends Veiculo>} (generics) faz o retorno já vir com o tipo certo,
     * sem precisar de cast por quem chama.
     */
    public <T extends Veiculo> List<T> listarPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Veiculo v : veiculos.values()) {
            if (tipo.isInstance(v)) {          // o veículo é desse tipo (ou subtipo)?
                resultado.add(tipo.cast(v));   // converte com segurança para T
            }
        }
        return resultado;
    }

    /**
     * Soma o custo de uma viagem de {@code km} para a frota toda.
     * POLIMORFISMO: v.calcularCustoViagem(km) executa a fórmula de Carro, Moto ou Caminhao
     * conforme o objeto real, sem nenhum if/instanceof.
     */
    public double calcularCustoViagemFrota(double km) {
        double total = 0;
        for (Veiculo v : veiculos.values()) {
            total += v.calcularCustoViagem(km);
        }
        return total;
    }

    /** Quantidade de veículos cadastrados. */
    public int total() { return veiculos.size(); }
}
