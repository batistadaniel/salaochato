import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        Menu menu = new Menu();
        App app = new App("Salão Beleza Rápida");

        menu.tituloInicial();
        app.menu();
    }
}

class Menu {

    void tituloInicial() {
        System.out.println();
        System.out.println("*************** BEM VINDO ***************");
        System.out.println();
    }
}

class App {
    private final String nome;
    private final Scanner entrada;
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Servico> servicos = new ArrayList<>();
    private ArrayList<Agendamento> agendamentos = new ArrayList<>();

    public App(String nome) {
        this.nome = nome;
        this.entrada = new Scanner(System.in);
    }

    public void menu() {
        int opcao;

        do {
            System.out.println("\n*************** " + nome + " ***************");
            System.out.println(">>> 1. Cadastrar Usuário");
            System.out.println(">>> 2. Listar Usuários");
            System.out.println(">>> 3. Cadastrar Serviço");
            System.out.println(">>> 4. Listar Serviços");
            System.out.println(">>> 5. Cadastrar Agendamento");
            System.out.println(">>> 6. Listar Agendamentos");
            System.out.println(">>> 7. Sair");

            System.out.print(">>>>> ");
            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    cadastrarServico();
                    break;
                case 4:
                    listarServicos();
                    break;
                case 5:
                    cadastrarAgendamento();
                    break;
                case 6:
                    listarAgendamentos();
                    break;
                case 7:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 7);
    }

    private void cadastrarUsuario() {
        System.out.println("\nCadastro de Usuário:");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();

        System.out.println("Tipo de Usuário:");
        System.out.println("1. Funcionario");
        System.out.println("2. Cliente");
        System.out.print(">>>>> ");
        int tipo = entrada.nextInt();
        entrada.nextLine();

        if (tipo == 1) {
            funcionarios.add(new Funcionario(nome, email));
            System.out.println("Funcionário cadastrado com sucesso!");
        } else if (tipo == 2) {
            clientes.add(new Cliente(nome, email));
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    private void listarUsuarios() {
        System.out.println("\nFuncionários:");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println(f.getNome() + " - " + f.getEmail());
            }
        }

        System.out.println("\nClientes:");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c.getNome() + " - " + c.getEmail());
            }
        }
    }

    void cadastrarServico() {
        System.out.println("\nCadastro de Serviço:");
        System.out.print("Nome do Serviço: ");
        String nome = entrada.nextLine();
        System.out.print("Descrição: ");
        String descricao = entrada.nextLine();
        servicos.add(new Servico(nome, descricao));
        System.out.println("Serviço cadastrado com sucesso!");
    }

    void listarServicos() {
        System.out.println("\nServiços Cadastrados:");
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
        } else {
            for (int i = 0; i < servicos.size(); i++) {
                System.out.println((i + 1) + ". " + servicos.get(i).getNome());
            }
        }
    }

    void cadastrarAgendamento() {
        System.out.println("\nCadastro de Agendamento:");

        if (clientes.isEmpty() || funcionarios.isEmpty() || servicos.isEmpty()) {
            System.out.println("Certifique-se de ter clientes, funcionários e serviços cadastrados antes de agendar.");
            return;
        }

        System.out.println("Selecione o Cliente:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNome());
        }
        int clienteIndex = entrada.nextInt() - 1;

        System.out.println("Selecione o Funcionário:");
        for (int i = 0; i < funcionarios.size(); i++) {
            System.out.println((i + 1) + ". " + funcionarios.get(i).getNome());
        }
        int funcionarioIndex = entrada.nextInt() - 1;

        System.out.println("Selecione o Serviço:");
        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + ". " + servicos.get(i).getNome());
        }
        int servicoIndex = entrada.nextInt() - 1;
        entrada.nextLine(); // Limpa buffer

        System.out.print("Data (yyyy-mm-dd): ");
        LocalDate data = LocalDate.parse(entrada.nextLine());
        System.out.print("Hora (hh:mm): ");
        LocalTime hora = LocalTime.parse(entrada.nextLine());

        agendamentos.add(new Agendamento(
                clientes.get(clienteIndex),
                funcionarios.get(funcionarioIndex),
                servicos.get(servicoIndex),
                data,
                hora));

        System.out.println("Agendamento cadastrado com sucesso!");
    }

    void listarAgendamentos() {
        System.out.println("\nAgendamentos:");
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
        } else {
            for (Agendamento ag : agendamentos) {
                System.out.println("Cliente: " + ag.getCliente().getNome() + ", Serviço: " + ag.getServico().getNome() +
                        ", Data: " + ag.getData() + ", Hora: " + ag.getHora());
            }
        }
    }
}

class Usuario {
    private String nome;
    private String email;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
}

class Cliente extends Usuario {
    public Cliente(String nome, String email) { super(nome, email); }
}

class Funcionario extends Usuario {
    public Funcionario(String nome, String email) { super(nome, email); }
}

class Servico {
    private String nome, descricao;

    public Servico(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
}

class Agendamento {
    private Cliente cliente;
    private Funcionario funcionario;
    private Servico servico;
    private LocalDate data;
    private LocalTime hora;

    public Agendamento(Cliente cliente, Funcionario funcionario, Servico servico, LocalDate data, LocalTime hora) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
    }

    public Cliente getCliente() { return cliente; }
    public Servico getServico() { return servico; }
    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
}
