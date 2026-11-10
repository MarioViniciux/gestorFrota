# Locais onde cada requisito foi cumprido

Projeto: Gestor de Frota e Entregas. Cada item abaixo abre o arquivo na linha indicada (links relativos; funcionam no GitHub, VS Code e na maioria dos visualizadores de Markdown).
As linhas valem para o código atual: se o código for editado, os números podem mudar. Os trechos também estão marcados no código com comentários `// N: ...`.

**#1.1. Encapsulamento - todos os atributos de classes não estáticas devem ser private:**
- [Caminhao.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L17) — `private double volumeBau;`
- [Caminhao.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L18) — `private double cmt;`
- [Caminhao.java - linha 19](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L19) — `private final double tara;`
- [Caminhao.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L20) — `private double pbtc;`
- [Caminhao.java - linha 21](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L21) — `private int numeroEixos;`
- [Carga.java - linha 6](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carga.java#L6) — `private final String descricao;`
- [Carga.java - linha 7](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carga.java#L7) — `private final double peso;`
- [Carro.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L17) — `private int numeroPortas;`
- [Carro.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L18) — `private double volumePortaMalas;`
- [Carro.java - linha 19](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L19) — `private double capacidadeCargaKg;`
- [CategoriaCNH.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/modelo/CategoriaCNH.java#L16) — `private final String descricao;`
- [Entrega.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L12) — `private final Veiculo veiculo;`
- [Entrega.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L13) — `private final Motorista motorista;`
- [Entrega.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L15) — `private final Carga carga;`
- [Entrega.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L16) — `private final double distanciaKm;`
- [Entrega.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L17) — `private final double valor;`
- [Frota.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L9) — `private final ArrayList<Veiculo> veiculos = new ArrayList<>();`
- [Frota.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L10) — `private final ArrayList<Motorista> motoristas = new ArrayList<>();`
- [Frota.java - linha 11](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L11) — `private final ArrayList<Entrega> entregas = new ArrayList<>();`
- [FrotaApp.java - linha 52](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L52) — `private final Frota frota = new Frota();`
- [FrotaApp.java - linha 53](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L53) — `private final RepositorioVeiculos repoVeiculos = new RepositorioVeiculos();`
- [FrotaApp.java - linha 54](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L54) — `private final RepositorioMotoristas repoMotoristas = new RepositorioMotoristas();`
- [FrotaApp.java - linha 57](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L57) — `private Motorista ultimoMotoristaCadastrado;`
- [FrotaApp.java - linha 59](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L59) — `private final CardLayout cards = new CardLayout();`
- [FrotaApp.java - linha 60](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L60) — `private final JPanel content = new JPanel(cards);`
- [FrotaApp.java - linha 61](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L61) — `private final JLabel titulo = new JLabel();`
- [FrotaApp.java - linha 62](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L62) — `private final java.util.Map<String, JButton> navButtons = new java.util.LinkedHashMap<>();`
- [FrotaApp.java - linha 64](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L64) — `private final DefaultTableModel mdlVeiculos = modelo("Tipo", "Placa", "Modelo", "KM rodados"...`
- [FrotaApp.java - linha 65](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L65) — `private final DefaultTableModel mdlMotoristas = modelo("Nome", "CPF", "CNH", "Habilitado par...`
- [FrotaApp.java - linha 66](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L66) — `private final DefaultTableModel mdlEntregas = modelo("Carga", "Peso (kg)", "Km", "Veículo", ...`
- [FrotaApp.java - linha 67](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L67) — `private final DefaultTableModel mdlRemovidos = modelo("Data/hora", "Tipo", "Identificação", ...`
- [FrotaApp.java - linha 68](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L68) — `private JPanel dashboard;`
- [ItemChecagem.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L10) — `private String nome;`
- [ItemChecagem.java - linha 11](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L11) — `private boolean apto;`
- [ItemChecagem.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L12) — `private float kilometrosDeUso;`
- [ItemChecagem.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L13) — `private int tempoDeUsoMeses;`
- [ItemChecagem.java - linha 14](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L14) — `private Double limiteKm;`
- [ItemChecagem.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L15) — `private Integer limiteMeses;`
- [Moto.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L15) — `private int cilindradas;`
- [Moto.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L16) — `private boolean possuiBau;`
- [Moto.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L17) — `private double capacidadeCargaKg;`
- [Motorista.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L18) — `private String numeroCNH;`
- [Motorista.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L20) — `private Set<CategoriaCNH> tiposCNH;`
- [Motorista.java - linha 21](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L21) — `private LocalDate validadeCNH;`
- [Motorista.java - linha 22](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L22) — `private boolean ativo;`
- [Pessoa.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L13) — `private String nome;`
- [Pessoa.java - linha 14](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L14) — `private int idade;`
- [Pessoa.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L15) — `private final String cpf;`
- [Pessoa.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L16) — `private String telefone;`
- [Pessoa.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L17) — `private String email;`
- [Repositorio.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L18) — `private final Path arquivo;`
- [RepositorioVeiculos.java - linha 36](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L36) — `private final Map<String, Veiculo> veiculos = new LinkedHashMap<>();`
- [SistemaVeiculo.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/SistemaVeiculo.java#L12) — `private String nomeSistema;`
- [SistemaVeiculo.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/SistemaVeiculo.java#L13) — `private List<ItemChecagem> itens;`
- [TipoCombustivel.java - linha 24](src/main/java/com/mycompany/gestorfrotaentrega/modelo/TipoCombustivel.java#L24) — `private final String descricao;`
- [TipoCombustivel.java - linha 26](src/main/java/com/mycompany/gestorfrotaentrega/modelo/TipoCombustivel.java#L26) — `private final String unidade;`
- [TipoCombustivel.java - linha 28](src/main/java/com/mycompany/gestorfrotaentrega/modelo/TipoCombustivel.java#L28) — `private final double precoReferencia;`
- [Veiculo.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L25) — `private final String placa;`
- [Veiculo.java - linha 30](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L30) — `private String modelo;`
- [Veiculo.java - linha 31](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L31) — `private String motor;`
- [Veiculo.java - linha 33](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L33) — `private TipoCombustivel combustivel;`
- [Veiculo.java - linha 34](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L34) — `private double potencia;`
- [Veiculo.java - linha 35](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L35) — `private double consumo;`
- [Veiculo.java - linha 36](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L36) — `private double quilometragem;`
- [Veiculo.java - linha 40](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L40) — `private final List<SistemaVeiculo> sistemas = new ArrayList<>();`

**#1.2. Atributos String, int e float - declarar e usar pelo menos um atributo não static do tipo String, outro int e outro float na mesma classe:**
- [ItemChecagem.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L10) — `private String nome;`
- [ItemChecagem.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L12) — `private float kilometrosDeUso;`
- [ItemChecagem.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L13) — `private int tempoDeUsoMeses;`
- [ItemChecagem.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L20) — `this.nome = nome;`
- [ItemChecagem.java - linha 50](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L50) — `this.kilometrosDeUso = (float) (this.kilometrosDeUso + km);`
- [ItemChecagem.java - linha 51](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L51) — `this.tempoDeUsoMeses += meses;`
- [ItemChecagem.java - linha 97](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L97) — `this.nome = nome;`

**#1.3. Atributo do tipo de uma enumeração - algum atributo deve ser do tipo de uma enumeração criada para o sistema e usada no mesmo:**
- [CategoriaCNH.java - linha 8](src/main/java/com/mycompany/gestorfrotaentrega/modelo/CategoriaCNH.java#L8) — `public enum CategoriaCNH {`
- [Frota.java - linha 80](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L80) — `public static List<CategoriaCNH> categoriasExigidas(Veiculo veiculo) {`
- [FrotaApp.java - linha 650](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L650) — `JComboBox<TipoCombustivel> comb = new JComboBox<>(TipoCombustivel.values());`
- [Motorista.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L20) — `private Set<CategoriaCNH> tiposCNH;`
- [RepositorioVeiculos.java - linha 183](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L183) — `TipoCombustivel comb = TipoCombustivel.valueOf(d[4]);`
- [TipoCombustivel.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/TipoCombustivel.java#L13) — `public enum TipoCombustivel {`
- [Veiculo.java - linha 33](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L33) — `private TipoCombustivel combustivel;`
- [Veiculo.java - linha 112](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L112) — `return calcularCustoCombustivel(km, combustivel.getPrecoReferencia());`

**#1.4. Atributo ArrayList - deve ter pelo menos um atributo ArrayList:**
- [Frota.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L9) — `private final ArrayList<Veiculo> veiculos = new ArrayList<>();`
- [Frota.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L10) — `private final ArrayList<Motorista> motoristas = new ArrayList<>();`
- [Frota.java - linha 11](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L11) — `private final ArrayList<Entrega> entregas = new ArrayList<>();`
- [Frota.java - linha 17](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L17) — `return veiculos.add(veiculo);`
- [Frota.java - linha 29](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L29) — `return motoristas.add(motorista);`
- [Frota.java - linha 157](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L157) — `entregas.add(entrega);`
- [Repositorio.java - linha 39](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L39) — `public ArrayList<T> carregar() throws IOException {`

**#1.5. Constante em classe concreta - constante de tipo primitivo, com valor na declaração, em classe concreta e necessária em outra classe do sistema:**
- [Carro.java - linha 23](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L23) — `public static final double CAPACIDADE_PADRAO_KG = 500;`
- [Entrega.java - linha 8](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L8) — `public static final double TAXA_ADMINISTRATIVA = 0.10;`
- [Frota.java - linha 134](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L134) — `return veiculo.calcularCustoViagem(distanciaKm) * (1 + Entrega.TAXA_ADMINISTRATIVA);`
- [FrotaApp.java - linha 652](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L652) — `JSpinner capLeve = new JSpinner(new SpinnerNumberModel(Moto.CAPACIDADE_PADRAO_KG, 1.0, 5000....`
- [FrotaApp.java - linha 662](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L662) — `if (i != 2) capLeve.setValue(i == 0 ? Moto.CAPACIDADE_PADRAO_KG : Carro.CAPACIDADE_PADRAO_KG);`
- [Moto.java - linha 21](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L21) — `public static final double CAPACIDADE_PADRAO_KG = 30;`

**#2.1. Método com argumentos variados (varargs) - método com lista de argumentos variados, com FOR sobre os dados recebidos, usado no sistema:**
- [Caminhao.java - linha 172](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L172) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [Carro.java - linha 104](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L104) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [FrotaApp.java - linha 479](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L479) — `return pagina(t, add, man, det, del);`
- [FrotaApp.java - linha 564](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L564) — `return pagina(t, det);`
- [FrotaApp.java - linha 613](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L613) — `return pagina(t, add, det, del);`
- [FrotaApp.java - linha 626](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L626) — `return pagina(tabela(mdlEntregas), add);`
- [FrotaApp.java - linha 630](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L630) — `private JComponent pagina(JTable t, JButton... acoes) {`
- [FrotaApp.java - linha 635](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L635) — `for (JButton b : acoes) barra.add(b);`
- [FrotaApp.java - linha 656](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L656) — `JPanel f = form("Tipo", tipo, "Placa", placa, "Modelo", modeloF, "Consumo (km por litro/unid...`
- [FrotaApp.java - linha 706](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L706) — `JPanel f = form("Nome", nome, "Idade", idade, "CPF (11 dígitos)", cpf, "Telefone", tel, "E-m...`
- [FrotaApp.java - linha 779](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L779) — `JPanel f = form("Descrição da carga", desc, "Peso (kg)", peso, "Distância (km)", dist,`
- [FrotaApp.java - linha 987](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L987) — `private static JPanel form(Object... pares) {`
- [FrotaApp.java - linha 993](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L993) — `for (int i = 0; i < pares.length; i += 2) {`
- [Moto.java - linha 91](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L91) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [Veiculo.java - linha 140](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L140) — `return somarCustos(calcularCustoViagem(km), pedagio);`
- [Veiculo.java - linha 149](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L149) — `protected static double somarCustos(double... parcelas) {`
- [Veiculo.java - linha 151](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L151) — `for (double p : parcelas) {`

**#2.2. Método estático - pelo menos um método estático (não pode ser o main), necessário e utilizado:**
- [Caminhao.java - linha 172](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L172) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [Carro.java - linha 104](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L104) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [Entrega.java - linha 66](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L66) — `public static String formatarValor(double valor) {`
- [Entrega.java - linha 81](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L81) — `+ " - " + formatarValor(valor);`
- [Frota.java - linha 66](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L66) — `public static double capacidadeKg(Veiculo veiculo) {`
- [Frota.java - linha 80](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L80) — `public static List<CategoriaCNH> categoriasExigidas(Veiculo veiculo) {`
- [Frota.java - linha 94](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L94) — `for (CategoriaCNH c : categoriasExigidas(veiculo)) {`
- [Frota.java - linha 106](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L106) — `if (pesoKg > capacidadeKg(veiculo)) {`
- [Frota.java - linha 107](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L107) — `return "carga acima da capacidade (" + capacidadeKg(veiculo) + " kg)";`
- [FrotaApp.java - linha 341](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L341) — `kpis.add(kpi("Receita em entregas", Entrega.formatarValor(receita), OK));`
- [FrotaApp.java - linha 426](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L426) — `JLabel v = new JLabel(Entrega.formatarValor(valorDe(r)));`
- [FrotaApp.java - linha 760](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L760) — `orc.setText(v == null ? " " : "Orçamento: " + Entrega.formatarValor(`
- [FrotaApp.java - linha 840](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L840) — `Entrega.formatarValor(valorDe(r))});`
- [FrotaApp.java - linha 987](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L987) — `private static JPanel form(Object... pares) {`
- [Moto.java - linha 91](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L91) — `return somarCustos(calcularCustoCombustivel(km), km * calcularCustoManutencaoPorKm());`
- [Motorista.java - linha 157](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L157) — `public static Set<CategoriaCNH>lerCategorias(String texto){`
- [RepositorioMotoristas.java - linha 30](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L30) — `Set<CategoriaCNH> tiposCNH = Motorista.lerCategorias(dados[6]);`
- [Veiculo.java - linha 149](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L149) — `protected static double somarCustos(double... parcelas) {`

**#3. Construtor - pelo menos duas classes concretas do projeto devem possuir um construtor declarado:**
- [Caminhao.java - linha 27](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L27) — `public Caminhao(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Caminhao.java - linha 50](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L50) — `public Caminhao(String placa, String modelo, double consumo, double volumeBau,`
- [Carga.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carga.java#L10) — `public Carga (String descricao, double peso){`
- [Carro.java - linha 30](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L30) — `public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Carro.java - linha 42](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L42) — `public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Carro.java - linha 53](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L53) — `public Carro(String placa, String modelo, TipoCombustivel combustivel, double consumo) {`
- [Entrega.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L20) — `public Entrega(Veiculo veiculo, Motorista motorista, String descricaoCarga, double pesoCarga...`
- [Entrega.java - linha 31](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L31) — `this.carga = new Carga(descricaoCarga, pesoCarga);`
- [EntregaInvalidaException.java - linha 6](src/main/java/com/mycompany/gestorfrotaentrega/modelo/EntregaInvalidaException.java#L6) — `public EntregaInvalidaException (String mensagem){`
- [Frota.java - linha 154](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L154) — `Entrega entrega = new Entrega(veiculo, motorista, descricao, pesoKg, distanciaKm,`
- [FrotaApp.java - linha 677](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L677) — `case 0 -> new Moto(pl, mod, "Não informado", TipoCombustivel.GASOLINA, 0.0, cons,`
- [FrotaApp.java - linha 679](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L679) — `case 1 -> new Carro(pl, mod, "Não informado", (TipoCombustivel) comb.getSelectedItem(), 0.0,...`
- [FrotaApp.java - linha 681](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L681) — `default -> new Caminhao(pl, mod, cons, ((Number) volume.getValue()).doubleValue(),`
- [FrotaApp.java - linha 714](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L714) — `ultimoMotoristaCadastrado = new Motorista(nome.getText().trim(), (int) idade.getValue(), cpf...`
- [ItemChecagem.java - linha 19](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L19) — `public ItemChecagem(String nome) {`
- [ItemChecagem.java - linha 28](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L28) — `public ItemChecagem(String nome, double limiteKm, int limiteMeses) {`
- [Moto.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L25) — `public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Moto.java - linha 37](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L37) — `public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Moto.java - linha 48](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L48) — `public Moto(String placa, String modelo, int cilindradas, double consumo) {`
- [Motorista.java - linha 26](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L26) — `public Motorista(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail, Str...`
- [Motorista.java - linha 27](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L27) — `super(pNome, pIdade, pCpf, pTelefone, pEmail);`
- [Pessoa.java - linha 21](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L21) — `public Pessoa(String pNome, int pIdade, String pCpf, String pTelefone, String pEmail){`
- [RepositorioMotoristas.java - linha 19](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L19) — `public RepositorioMotoristas(){`
- [RepositorioMotoristas.java - linha 33](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L33) — `return new Motorista(`
- [RepositorioVeiculos.java - linha 30](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L30) — `public RepositorioVeiculos() {`
- [RepositorioVeiculos.java - linha 188](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L188) — `return new Caminhao(d[1], d[2], d[3], comb, potencia, consumo,`
- [RepositorioVeiculos.java - linha 192](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L192) — `return new Carro(d[1], d[2], d[3], comb, potencia, consumo,`
- [RepositorioVeiculos.java - linha 195](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L195) — `return new Moto(d[1], d[2], d[3], comb, potencia, consumo,`
- [SistemaVeiculo.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/modelo/SistemaVeiculo.java#L16) — `public SistemaVeiculo(String nomeSistema) {`
- [Veiculo.java - linha 49](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L49) — `public Veiculo(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Veiculo.java - linha 74](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L74) — `public Veiculo(String placa, String modelo, TipoCombustivel combustivel, double consumo) {`

**#4. Associação por Composição - o construtor precisa ser usado; não pode ser do tipo genérico; não pode ser feita em classe abstrata:**
- [Carga.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carga.java#L10) — `public Carga (String descricao, double peso){`
- [Entrega.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L15) — `private final Carga carga;`
- [Entrega.java - linha 31](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L31) — `this.carga = new Carga(descricaoCarga, pesoCarga);`
- [Frota.java - linha 154](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L154) — `Entrega entrega = new Entrega(veiculo, motorista, descricao, pesoKg, distanciaKm,`

**#5. Associação por Agregação - os construtores envolvidos precisam ser usados de maneira importante no sistema; não pode ser feita em classe abstrata:**
- [Entrega.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L12) — `private final Veiculo veiculo;`
- [Entrega.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L13) — `private final Motorista motorista;`
- [Entrega.java - linha 20](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L20) — `public Entrega(Veiculo veiculo, Motorista motorista, String descricaoCarga, double pesoCarga...`
- [Entrega.java - linha 28](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L28) — `this.veiculo = veiculo;`
- [Entrega.java - linha 29](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L29) — `this.motorista = motorista;`
- [Frota.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L9) — `private final ArrayList<Veiculo> veiculos = new ArrayList<>();`
- [Frota.java - linha 10](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L10) — `private final ArrayList<Motorista> motoristas = new ArrayList<>();`
- [Frota.java - linha 11](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L11) — `private final ArrayList<Entrega> entregas = new ArrayList<>();`
- [Frota.java - linha 154](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L154) — `Entrega entrega = new Entrega(veiculo, motorista, descricao, pesoKg, distanciaKm,`

**#6.1. Atributo final em superclasse concreta - atributo final em alguma superclasse concreta, diferente do criado para o item 1.5:**
- [Motorista.java - linha 27](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L27) — `super(pNome, pIdade, pCpf, pTelefone, pEmail);`
- [Pessoa.java - linha 15](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L15) — `private final String cpf;`
- [Pessoa.java - linha 27](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L27) — `this.cpf = pCpf;`

**#6.2. Acoplamento dinâmico - classe pai concreta com método x; classe filha sem o método x; outra classe com atributo declarado e instanciado como a FILHA que usa o método x:**
- [FrotaApp.java - linha 57](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L57) — `private Motorista ultimoMotoristaCadastrado;`
- [FrotaApp.java - linha 714](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L714) — `ultimoMotoristaCadastrado = new Motorista(nome.getText().trim(), (int) idade.getValue(), cpf...`
- [FrotaApp.java - linha 722](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L722) — `JOptionPane.showMessageDialog(this, ultimoMotoristaCadastrado.exibirDados(),`
- [Motorista.java - linha 14](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L14) — `public class Motorista extends Pessoa implements Persistivel{`
- [Pessoa.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L9) — `public class Pessoa {`
- [Pessoa.java - linha 86](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L86) — `public String exibirDados(){`

**#7.1. Polimorfismo de sobrecarga - mesmo nome de método/construtor com parâmetros diferentes:**
- [Caminhao.java - linha 27](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L27) — `public Caminhao(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Caminhao.java - linha 50](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L50) — `public Caminhao(String placa, String modelo, double consumo, double volumeBau,`
- [Caminhao.java - linha 136](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L136) — `public void carregarVeiculo(double pesoCargaKilos) {`
- [Caminhao.java - linha 153](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L153) — `public void carregarVeiculo(double pesoCargaKilos, double cargaExtraKilos) {`
- [Caminhao.java - linha 154](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L154) — `carregarVeiculo(pesoCargaKilos + cargaExtraKilos);`
- [Carro.java - linha 30](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L30) — `public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Carro.java - linha 42](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L42) — `public Carro(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Carro.java - linha 53](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L53) — `public Carro(String placa, String modelo, TipoCombustivel combustivel, double consumo) {`
- [ItemChecagem.java - linha 19](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L19) — `public ItemChecagem(String nome) {`
- [ItemChecagem.java - linha 28](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L28) — `public ItemChecagem(String nome, double limiteKm, int limiteMeses) {`
- [ItemChecagem.java - linha 29](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L29) — `this(nome);`
- [Moto.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L25) — `public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Moto.java - linha 37](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L37) — `public Moto(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Moto.java - linha 48](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L48) — `public Moto(String placa, String modelo, int cilindradas, double consumo) {`
- [Veiculo.java - linha 49](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L49) — `public Veiculo(String placa, String modelo, String motor, TipoCombustivel combustivel,`
- [Veiculo.java - linha 74](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L74) — `public Veiculo(String placa, String modelo, TipoCombustivel combustivel, double consumo) {`
- [Veiculo.java - linha 111](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L111) — `public double calcularCustoCombustivel(double km) {`
- [Veiculo.java - linha 112](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L112) — `return calcularCustoCombustivel(km, combustivel.getPrecoReferencia());`
- [Veiculo.java - linha 122](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L122) — `public double calcularCustoCombustivel(double km, double precoPorUnidade) {`
- [Veiculo.java - linha 136](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L136) — `public double calcularCustoViagem(double km, double pedagio) {`

**#7.2. Polimorfismo de coerção (explícito) - o atributo que recebe é do tipo do casting e o fornecedor é de outro tipo; sem usar função de conversão:**
- [ItemChecagem.java - linha 50](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L50) — `this.kilometrosDeUso = (float) (this.kilometrosDeUso + km);`

**#7.3. Polimorfismo de subtipagem - pai e filho concretos (não pode ser feito com classes abstratas):**
- [FrotaApp.java - linha 806](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L806) — `return identificar(m) + ", CNH " + new TreeSet<>(m.getTiposCNH()) + ")"`
- [FrotaApp.java - linha 811](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L811) — `private String identificar(Pessoa p) {`
- [Motorista.java - linha 14](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L14) — `public class Motorista extends Pessoa implements Persistivel{`
- [Pessoa.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L9) — `public class Pessoa {`

**#7.4. Polimorfismo paramétrico - parâmetro genérico passado de forma explícita; não pode ser no construtor nem em classe abstrata:**
- [FrotaApp.java - linha 346](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L346) — `String porTipo = repoVeiculos.<Carro>listarPorTipo(Carro.class).size() + " carros · "`
- [FrotaApp.java - linha 347](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L347) — `+ repoVeiculos.<Moto>listarPorTipo(Moto.class).size() + " motos · "`
- [FrotaApp.java - linha 348](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L348) — `+ repoVeiculos.<Caminhao>listarPorTipo(Caminhao.class).size() + " caminhões";`
- [RepositorioVeiculos.java - linha 104](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L104) — `public <T extends Veiculo> List<T> listarPorTipo(Class<T> tipo) {`

**#8. Interfaces - pelo menos três classes implementam uma interface e uma delas implementa pelo menos duas; sem get/set nem construtores nas interfaces:**
- [Custeavel.java - linha 12](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Custeavel.java#L12) — `public interface Custeavel {`
- [Entrega.java - linha 6](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L6) — `public class Entrega implements Persistivel{`
- [Entrega.java - linha 72](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L72) — `public String paraLinha() {`
- [FrotaApp.java - linha 220](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L220) — `Files.writeString(HISTORICO, entrega.paraLinha() + System.lineSeparator(), StandardOpenOptio...`
- [Manutenivel.java - linha 13](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Manutenivel.java#L13) — `public interface Manutenivel {`
- [Motorista.java - linha 14](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L14) — `public class Motorista extends Pessoa implements Persistivel{`
- [Motorista.java - linha 130](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L130) — `public String paraLinha(){`
- [Persistivel.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Persistivel.java#L9) — `public interface Persistivel {`
- [Repositorio.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L16) — `public abstract class Repositorio<T extends Persistivel> {`
- [Repositorio.java - linha 32](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L32) — `linhas.add(item.paraLinha());`
- [Veiculo.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L18) — `public abstract class Veiculo implements Custeavel, Manutenivel, Persistivel {`
- [Veiculo.java - linha 256](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L256) — `public String paraLinha() {`

**#9. Pacotes - criar EXATAMENTE dois pacotes (modelo e aplicacao):**
- [Caminhao.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Carga.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carga.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Carro.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [CategoriaCNH.java - linha 5](src/main/java/com/mycompany/gestorfrotaentrega/modelo/CategoriaCNH.java#L5) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Custeavel.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Custeavel.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Entrega.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [EntregaInvalidaException.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/EntregaInvalidaException.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Frota.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [FrotaApp.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L1) — `package com.mycompany.gestorfrotaentrega.aplicacao;`
- [ItemChecagem.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/ItemChecagem.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Manutenivel.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Manutenivel.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Moto.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Motorista.java - linha 5](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L5) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Persistivel.java - linha 6](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Persistivel.java#L6) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Pessoa.java - linha 5](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Pessoa.java#L5) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Repositorio.java - linha 5](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L5) — `package com.mycompany.gestorfrotaentrega.aplicacao;`
- [RepositorioMotoristas.java - linha 5](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L5) — `package com.mycompany.gestorfrotaentrega.aplicacao;`
- [RepositorioVeiculos.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L1) — `package com.mycompany.gestorfrotaentrega.aplicacao;`
- [SistemaVeiculo.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/SistemaVeiculo.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [TipoCombustivel.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/TipoCombustivel.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`
- [Veiculo.java - linha 1](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L1) — `package com.mycompany.gestorfrotaentrega.modelo;`

**#10. Classes abstratas - pelo menos duas, com método abstrato (que não seja construtor, get nem set) e com as classes concretas implementadas:**
- [Caminhao.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L9) — `public class Caminhao extends Veiculo {`
- [Caminhao.java - linha 66](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Caminhao.java#L66) — `public void carregarSistemasDoManual() {`
- [Carro.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L9) — `public class Carro extends Veiculo {`
- [Carro.java - linha 68](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Carro.java#L68) — `public void carregarSistemasDoManual() {`
- [Moto.java - linha 9](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L9) — `public class Moto extends Veiculo {`
- [Moto.java - linha 58](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Moto.java#L58) — `public void carregarSistemasDoManual() {`
- [Repositorio.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L16) — `public abstract class Repositorio<T extends Persistivel> {`
- [Repositorio.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L25) — `protected abstract T converter(String linha);`
- [RepositorioMotoristas.java - linha 16](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L16) — `public class RepositorioMotoristas extends Repositorio<Motorista>{`
- [RepositorioMotoristas.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L25) — `protected Motorista converter(String linha){`
- [RepositorioVeiculos.java - linha 26](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L26) — `public class RepositorioVeiculos extends Repositorio<Veiculo> {`
- [RepositorioVeiculos.java - linha 167](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L167) — `protected Veiculo converter(String linha) {`
- [Veiculo.java - linha 18](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L18) — `public abstract class Veiculo implements Custeavel, Manutenivel, Persistivel {`
- [Veiculo.java - linha 89](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L89) — `public abstract void carregarSistemasDoManual();`
- [Veiculo.java - linha 97](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L97) — `public abstract double calcularCustoViagem(double km);`
- [Veiculo.java - linha 102](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L102) — `public abstract double calcularCustoManutencaoPorKm();`
- [Veiculo.java - linha 245](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L245) — `protected abstract String camposEspecificos();`

**#11. Exceção - uma exceção deve ser criada, levantada e tratada pelo programa:**
- [EntregaInvalidaException.java - linha 4](src/main/java/com/mycompany/gestorfrotaentrega/modelo/EntregaInvalidaException.java#L4) — `public class EntregaInvalidaException extends Exception {`
- [Frota.java - linha 137](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L137) — `public Entrega atribuirEntrega(Veiculo veiculo, Motorista motorista, String descricao,`
- [Frota.java - linha 141](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L141) — `throw new EntregaInvalidaException("Peso e distância devem ser maiores que zero.");`
- [Frota.java - linha 146](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L146) — `throw new EntregaInvalidaException("Veículo " + veiculo.getPlaca() + ": " + motivo + ".");`
- [Frota.java - linha 150](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Frota.java#L150) — `throw new EntregaInvalidaException("Motorista " + motorista.getNome()`
- [FrotaApp.java - linha 789](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L789) — `Entrega e = frota.atribuirEntrega(v, m, desc.getText().trim().replace(";", ","),`
- [FrotaApp.java - linha 795](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L795) — `} catch (EntregaInvalidaException ex) {`

**#12. Entrada de dados por caixas de diálogo - entrada de dados usando caixas de diálogo (interface gráfica sozinha não cumpre):**
- [FrotaApp.java - linha 646](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L646) — `private void dialogoVeiculo() {`
- [FrotaApp.java - linha 670](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L670) — `if (confirmar("Novo veículo", f)) {`
- [FrotaApp.java - linha 693](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L693) — `private void dialogoMotorista() {`
- [FrotaApp.java - linha 709](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L709) — `if (confirmar("Novo motorista", f)) {`
- [FrotaApp.java - linha 735](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L735) — `private void dialogoEntrega() {`
- [FrotaApp.java - linha 782](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L782) — `if (confirmar("Nova entrega", f)) {`
- [FrotaApp.java - linha 1007](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L1007) — `private boolean confirmar(String titulo, JComponent corpo) {`

**#13. Persistência em arquivo - guardar alguma informação em arquivo e depois recuperá-la e usá-la:**
- [Entrega.java - linha 72](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Entrega.java#L72) — `public String paraLinha() {`
- [FrotaApp.java - linha 48](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L48) — `private static final Path HISTORICO = Path.of("historico.txt");`
- [FrotaApp.java - linha 123](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L123) — `private void carregarDados() {`
- [FrotaApp.java - linha 125](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L125) — `for (Veiculo v : repoVeiculos.carregar()) {`
- [FrotaApp.java - linha 127](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L127) — `repoVeiculos.cadastrar(v);`
- [FrotaApp.java - linha 131](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L131) — `for (Motorista m : repoMotoristas.carregar()) {`
- [FrotaApp.java - linha 140](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L140) — `private void registrarRemocao(String tipo, String identificador, String descricao, String te...`
- [FrotaApp.java - linha 151](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L151) — `private List<String[]> lerRemovidos() {`
- [FrotaApp.java - linha 211](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L211) — `repoVeiculos.salvar();`
- [FrotaApp.java - linha 218](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L218) — `private void registrarHistorico(Entrega entrega) {`
- [FrotaApp.java - linha 227](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L227) — `private List<String[]> lerHistorico() {`
- [FrotaApp.java - linha 601](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L601) — `repoMotoristas.remover(cpf);`
- [FrotaApp.java - linha 684](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L684) — `repoVeiculos.cadastrar(v);`
- [FrotaApp.java - linha 717](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/FrotaApp.java#L717) — `repoMotoristas.cadastrar(ultimoMotoristaCadastrado);`
- [Motorista.java - linha 130](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Motorista.java#L130) — `public String paraLinha(){`
- [Repositorio.java - linha 28](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L28) — `public void salvar(List<T> itens) throws IOException {`
- [Repositorio.java - linha 35](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L35) — `Files.write(arquivo, linhas);`
- [Repositorio.java - linha 39](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L39) — `public ArrayList<T> carregar() throws IOException {`
- [Repositorio.java - linha 46](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/Repositorio.java#L46) — `for (String linha : Files.readAllLines(arquivo)) {`
- [RepositorioMotoristas.java - linha 25](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioMotoristas.java#L25) — `protected Motorista converter(String linha){`
- [RepositorioVeiculos.java - linha 139](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L139) — `public void salvar() throws IOException {`
- [RepositorioVeiculos.java - linha 167](src/main/java/com/mycompany/gestorfrotaentrega/aplicacao/RepositorioVeiculos.java#L167) — `protected Veiculo converter(String linha) {`
- [Veiculo.java - linha 256](src/main/java/com/mycompany/gestorfrotaentrega/modelo/Veiculo.java#L256) — `public String paraLinha() {`
