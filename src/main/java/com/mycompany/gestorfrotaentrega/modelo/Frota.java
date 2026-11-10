package com.mycompany.gestorfrotaentrega.modelo;

import java.util.ArrayList;
import java.util.List;

public class Frota {
    private final ArrayList<Veiculo> veiculos = new ArrayList<>();
    private final ArrayList<Motorista> motoristas = new ArrayList<>();
    private final ArrayList<Entrega> entregas = new ArrayList<>();

    public boolean adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null || buscarVeiculo(veiculo.getPlaca()) != null) {
            return false;
        }
        return veiculos.add(veiculo);
    }

    public boolean adicionarMotorista(Motorista motorista) {
        if (motorista == null) {
            return false;
        }
        for (Motorista m : motoristas) {
            if (m.getCpf().equals(motorista.getCpf())) {
                return false;
            }
        }
        return motoristas.add(motorista);
    }

    public Veiculo buscarVeiculo(String placa) {
        if (placa == null) {
            return null;
        }
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa.trim())) {
                return v;
            }
        }
        return null;
    }

    public boolean removerVeiculo(String placa) {
        Veiculo v = buscarVeiculo(placa);
        return v != null && veiculos.remove(v);
    }

    public boolean removerMotorista(String cpf) {
        return cpf != null && motoristas.removeIf(m -> m.getCpf().equals(cpf));
    }

    public List<Veiculo> getVeiculos() {
        return List.copyOf(veiculos);
    }

    public List<Motorista> getMotoristas() {
        return List.copyOf(motoristas);
    }

    public List<Entrega> getEntregas() {
        return List.copyOf(entregas);
    }

    public static double capacidadeKg(Veiculo veiculo) {
        if (veiculo instanceof Caminhao caminhao) {
            return caminhao.getCmt() - caminhao.getTara();
        }
        if (veiculo instanceof Carro carro) {
            return carro.getCapacidadeCargaKg();
        }
        if (veiculo instanceof Moto moto) {
            return moto.getCapacidadeCargaKg();
        }
        return 0;
    }

    public static List<CategoriaCNH> categoriasExigidas(Veiculo veiculo) {
        if (veiculo instanceof Caminhao) {
            return List.of(CategoriaCNH.C, CategoriaCNH.E);
        }
        if (veiculo instanceof Moto) {
            return List.of(CategoriaCNH.A);
        }
        return List.of(CategoriaCNH.B);
    }

    public boolean podeDirigir(Motorista motorista, Veiculo veiculo) {
        if (!motorista.estaDisponivel()) {
            return false;
        }
        for (CategoriaCNH c : categoriasExigidas(veiculo)) {
            if (motorista.possuirCategoria(c)) {
                return true;
            }
        }
        return false;
    }

    public String motivoInaptidao(Veiculo veiculo, double pesoKg) {
        if (!veiculo.isApto()) {
            return "veículo em manutenção/bloqueado";
        }
        if (pesoKg > capacidadeKg(veiculo)) {
            return "carga acima da capacidade (" + capacidadeKg(veiculo) + " kg)";
        }
        return null;
    }

    public List<Veiculo> veiculosAptos(double pesoKg) {
        List<Veiculo> aptos = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (motivoInaptidao(v, pesoKg) == null) {
                aptos.add(v);
            }
        }
        return aptos;
    }

    public List<Motorista> motoristasHabilitados(Veiculo veiculo) {
        List<Motorista> habilitados = new ArrayList<>();
        for (Motorista m : motoristas) {
            if (podeDirigir(m, veiculo)) {
                habilitados.add(m);
            }
        }
        return habilitados;
    }

    public double orcamento(Veiculo veiculo, double distanciaKm) {
        return veiculo.calcularCustoViagem(distanciaKm) * (1 + Entrega.TAXA_ADMINISTRATIVA);
    }

    public Entrega atribuirEntrega(Veiculo veiculo, Motorista motorista, String descricao,
                                   double pesoKg, double distanciaKm) throws EntregaInvalidaException {
        if (pesoKg <= 0 || distanciaKm <= 0) {
            throw new EntregaInvalidaException("Peso e distância devem ser maiores que zero.");
        }
        String motivo = motivoInaptidao(veiculo, pesoKg);
        if (motivo != null) {
            throw new EntregaInvalidaException("Veículo " + veiculo.getPlaca() + ": " + motivo + ".");
        }
        if (!podeDirigir(motorista, veiculo)) {
            throw new EntregaInvalidaException("Motorista " + motorista.getNome()
                    + " indisponível ou sem CNH adequada para " + veiculo.getTipoVeiculo() + ".");
        }
        Entrega entrega = new Entrega(veiculo, motorista, descricao, pesoKg, distanciaKm,
                orcamento(veiculo, distanciaKm));
        veiculo.rodar(distanciaKm, 0); 
        entregas.add(entrega);
        return entrega;
    }
}
