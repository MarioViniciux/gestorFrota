package com.mycompany.gestorfrotaentrega.aplicacao; // 9: pacote 'aplicacao' (o projeto tem exatamente 2 pacotes: modelo e aplicacao)

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
    
    // 13: arquivos de texto onde o histórico de entregas e as remoções são guardados
    private static final Path HISTORICO = Path.of("historico.txt");
    private static final Path REMOVIDOS = Path.of("removidos.txt");
    
    // 1.1: atributos não estáticos private (encapsulamento)
    private final Frota frota = new Frota();
    private final RepositorioVeiculos repoVeiculos = new RepositorioVeiculos();
    private final RepositorioMotoristas repoMotoristas = new RepositorioMotoristas();
    
    // 6.2: acoplamento dinâmico — atributo declarado como Motorista (filha) e instanciado como Motorista em dialogoMotorista(); usa exibirDados(), definido só em Pessoa (pai)
    private Motorista ultimoMotoristaCadastrado;
    
    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);
    private final JLabel titulo = new JLabel();
    private final java.util.Map<String, JButton> navButtons = new java.util.LinkedHashMap<>();
    
    private final DefaultTableModel mdlVeiculos = modelo("Tipo", "Placa", "Modelo", "KM rodados", "Carga máx.", "Itens pendentes", "Status");
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
        content.add(paginaMotoristas(), "Motoristas");
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
    
    // 13: recupera veículos e motoristas gravados em arquivo ao iniciar o sistema e os usa na Frota
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
    
    // 13: grava a remoção em removidos.txt
    private void registrarRemocao(String tipo, String identificador, String descricao, String telefone, String email, String detalhes) {
        String linha = String.join(";", java.time.LocalDateTime.now().withNano(0).toString(), tipo, limpar(identificador), limpar(descricao), limpar(telefone), limpar(email), limpar(detalhes).replace("\n", "\\n")) + System.lineSeparator();
        
        try {
            Files.writeString(REMOVIDOS, linha, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            aviso("Erro ao gravar o registro de remoções: " + e.getMessage());
        }
    }
    
    // 13: recupera os registros de removidos.txt
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
    
    /** Capacidade máxima de carga em kg (caminhão: CMT menos a tara). */
    private static String cargaMaxima(Veiculo v) {
        if (v instanceof Caminhao c) return String.format("%.0f kg", c.getCmt() - c.getTara());
        if (v instanceof Carro c) return String.format("%.0f kg", c.getCapacidadeCargaKg());
        if (v instanceof Moto m) return String.format("%.0f kg", m.getCapacidadeCargaKg());
        return "-";
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
    
    // 13: grava os veículos em veiculos.txt
    private void salvarVeiculos() {
        try {
            repoVeiculos.salvar();
        } catch (IOException e) {
            aviso("Erro ao salvar: " + e.getMessage());
        }
    }
    
    // 13: grava a entrega em historico.txt
    private void registrarHistorico(Entrega entrega) {
        try {
            Files.writeString(HISTORICO, entrega.paraLinha() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            aviso("Erro ao gravar o histórico: " + e.getMessage());
        }
    }
    
    // 13: recupera o histórico de entregas de historico.txt (usado no painel e na tabela de entregas)
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
    
    private JComponent criarSidebar() {
        JPanel p = new JPanel();
        p.setBackground(SIDEBAR);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(230, 0));
        p.setBorder(new EmptyBorder(24, 14, 24, 14));

        JLabel logo = new JLabel("🚚  GFE");
        logo.setForeground(Color.WHITE);
        logo.setFont(logo.getFont().deriveFont(Font.BOLD, 22f));
        logo.setBorder(new EmptyBorder(0, 10, 28, 0));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(logo);

        for (String nome : new String[]{"Painel", "Veículos", "Motoristas", "Entregas", "Removidos"}) {
            JButton b = new JButton(nome);
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setForeground(new Color(0xD1D5DB));
            b.setBackground(SIDEBAR);
            b.setFont(BASE.deriveFont(Font.BOLD));
            b.setBorder(new EmptyBorder(0, 14, 0, 0));
            b.setFocusPainted(false);
            b.setContentAreaFilled(false);
            b.setOpaque(true);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addActionListener(e -> ir(nome));
            navButtons.put(nome, b);
            p.add(b);
            p.add(Box.createVerticalStrut(4));
        }
        p.add(Box.createVerticalGlue());

        JButton nova = botao("+ Nova entrega", ACCENT);
        nova.setAlignmentX(Component.LEFT_ALIGNMENT);
        nova.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        nova.addActionListener(e -> dialogoEntrega());
        p.add(nova);
        
        return p;
    }
    
    private void ir(String pagina) {
        cards.show(content, pagina);
        titulo.setText(pagina);
        navButtons.forEach((n, b) -> {
            boolean ativo = n.equals(pagina);
            b.setBackground(ativo ? SIDEBAR_HOVER : SIDEBAR);
            b.setForeground(ativo ? Color.WHITE : new Color(0xD1D5DB));
        });
    }
    
    private void montarDashboard() {
        dashboard.removeAll();
        List<Veiculo> vs = frota.getVeiculos();
        long manut = vs.stream().filter(Veiculo::precisaManutencao).count();
        List<String[]> historico = lerHistorico();
        double receita = 0;
        for (String[] r : historico) {
            receita += valorDe(r);
        }

        JPanel kpis = new JPanel(new GridLayout(1, 4, 16, 0));
        kpis.setOpaque(false);
        kpis.add(kpi("Veículos", String.valueOf(vs.size()), ACCENT));
        kpis.add(kpi("Motoristas", String.valueOf(frota.getMotoristas().size()), new Color(0x7C3AED)));
        kpis.add(kpi("Precisam de manutenção", String.valueOf(manut), manut > 0 ? WARN : OK));
        kpis.add(kpi("Receita em entregas", Entrega.formatarValor(receita), OK));
        kpis.setPreferredSize(new Dimension(0, 110));

        // 7.4: polimorfismo paramétrico — chamada com tipo genérico explícito ao método listarPorTipo
        // Polimorfismo paramétrico: o tipo genérico T é passado de forma explícita (<Carro>, <Moto>...).
        String porTipo = repoVeiculos.<Carro>listarPorTipo(Carro.class).size() + " carros · "
                + repoVeiculos.<Moto>listarPorTipo(Moto.class).size() + " motos · "
                + repoVeiculos.<Caminhao>listarPorTipo(Caminhao.class).size() + " caminhões";

        JPanel meio = new JPanel(new GridLayout(1, 2, 16, 0));
        meio.setOpaque(false);
        meio.add(cartao("Saúde da frota  (" + porTipo + ")", listaSaude(vs)));
        meio.add(cartao("Últimas entregas", listaEntregas(historico)));

        dashboard.add(kpis, BorderLayout.NORTH);
        JPanel c = new JPanel(new BorderLayout());
        c.setOpaque(false);
        c.setBorder(new EmptyBorder(16, 0, 0, 0));
        c.add(meio);
        dashboard.add(c, BorderLayout.CENTER);
        dashboard.revalidate();
        dashboard.repaint();
    }
    
    private static double saude(Veiculo v) {
        int total = 0;
        
        for (SistemaVeiculo s : v.getSistemas()) {
            total += s.getItens().size();
        }
        
        return total == 0 ? 1.0 : 1.0 - (double) v.listarItensPendentes().size() / total;
    }
    
    private JComponent listaSaude(List<Veiculo> vs) {
        JPanel l = new JPanel();
        l.setOpaque(false);
        l.setLayout(new BoxLayout(l, BoxLayout.Y_AXIS));
        
        if (vs.isEmpty()) {
            JLabel v = new JLabel("Nenhum veículo cadastrado.");
            v.setForeground(MUTED);
            l.add(v);
        }
        
        for (Veiculo v : vs) {
            double pct = saude(v);
            JPanel linha = new JPanel(new BorderLayout(10, 0));
            linha.setOpaque(false);
            linha.setBorder(new EmptyBorder(6, 0, 6, 0));
            linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            JLabel nome = new JLabel(v.getTipoVeiculo() + " " + v.getPlaca());
            nome.setPreferredSize(new Dimension(150, 20));
            Color cor = pct >= 1.0 ? OK : pct >= 0.9 ? WARN : BAD;
            linha.add(nome, BorderLayout.WEST);
            linha.add(barra(pct, cor), BorderLayout.CENTER);
            JLabel st = new JLabel(String.format("%.0f%%", pct * 100));
            st.setForeground(cor);
            st.setPreferredSize(new Dimension(44, 20));
            linha.add(st, BorderLayout.EAST);
            l.add(linha);
        }
        
        return new JScrollPane(l) {{ setBorder(null); setOpaque(false); getViewport().setOpaque(false); }};
    }
    
    private JComponent listaEntregas(List<String[]> historico) {
        JPanel l = new JPanel();
        l.setOpaque(false);
        l.setLayout(new BoxLayout(l, BoxLayout.Y_AXIS));
        
        if (historico.isEmpty()) {
            JLabel v = new JLabel("Nenhuma entrega registrada.");
            v.setForeground(MUTED);
            l.add(v);
        }
        
        for (int i = historico.size() - 1; i >= 0; i--) {
            String[] r = historico.get(i);
            JPanel linha = new JPanel(new BorderLayout());
            linha.setOpaque(false);
            linha.setBorder(new EmptyBorder(6, 0, 6, 0));
            linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            JLabel a = new JLabel("<html><b>" + r[2] + "</b><br><span style='color:#6B7280'>"
                    + descricaoVeiculo(r[0]) + " · " + nomeMotorista(r[1]) + " · " + r[4] + " km</span></html>");
            JLabel v = new JLabel(Entrega.formatarValor(valorDe(r)));
            v.setFont(v.getFont().deriveFont(Font.BOLD));
            linha.add(a, BorderLayout.CENTER);
            linha.add(v, BorderLayout.EAST);
            l.add(linha);
        }
        
        return new JScrollPane(l) {{ setBorder(null); setOpaque(false); getViewport().setOpaque(false); }};
    }
    
    private JComponent paginaVeiculos() {
        JButton add = botao("+ Novo veículo", ACCENT);
        add.addActionListener(e -> dialogoVeiculo());
        JButton man = botao("Realizar manutenção", WARN);
        JTable t = tabela(mdlVeiculos);
        
        man.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um veículo na tabela."); return; }
            Veiculo v = frota.buscarVeiculo((String) mdlVeiculos.getValueAt(r, 1));
            if (v == null || !v.precisaManutencao()) { aviso("Este veículo não tem itens pendentes."); return; }
            for (ItemChecagem item : v.listarItensPendentes()) {
                item.concluirManutencao();
            }
            salvarVeiculos();
            atualizarTudo();
        });
        
        JButton det = botao("Detalhar sistemas", MUTED);
        
        det.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um veículo na tabela."); return; }
            Veiculo v = frota.buscarVeiculo((String) mdlVeiculos.getValueAt(r, 1));
            if (v != null) dialogoSistemas(v);
        });
        
        JButton del = botao("Remover veículo", BAD);
        
        del.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um veículo na tabela."); return; }
            Veiculo v = frota.buscarVeiculo((String) mdlVeiculos.getValueAt(r, 1));
            if (v == null) return;
            if (!confirmarRemocao("o veículo " + v.getTipoVeiculo() + " " + v.getPlaca() + " (" + v.getModelo() + ")")) return;
            repoVeiculos.remover(v.getPlaca());
            frota.removerVeiculo(v.getPlaca());
            salvarVeiculos();
            registrarRemocao("Veículo", v.getPlaca(), v.getTipoVeiculo() + " " + v.getModelo()
                    + " - " + String.format("%.0f km", v.getQuilometragem()), "", "", detalhesVeiculos(v));
            atualizarTudo();
        });
        
        return pagina(t, add, man, det, del);
    }
    
    private void dialogoSistemas(Veiculo v) {
        record Linha(String sistema, ItemChecagem item) { }
        List<Linha> linhas = new ArrayList<>();
        
        for (SistemaVeiculo s : v.getSistemas()) {
            for (ItemChecagem i : s.getItens()) linhas.add(new Linha(s.getNomeSistema(), i));
        }
        
        // reprovados primeiro; depois maior desgaste; checagens simples (sem medida) por último
        linhas.sort((a, b) -> {
            if (a.item().isApto() != b.item().isApto()) return a.item().isApto() ? 1 : -1;
            return Double.compare(b.item().percentualDesgaste(), a.item().percentualDesgaste());
        });

        DefaultTableModel m = modelo("Sistema", "Componente", "Desgaste", "Uso (km)", "Uso (meses)", "Situação");
        int criticos = 0, atencao = 0;
        
        for (Linha l : linhas) {
            ItemChecagem i = l.item();
            double d = i.percentualDesgaste();
            String situacao;
            if (!i.isApto() || d >= 100) { situacao = "Manutenção necessária"; criticos++; }
            else if (d >= 80) { situacao = "Atenção"; atencao++; }
            else situacao = "Em dia";
            String km = i.getLimiteKm() == null ? "—" : String.format("%.0f / %.0f", i.getKilometrosDeUso(), i.getLimiteKm());
            String meses = i.getLimiteMeses() == null ? "—" : i.getTempoDeUsoMeses() + " / " + i.getLimiteMeses();
            m.addRow(new Object[]{l.sistema(), i.getNome(), d, km, meses, situacao});
        }
        
        JTable tb = tabela(m);
        tb.setRowHeight(32);
        tb.getColumnModel().getColumn(2).setCellRenderer((t2, val, sel, foc, row, col) -> {
            double d = (Double) val;
            
            if (d < 0) {
                JLabel n = new JLabel("sem medição");
                n.setForeground(MUTED);
                n.setBorder(new EmptyBorder(0, 12, 0, 0));
                return n;
            }
            
            JProgressBar pb = new JProgressBar(0, 100);
            pb.setValue((int) Math.min(100, Math.round(d)));
            pb.setStringPainted(true);
            pb.setString(String.format("%.0f%%", d));
            pb.setForeground(d >= 100 ? BAD : d >= 80 ? WARN : OK);
            pb.setBorder(new EmptyBorder(8, 8, 8, 8));
            
            return pb;
        });
        tb.getColumnModel().getColumn(0).setPreferredWidth(150);
        tb.getColumnModel().getColumn(1).setPreferredWidth(200);
        tb.getColumnModel().getColumn(5).setPreferredWidth(160);
        JLabel resumo = new JLabel(String.format("<html><b>%s %s</b> — %d precisam de manutenção, %d em atenção (≥ 80%% do limite)</html>",
                v.getTipoVeiculo(), v.getPlaca(), criticos, atencao));
        resumo.setBorder(new EmptyBorder(0, 0, 8, 0));
        JPanel corpo = new JPanel(new BorderLayout());
        corpo.add(resumo, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(tb);
        sp.setPreferredSize(new Dimension(820, 380));
        corpo.add(sp, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, corpo, "Sistemas do veículo", JOptionPane.PLAIN_MESSAGE);
    }
    
    private JComponent paginaRemovidos() {
        JTable t = tabela(mdlRemovidos);
        JButton det = botao("Ver detalhes", ACCENT);
        
        det.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um registro na tabela."); return; }
            // a tabela mostra os registros do mais recente para o mais antigo
            List<String[]> todos = lerRemovidos();
            String[] reg = todos.get(todos.size() - 1 - r);
            String texto = "Removido em: " + reg[0].replace('T', ' ') + "\n\n"
                    + (reg[6].isBlank() ? "Registro antigo, sem detalhes adicionais.\n" + reg[1] + ": " + reg[2] + "\n" + reg[3] : reg[6]);
            JTextArea area = new JTextArea(texto, 14, 44);
            area.setEditable(false); // selecionável para copiar telefone/e-mail
            area.setFont(BASE);
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Detalhes da remoção - " + reg[1], JOptionPane.PLAIN_MESSAGE);
        });
        
        return pagina(t, det);
    }
    
    private JComponent paginaMotoristas() {
        JButton add = botao("+ Novo motorista", ACCENT);
        add.addActionListener(e -> dialogoMotorista());
        JTable t = tabela(mdlMotoristas);
        JButton det = botao("Detalhes", MUTED);
        
        det.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um motorista na tabela."); return; }
            // Acoplamento dinâmico: variável declarada e instanciada como Motorista (filha),
            // usando exibirDados(), método definido apenas na classe pai Pessoa.
            for (Motorista m : frota.getMotoristas()) {
                if (m.getCpf().equals(mdlMotoristas.getValueAt(r, 1))) {
                    JOptionPane.showMessageDialog(this, m.exibirDados(), "Motorista", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        
        JButton del = botao("Remover motorista", BAD);
        
        del.addActionListener(e -> {
            int r = t.getSelectedRow();
            if (r < 0) { aviso("Selecione um motorista na tabela."); return; }
            String cpf = (String) mdlMotoristas.getValueAt(r, 1);
            String nome = (String) mdlMotoristas.getValueAt(r, 0);
            Motorista mot = null;
            
            for (Motorista x : frota.getMotoristas()) if (x.getCpf().equals(cpf)) mot = x;
            
            if (mot == null) return;
            
            if (!confirmarRemocao("o motorista " + nome + " (CPF " + cpf + ")")) return;
            
            try {
                repoMotoristas.remover(cpf);
            } catch (IOException ex) {
                aviso("Erro ao salvar: " + ex.getMessage());
                return;
            }
            
            frota.removerMotorista(cpf);
            registrarRemocao("Motorista", cpf, nome + " - CNH " + mot.getNumeroCNH(),
                    mot.getTelefone(), mot.getEmail(), detalhesMotorista(mot));
            atualizarTudo();
        });
        
        return pagina(t, add, det, del);
    }
    
    private boolean confirmarRemocao(String quem) {
        return JOptionPane.showConfirmDialog(this,
                "Remover " + quem + "?\nA remoção ficará registrada na aba \"Removidos\".",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    private JComponent paginaEntregas() {
        JButton add = botao("+ Nova entrega", ACCENT);
        add.addActionListener(e -> dialogoEntrega());
        
        return pagina(tabela(mdlEntregas), add);
    }

    // 2.1: método com lista de argumentos variados (JButton...), com FOR sobre os dados recebidos; usado nas páginas do sistema
    private JComponent pagina(JTable t, JButton... acoes) {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setOpaque(false);
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        barra.setOpaque(false);
        for (JButton b : acoes) barra.add(b);
        p.add(barra, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createLineBorder(new Color(0xE5E7EB)));
        sp.getViewport().setBackground(Color.WHITE);
        p.add(sp, BorderLayout.CENTER);
        
        return p;
    }
    
    // 12: entrada de dados por caixa de diálogo (JOptionPane, via confirmar())
    private void dialogoVeiculo() {
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Moto", "Carro", "Caminhão"});
        JTextField placa = new JTextField(), modeloF = new JTextField();
        JSpinner consumo = new JSpinner(new SpinnerNumberModel(10.0, 0.1, 200.0, 0.5));
        JComboBox<TipoCombustivel> comb = new JComboBox<>(TipoCombustivel.values());
        JSpinner cc = new JSpinner(new SpinnerNumberModel(160, 50, 2000, 10));
        JSpinner capLeve = new JSpinner(new SpinnerNumberModel(Moto.CAPACIDADE_PADRAO_KG, 1.0, 5000.0, 5.0));
        JSpinner volume = new JSpinner(new SpinnerNumberModel(40.0, 0.0, 200.0, 1.0));
        JSpinner cmt = new JSpinner(new SpinnerNumberModel(80000.0, 1000.0, 200000.0, 1000.0));
        JSpinner tara = new JSpinner(new SpinnerNumberModel(26000.0, 500.0, 100000.0, 500.0));
        JPanel f = form("Tipo", tipo, "Placa", placa, "Modelo", modeloF, "Consumo (km por litro/unidade)", consumo,
                "Combustível (carro)", comb, "Cilindradas (moto)", cc, "Carga máxima kg (carro/moto)", capLeve, "Volume do baú m³ (caminhão)", volume,
                "CMT kg (caminhão)", cmt, "Tara kg (caminhão)", tara);
        Runnable ajusta = () -> {
            int i = tipo.getSelectedIndex();
            capLeve.setEnabled(i != 2);
            if (i != 2) capLeve.setValue(i == 0 ? Moto.CAPACIDADE_PADRAO_KG : Carro.CAPACIDADE_PADRAO_KG); // 1.5: usa as constantes CAPACIDADE_PADRAO_KG de Moto e Carro
            cc.setEnabled(i == 0);
            comb.setEnabled(i == 1);
            volume.setEnabled(i == 2); cmt.setEnabled(i == 2); tara.setEnabled(i == 2);
        };
        tipo.addActionListener(e -> ajusta.run());
        ajusta.run();
        
        if (confirmar("Novo veículo", f)) {
            try {
                if (placa.getText().isBlank() || modeloF.getText().isBlank()) throw new IllegalArgumentException("Informe placa e modelo.");
                String pl = placa.getText().trim();
                String mod = modeloF.getText().trim();
                double cons = ((Number) consumo.getValue()).doubleValue();
                Veiculo v = switch (tipo.getSelectedIndex()) {
                    case 0 -> new Moto(pl, mod, "Não informado", TipoCombustivel.GASOLINA, 0.0, cons,
                            ((Number) cc.getValue()).intValue(), true, ((Number) capLeve.getValue()).doubleValue());
                    case 1 -> new Carro(pl, mod, "Não informado", (TipoCombustivel) comb.getSelectedItem(), 0.0, cons,
                            4, 400.0, ((Number) capLeve.getValue()).doubleValue());
                    default -> new Caminhao(pl, mod, cons, ((Number) volume.getValue()).doubleValue(),
                            ((Number) cmt.getValue()).doubleValue(), ((Number) tara.getValue()).doubleValue());
                };
                repoVeiculos.cadastrar(v); // lança IllegalArgumentException se a placa já existir
                frota.adicionarVeiculo(v);
                salvarVeiculos();
                atualizarTudo();
            } catch (IllegalArgumentException ex) { aviso(ex.getMessage()); }
        }
    }
    
    // 12: entrada de dados por caixa de diálogo (JOptionPane, via confirmar())
    private void dialogoMotorista() {
        JTextField nome = new JTextField(), cpf = new JTextField(), tel = new JTextField(), email = new JTextField();
        JTextField cnh = new JTextField(), validade = new JTextField(LocalDate.now().plusYears(5).toString());
        JSpinner idade = new JSpinner(new SpinnerNumberModel(30, 18, 100, 1));
        JPanel cats = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        java.util.Map<CategoriaCNH, JCheckBox> caixas = new java.util.EnumMap<>(CategoriaCNH.class);
        
        for (CategoriaCNH c : CategoriaCNH.values()) {
            JCheckBox cb = new JCheckBox(c.name());
            caixas.put(c, cb);
            cats.add(cb);
        }
        
        JPanel f = form("Nome", nome, "Idade", idade, "CPF (11 dígitos)", cpf, "Telefone", tel, "E-mail", email,
                "Nº da CNH (11 dígitos)", cnh, "Categorias", cats, "Validade (AAAA-MM-DD)", validade);
        
        if (confirmar("Novo motorista", f)) {
            try {
                Set<CategoriaCNH> categorias = EnumSet.noneOf(CategoriaCNH.class);
                caixas.forEach((c, cb) -> { if (cb.isSelected()) categorias.add(c); });
                // 6.2: o atributo ultimoMotoristaCadastrado é instanciado como Motorista (classe filha)
                ultimoMotoristaCadastrado = new Motorista(nome.getText().trim(), (int) idade.getValue(), cpf.getText().trim(),
                        tel.getText().trim(), email.getText().trim(), cnh.getText().trim(), categorias,
                        LocalDate.parse(validade.getText().trim()), true);
                repoMotoristas.cadastrar(ultimoMotoristaCadastrado); // valida CPF/CNH duplicados e grava em arquivo
                frota.adicionarMotorista(ultimoMotoristaCadastrado);
                atualizarTudo();
                // 6.2: chama exibirDados() (método da classe pai Pessoa) a partir do atributo do tipo Motorista
                // exibirDados() é herdado de Pessoa e chamado por um atributo do tipo Motorista
                JOptionPane.showMessageDialog(this, ultimoMotoristaCadastrado.exibirDados(),
                        "Motorista cadastrado", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException ex) {
                aviso("Data inválida. Use o formato AAAA-MM-DD.");
            } catch (IllegalArgumentException ex) {
                aviso(ex.getMessage());
            } catch (IOException ex) {
                aviso("Erro ao salvar: " + ex.getMessage());
            }
        }
    }
    
    // 12: entrada de dados por caixa de diálogo (JOptionPane, via confirmar())
    private void dialogoEntrega() {
        JTextField desc = new JTextField();
        JSpinner peso = new JSpinner(new SpinnerNumberModel(50.0, 1.0, 100000.0, 10.0));
        JSpinner dist = new JSpinner(new SpinnerNumberModel(50.0, 1.0, 100000.0, 10.0));
        JComboBox<Veiculo> vei = new JComboBox<>();
        JComboBox<Motorista> mot = new JComboBox<>();
        mot.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Motorista m) setText(descrever(m));
                return this;
            }
        });
        JLabel orc = new JLabel(" ");
        orc.setFont(orc.getFont().deriveFont(Font.BOLD, 16f));
        JLabel msg = new JLabel(" ");
        msg.setForeground(BAD);

        Runnable atualizaMot = () -> {
            mot.removeAllItems();
            Veiculo v = (Veiculo) vei.getSelectedItem();
            if (v != null) for (Motorista m : frota.motoristasHabilitados(v)) mot.addItem(m);
            if (vei.getItemCount() == 0) msg.setText("Nenhum veículo apto para esta carga.");
            else if (mot.getItemCount() == 0) msg.setText("Nenhum motorista habilitado para este veículo.");
            else msg.setText(" ");
            orc.setText(v == null ? " " : "Orçamento: " + Entrega.formatarValor(
                    frota.orcamento(v, ((Number) dist.getValue()).doubleValue())));
        };
        
        vei.addActionListener(e -> atualizaMot.run());
        
        Runnable recalcula = () -> {
            double p = ((Number) peso.getValue()).doubleValue();
            Object sel = vei.getSelectedItem();
            vei.removeAllItems();
            for (Veiculo v : frota.veiculosAptos(p)) vei.addItem(v);
            if (sel != null) vei.setSelectedItem(sel);
            atualizaMot.run();
        };
        
        peso.addChangeListener(e -> recalcula.run());
        dist.addChangeListener(e -> atualizaMot.run());
        recalcula.run();

        JPanel f = form("Descrição da carga", desc, "Peso (kg)", peso, "Distância (km)", dist,
                "Veículo apto", vei, "Motorista habilitado", mot, "", orc, "", msg);
        
        if (confirmar("Nova entrega", f)) {
            Veiculo v = (Veiculo) vei.getSelectedItem();
            Motorista m = (Motorista) mot.getSelectedItem();
            
            if (desc.getText().isBlank() || v == null || m == null) { aviso("Preencha a carga e escolha veículo e motorista."); return; }
            
            try {
                Entrega e = frota.atribuirEntrega(v, m, desc.getText().trim().replace(";", ","),
                        ((Number) peso.getValue()).doubleValue(), ((Number) dist.getValue()).doubleValue());
                salvarVeiculos(); // a entrega altera quilometragem e desgaste dos itens
                registrarHistorico(e);
                atualizarTudo();
                ir("Entregas");
            } catch (EntregaInvalidaException ex) { // 11: exceção do sistema tratada aqui
                aviso("Entrega recusada: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                aviso(ex.getMessage());
            }
        }
    }
    
    private String descrever(Motorista m) {
        // 7.3: polimorfismo de subtipagem (pai e filho concretos)
        // Polimorfismo de subtipagem: um Motorista é passado onde se espera uma Pessoa (ambas concretas).
        return identificar(m) + ", CNH " + new TreeSet<>(m.getTiposCNH()) + ")"
                + (m.estaDisponivel() ? "" : " - INDISPONÍVEL");
    }

    // 7.3: parâmetro do tipo Pessoa (pai concreto) recebe um Motorista (filho concreto)
    private String identificar(Pessoa p) {
        return p.getNome() + " (CPF " + p.getCpf();
    }

    private void atualizarTudo() {
        mdlVeiculos.setRowCount(0);
        
        for (Veiculo v : frota.getVeiculos()) {
            String status = v.precisaManutencao() ? "Precisa de manutenção" : "Disponível";
            mdlVeiculos.addRow(new Object[]{v.getTipoVeiculo(), v.getPlaca(), v.getModelo(),
                    String.format("%.0f km", v.getQuilometragem()), cargaMaxima(v), v.listarItensPendentes().size(), status});
        }
        
        mdlMotoristas.setRowCount(0);
        
        for (Motorista m : frota.getMotoristas()) {
            StringBuilder habilita = new StringBuilder();
            for (CategoriaCNH c : new TreeSet<>(m.getTiposCNH())) {
                if (habilita.length() > 0) habilita.append(", ");
                habilita.append(c.name()).append(" - ").append(c.getDescricao());
            }
            mdlMotoristas.addRow(new Object[]{m.getNome(), m.getCpf(), new TreeSet<>(m.getTiposCNH()),
                    habilita.toString(), m.estaDisponivel() ? "Disponível" : "Indisponível"});
        }
        
        mdlEntregas.setRowCount(0);
        
        for (String[] r : lerHistorico()) {
            mdlEntregas.addRow(new Object[]{r[2], r[3], r[4], descricaoVeiculo(r[0]), nomeMotorista(r[1]),
                    Entrega.formatarValor(valorDe(r))});
        }
        
        mdlRemovidos.setRowCount(0);
        List<String[]> removidos = lerRemovidos();
        
        for (int i = removidos.size() - 1; i >= 0; i--) {
            String[] r = removidos.get(i);
            mdlRemovidos.addRow(new Object[]{r[0].replace('T', ' '), r[1], r[2], r[3], r[4], r[5]});
        }
        
        montarDashboard();
    }
    
    private static DefaultTableModel modelo(String... cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }
    
    private static JTable tabela(DefaultTableModel m) {
        JTable t = new JTable(m);
        t.setRowHeight(38);
        t.setShowVerticalLines(false);
        t.setGridColor(new Color(0xE5E7EB));
        t.setSelectionBackground(new Color(0xDBEAFE));
        t.setSelectionForeground(TEXT);
        t.setFont(BASE);
        t.setIntercellSpacing(new Dimension(0, 1));
        JTableHeader h = t.getTableHeader();
        h.setPreferredSize(new Dimension(0, 40));
        h.setReorderingAllowed(false);
        
        h.setDefaultRenderer((tb, v, s, f, r, c) -> {
            JLabel l = new JLabel(String.valueOf(v));
            l.setOpaque(true);
            l.setBackground(new Color(0xF9FAFB));
            l.setForeground(MUTED);
            l.setFont(BASE.deriveFont(Font.BOLD, 12f));
            l.setBorder(new EmptyBorder(0, 12, 0, 0));
            
            return l;
        });
        
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(tb, v, s, f, r, c);
                l.setBorder(new EmptyBorder(0, 12, 0, 0));
                String txt = String.valueOf(v);
                
                if (!s) {
                    l.setForeground(TEXT);
                    if (txt.equals("Disponível")) l.setForeground(OK);
                    else if (txt.equals("Precisa de manutenção") || txt.equals("Atenção")) l.setForeground(WARN);
                    else if (txt.equals("Manutenção necessária")) l.setForeground(BAD);
                    else if (txt.equals("Em dia")) l.setForeground(OK);
                    else if (txt.equals("Indisponível")) l.setForeground(BAD);
                }
                
                return l;
            }
        });
        
        return t;
    }
    
    private static JPanel kpi(String rotulo, String valor, Color cor) {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.setColor(cor);
                g2.fill(new RoundRectangle2D.Float(0, 14, 5, getHeight() - 28, 5, 5));
                g2.dispose();
            }
        };
        
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(18, 22, 14, 14));
        JLabel a = new JLabel(rotulo);
        a.setForeground(MUTED);
        JLabel b = new JLabel(valor);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 26f));
        b.setForeground(TEXT);
        p.add(a);
        p.add(Box.createVerticalStrut(6));
        p.add(b);
        
        return p;
    }
    
    private static JPanel cartao(String titulo, JComponent corpo) {
        JPanel p = new JPanel(new BorderLayout(0, 10)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(18, 22, 18, 22));
        JLabel t = new JLabel(titulo);
        t.setFont(t.getFont().deriveFont(Font.BOLD, 16f));
        p.add(t, BorderLayout.NORTH);
        p.add(corpo, BorderLayout.CENTER);
        
        return p;
    }
    
    private static JComponent barra(double pct, Color cor) {
        return new JComponent() {
            { setPreferredSize(new Dimension(100, 10)); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int y = (getHeight() - 10) / 2;
                g2.setColor(new Color(0xE5E7EB));
                g2.fillRoundRect(0, y, getWidth(), 10, 10, 10);
                g2.setColor(cor);
                g2.fillRoundRect(0, y, (int) (getWidth() * pct), 10, 10, 10);
                g2.dispose();
            }
        };
    }

    private static JButton botao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFont(BASE.deriveFont(Font.BOLD));
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 18, 10, 18));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return b;
    }
    
    // 2.1: método com lista de argumentos variados (Object...), com FOR sobre os dados recebidos; usado nos diálogos
    // 2.2: método estático (não é o main), necessário e usado nos diálogos
    private static JPanel form(Object... pares) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        
        for (int i = 0; i < pares.length; i += 2) {
            c.gridy = i / 2;
            c.gridx = 0; c.weightx = 0; c.fill = GridBagConstraints.NONE;
            p.add(new JLabel((String) pares[i]), c);
            c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
            JComponent comp = (JComponent) pares[i + 1];
            comp.setPreferredSize(new Dimension(300, Math.max(30, comp.getPreferredSize().height)));
            p.add(comp, c);
        }
        
        return p;
    }

    // 12: caixa de diálogo (JOptionPane.showConfirmDialog) que recebe os dados digitados nos formulários
    private boolean confirmar(String titulo, JComponent corpo) {
        return JOptionPane.showConfirmDialog(this, corpo, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
    }

    private void aviso(String m) {
        JOptionPane.showMessageDialog(this, m, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
