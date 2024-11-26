
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Servidor (default: localhost): ");
        String servidor = scanner.nextLine().trim();
        servidor = servidor.isEmpty() ? "localhost" : servidor;

        System.out.print("Porta (default: 3306): ");
        String porta = scanner.nextLine().trim();
        porta = porta.isEmpty() ? "3306" : porta;

        System.out.print("Nome do banco: ");
        String nomeBanco = scanner.nextLine();

        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Banco banco = new Banco(servidor, porta, nomeBanco, usuario, senha);

        if (!banco.conectado()) {
            System.out.println("Erro ao conectar ao banco: " + banco.obterMensagemErro());
            return;
        }

        System.out.println("Conexão estabelecida com sucesso!");

        Contato contato = new Contato(banco.obterConexao());
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nOpções:");
            System.out.println("1. Gravar contato");
            System.out.println("2. Atualizar contato");
            System.out.println("3. Deletar contato");
            System.out.println("4. Listar contatos");
            System.out.println("5. Pesquisar contato");
            System.out.println("6. Obter contato por ID");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        contato.setNome(scanner.nextLine());

                        System.out.print("E-mail: ");
                        contato.setEmail(scanner.nextLine());

                        System.out.print("Telefone: ");
                        contato.setTelefone(scanner.nextLine());

                        contato.gravarContato();
                        break;

                    case 2:
                        System.out.print("ID do contato para atualizar: ");
                        contato.setIdContato(scanner.nextInt());
                        scanner.nextLine();

                        System.out.print("Novo nome: ");
                        contato.setNome(scanner.nextLine());

                        System.out.print("Novo e-mail: ");
                        contato.setEmail(scanner.nextLine());

                        System.out.print("Novo telefone: ");
                        contato.setTelefone(scanner.nextLine());

                        contato.atualizarContato();
                        System.out.println("Contato atualizado com sucesso.");
                        break;

                    case 3:
                        System.out.print("ID do contato para deletar: ");
                        int idParaDeletar = scanner.nextInt();
                        scanner.nextLine();

                        contato.deletarContato(idParaDeletar);
                        System.out.println("Contato deletado com sucesso.");
                        break;

                    case 4:
                        contato.listarContatos();
                        break;

                    case 5:
                        System.out.print("Informe o termo para pesquisa: ");
                        String termo = scanner.nextLine();

                        contato.pesquisarContato(termo);
                        break;

                    case 6:
                        System.out.print("ID do contato para buscar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (contato.obterContatoPorId(id)) {
                            System.out.println("Contato encontrado:");
                            System.out.println("ID: " + contato.getIdContato());
                            System.out.println("Nome: " + contato.getNome());
                            System.out.println("E-mail: " + contato.getEmail());
                            System.out.println("Telefone: " + contato.getTelefone());
                        } else {
                            System.out.println("Contato com ID " + id + " não encontrado.");
                        }
                        break;

                    case 7:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (SQLException e) {
                System.err.println("Erro durante a operação: " + e.getMessage());
            }
        }

        banco.encerraConexao();
        System.out.println("Conexão encerrada. Programa finalizado.");
    }
}
