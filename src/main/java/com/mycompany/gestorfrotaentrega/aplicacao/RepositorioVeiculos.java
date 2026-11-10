package com.mycompany.gestorfrotaentrega.aplicacao;

import com.mycompany.gestorfrotaentrega.modelo.Caminhao;
import com.mycompany.gestorfrotaentrega.modelo.Carro;
import com.mycompany.gestorfrotaentrega.modelo.ItemChecagem;
import com.mycompany.gestorfrotaentrega.modelo.Moto;
import com.mycompany.gestorfrotaentrega.modelo.SistemaVeiculo;
import com.mycompany.gestorfrotaentrega.modelo.TipoCombustivel;
import com.mycompany.gestorfrotaentrega.modelo.Veiculo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gerencia os veículos cadastrados (em memória), usando a PLACA como chave única.
 *
 * Trabalha sempre com o tipo abstrato Veiculo (POLIMORFISMO): a mesma lista guarda
 * Carro, Caminhao e Moto, e o repositório não precisa saber qual é qual.
 * Estende Repositorio<Veiculo> para ler/gravar veiculos.txt (Veiculo implementa Persistivel),
 * mas mantém os veículos em memória, num mapa, e só grava quando salvar() é chamado.
 */
public class RepositorioVeiculos extends Repositorio<Veiculo> {

    /** Arquivo de registro dos veículos (uma linha por veículo, campos separados por ';'). */
    public RepositorioVeiculos() {
        super("veiculos.txt");
    }

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

    // ===================== PERSISTÊNCIA EM ARQUIVO =====================

    /**
     * Grava todos os veículos cadastrados em veiculos.txt (sobrescreve o arquivo).
     * Usa o salvar(List) herdado de Repositorio, que chama Veiculo.paraLinha() (Persistivel).
     * carregar() também é herdado: lê o arquivo e devolve os veículos (não os cadastra aqui);
     * linhas em branco ou inválidas são ignoradas.
     */
    public void salvar() throws IOException {
        salvar(listarTodos());
    }

    private void restaurarEstado(Veiculo v, String km, String estado) {
        v.restaurarQuilometragem(Double.parseDouble(km));
        List<ItemChecagem> itens = new ArrayList<>();
        for (SistemaVeiculo sistema : v.getSistemas()) {
            itens.addAll(sistema.getItens());
        }
        String[] partes = estado.isEmpty() ? new String[0] : estado.split("\\|");
        if (partes.length != itens.size()) {
            return; // manual mudou desde a gravação: mantém os itens como novos
        }
        for (int i = 0; i < partes.length; i++) {
            String[] campos = partes[i].split(",");
            itens.get(i).setApto(Boolean.parseBoolean(campos[0]));
            itens.get(i).setKilometrosDeUso(Float.parseFloat(campos[1]));
            itens.get(i).setTempoDeUsoMeses(Integer.parseInt(campos[2]));
        }
    }

    /**
     * Gancho de Repositorio: transforma UMA linha do arquivo em Veiculo (Carro, Moto ou Caminhão),
     * restaurando também a quilometragem, o estado dos itens e a carga máxima.
     */
    @Override
    protected Veiculo converter(String linha) {
        String[] d = linha.split(";", -1);
        Veiculo v = criar(d);
        int pos = d[0].equals("Caminhão") ? 11 : 9; // primeiro campo após os dados específicos
        if (d.length >= pos + 2) {
            restaurarEstado(v, d[pos], d[pos + 1]);
        }
        if (d.length >= pos + 3 && !d[pos + 2].isBlank()) {
            double cap = Double.parseDouble(d[pos + 2]);
            if (v instanceof Carro c) c.setCapacidadeCargaKg(cap);
            else if (v instanceof Moto m) m.setCapacidadeCargaKg(cap);
        }
        return v;
    }

    private Veiculo criar(String[] d) {
        TipoCombustivel comb = TipoCombustivel.valueOf(d[4]);
        double potencia = Double.parseDouble(d[5]);
        double consumo = Double.parseDouble(d[6]);
        switch (d[0]) {
            case "Caminhão":
                return new Caminhao(d[1], d[2], d[3], comb, potencia, consumo,
                        Double.parseDouble(d[7]), Double.parseDouble(d[8]),
                        Double.parseDouble(d[9]), Integer.parseInt(d[10]));
            case "Carro":
                return new Carro(d[1], d[2], d[3], comb, potencia, consumo,
                        Integer.parseInt(d[7]), Double.parseDouble(d[8]));
            case "Moto":
                return new Moto(d[1], d[2], d[3], comb, potencia, consumo,
                        Integer.parseInt(d[7]), Boolean.parseBoolean(d[8]));
            default:
                throw new IllegalArgumentException("Tipo desconhecido: " + d[0]);
        }
    }
}
