package com.mycompany.gestorfrotaentrega.aplicacao;

import com.mycompany.gestorfrotaentrega.modelo.Caminhao;
import com.mycompany.gestorfrotaentrega.modelo.CategoriaCNH;
import com.mycompany.gestorfrotaentrega.modelo.Carro;
import com.mycompany.gestorfrotaentrega.modelo.Entrega;
import com.mycompany.gestorfrotaentrega.modelo.EntregaInvalidaException;
import com.mycompany.gestorfrotaentrega.modelo.Frota;
import com.mycompany.gestorfrotaentrega.modelo.ItemChecagem;
import com.mycompany.gestorfrotaentrega.modelo.Moto;
import com.mycompany.gestorfrotaentrega.modelo.Motorista;
import com.mycompany.gestorfrotaentrega.modelo.Pessoa;
import com.mycompany.gestorfrotaentrega.modelo.SistemaVeiculo;
import com.mycompany.gestorfrotaentrega.modelo.TipoCombustivel;
import com.mycompany.gestorfrotaentrega.modelo.Veiculo;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class FrotaApp extends JFrame {
    static final Color BG = new Color(0xF4F6FA);
    static final Color SIDEBAR = new Color(0x111827);
    static final Color SIDEBAR_HOVER = new Color(0x1F2937);
    static final Color ACCENT = new Color(0x2563EB);
    static final Color TEXT = new Color(0x111827);
    static final Color MUTED = new Color(0x6B7280);
    static final Color OK = new Color(0x16A34A);
    static final Color WARN = new Color(0xD97706);
    static final Color BAD = new Color(0xDC2626);
    static final Font BASE = new Font("SansSerif", Font.PLAIN, 14);
    
    private static final Path HISTORICO = Path.of("historico.txt");
    private static final Path REMOVIDOS = Path.of("removidos.txt");
    
    private final Frota frota = new Frota();
    private final RepositorioVeiculos repoVeiculos = new RepositorioVeiculos();
    private final RepositorioMotoristas repoMotoristas = new RepositorioMotoristas();
    
    private Motorista ultimoMotoristaCadastrado;
    
    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);
    private final JLabel titulo = new JLabel();
    private final java.util.Map<String, JButton> navButtons = new java.util.LinkedHashMap<>();
    
    private final DefaultTableModel mdlVeiculos = modelo("Tipo", "Placa", "Modelo", "KM rodados", "Itens pendentes", "Status");
    private final DefaultTableModel mdlMotoristas = modelo("Nome", "CPF", "CNH", "Habilitado para", "Situação");
    private final DefaultTableModel mdlEntregas = modelo("Carga", "Peso (kg)", "Km", "Veículo", "Motorista", "Valor");
    private final DefaultTableModel mdlRemovidos = modelo("Data/hora", "Tipo", "Identificação", "Descrição", "Telefone", "E-mail");
    private JPanel dashboard;
    
    public static void main(String[] args) {
        System.setProperty("sun.java2x.xrender", "false");
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) { }
            UIManager.put("defaultFont", BASE);
            new FrotaApp().setVisible(true);
        });
    }
    
    public FrotaApp() {
        super("Gestão de Frota e Entregas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1180, 740);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);
        
        carregarDados();
        
        JPanel root = new JPanel(new BorderLayout());
        root.add(criarSidebar(), BorderLayout.WEST);
        
        JPanel direita = new JPanel(new BorderLayout());
        direita.setBackground(BG);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setForeground(TEXT);
        titulo.setBorder(new EmptyBorder(24, 28, 8, 28));
        direita.add(titulo, BorderLayout.NORTH);
        
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(8, 28, 24, 28));
        
        dashboard = new JPanel(new BorderLayout());
        dashboard.setOpaque(false);
        
        content.add(dashboard, "Painel");
        content.add(paginaVeiculos(), "Veículos");
        content.add(paginaMotoristas(), "motoristas");
        content.add(paginaEntregas(), "Entregas");
        content.add(paginaRemovidos(), "Removidos");
        
        direita.add(content, BorderLayout.CENTER);
        root.add(direita, BorderLayout.CENTER);
        setContentPane(root);
        
        atualizarTudo();
        ir("Painel");
    }
    
    private static String limpar(String t) {
        return t == null ? "" : t.replace(";", ",").replace("\r", "");
    }
    
    private void carregarDados() {
        try {
            for (Veiculo v : repoVeiculos.carregar()) {
                if (frota.adicionarVeiculo(v)) {
                    repoVeiculos.cadastrar(v);
                }
            }
            
            for (Motorista m : repoMotoristas.carregar()) {
                frota.adicionarMotorista(m);
            }
        } catch (IOException e) {
            aviso("Não foi possível ler os arquivos de dados: " + e.getMessage());
        }
    }
    
    private void registrarRemocao(String tipo, String identificador, String descricao, String telefone, String email, String detalhes) {
        String linha = String.join(";", java.time.LocalDateTime.now().withNano(0).toString(), tipo, limpar(identificador), limpar(descricao), limpar(telefone), limpar(email), limpar(detalhes).replace("\n", "\\n")) + System.lineSeparator();
        
        try {
            Files.writeString(REMOVIDOS, linhas, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            aviso("Erro ao gravar o registro de remoções: " + e.getMessage());
        }
    }
    
    private List<String[]> lerRemovidos() {
        List<String[]> registros = new ArrayList<>();
        
        try {
            if (Files.exists(REMOVIDOS)) {
                for (String linha : Files.readAllLines(REMOVIDOS)) {
                    String[] d = linha.split(";", -1);
                    
                    if (linha.isBlank() || d.length < 4) continue;
                    
                    String[] r = new String[]{"", "", "", "", "", "", ""};
                    System.arraycopy(d, 0, r, 0, Math.min(d.length, 7));
                    r[6] = r[6].replace("\\n", "\n");
                    registros.add(r);
                }
            }
        } catch (IOException e) {
            aviso("Erro ao ler o registro de remoções: " + e.getMessage());
        }
        
        return registros;
    }
    
    private static String detalhesMotorista(Motorista m) {
        return m.exibirDados() + "\nCNH: " + m.getNumeroCNH() + "\nCategorias: " + new TreeSet<>(m.getTiposCNH()) + "\nValidade da CNH: " + m.getValidadeCNH() + "\nSituação ao ser removido: " + (m.estaDisponivel() ? "Disponível" : "Indisponível");
    }
    
    private static String detalhesVeiculos(Veiculo v) {
        StringBuilder d = new StringBuilder();
        
        d.append("Tipo: ").append(v.getTipoVeiculo()).append("\nPlaca: ").append(v.getPlaca()).append("\nModelo: ").append(v.getModelo()).append("\nMotor: ").append(v.getMotor()).append("\nCombustivel: ").append(v.getCombustivel()).append("\nPotência: ").append(v.getPotencia()).append(String.format("\nQuilometragem: %.0f km", v.getQuilometragem()));
        
        if (v instanceof Caminhao c) {
            d.append(String.format("\nBaú: %.1f m³ | CMT: %.0f kg | Tara: %.0f kg | Eixos: %d", c.getVolumeBau(), c.getCmt(), c.getTara(), c.getNumeroEixos()));
        } else if (v instanceof Carro c) {
            d.append(String.format("\nPortas: %d | Porta-malas: %.0f L | Carga máx.: %.0f kg", c.getNumeroPortas(), c.getVolumePortaMalas(), c.getCapacidadeCargaKg()));
        } else if (v instanceof Moto mo) {
            d.append(String.format("\nCilindradas: %d cc | Baú: %s | Carga máx.: %.0f kg", mo.getCilindradas(), mo.isPossuiBau() ? "sim" : "não", mo.getCapacidadeCargaKg()));
        }
        
        List<ItemChecagem> pend = v.listarItensPendentes();
        d.append("\nItens pendentes de manutenção: ");
        
        if (pend.isEmpty()) d.append("nenhum");
        for (ItemChecagem i : pend) d.append("\n - ").append(i.getNome());
       
        return d.toString();
    }
    
    private void salvarVeiculos() {
        try {
            repoVeiculos.salvar();
        } catch (IOException e) {
            aviso("Erro ao salvar: " + e.getMessage());
        }
    }
    
    private void registrarHistorico(Entrega entrega) {
        try {
            Files.writeString(HISTORICO, entrega.paraLinha() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            aviso("Erro ao gravar o histórico: " + e.getMessage());
        }
    }
    
    private List<String[]> lerHistorico() {
        List<String[]> registros = new ArrayList<>();
        
        try {
            if (!Files.exists(HISTORICO)) {
                return registros;
            }
            
            for (String linha : Files.readAllLines(HISTORICO)) {
                String[] d = linha.split(";", -1);
                
                if (!linha.isBlank() && d.length >= 6) {
                    registros.add(d);
                }
            }
        } catch (IOException e) {
            aviso("Erro ao ler o histórico: " + e.getMessage());
        }
        
        return registros;
    }
    
    private static double valorDe(String[] registro) {
        try {
            return Double.parseDouble((registro[5]));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private String nomeMotorista(String cpf) {
        for (Motorista m : frota.getMotoristas()) {
            if (m.getCpf().equals(cpf)) {
                return m.getNome();
            }
        }
        
        return cpf;
    }
    
    private String descricaoVeiculo(String placa) {
        Veiculo v = frota.buscarVeiculo(placa);
        
        return (v == null ? "Veículo" : v.getTipoVeiculo()) + " " + placa;
    }
}
