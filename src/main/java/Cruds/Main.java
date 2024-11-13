package Cruds;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal com a interface de linha de comando para gerenciar operações com Clientes.
 */
public class Main {
    /**
     * Método principal que executa o programa de gerenciamento de clientes.
     * Apresenta um menu de opções ao usuário para criar, listar, buscar, atualizar e excluir clientes.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Cria um objeto Scanner para capturar a entrada do usuário no console
        Scanner scanner = new Scanner(System.in);

        try {
            // Instancia o ClienteADO, responsável por gerenciar a interação com o banco de dados
            ClienteADO clienteADO = new ClienteADO();

            // Loop principal do programa para apresentar o menu e executar operações
            while (true) {
                // Exibe o menu de opções para o usuário
                System.out.println("\n--- Menu Principal ---");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Listar Clientes");
                System.out.println("3. Buscar Cliente");
                System.out.println("4. Atualizar Cliente");
                System.out.println("5. Excluir Cliente");
                System.out.println("0. Sair\n");
                System.out.print("Escolha uma opcao: ");

                // Captura a opção escolhida pelo usuário
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do scanner após capturar um número

                // Executa a operação com base na opção selecionada
                switch (opcao) {
                    case 1 -> {
                        // Captura os demais dados do cliente
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = scanner.nextLine();

                        Cliente novoCliente = new Cliente(0, nome, email, telefone, new Date(System.currentTimeMillis()));
                        clienteADO.cadastrarCliente(novoCliente);
                        System.out.println("Cliente cadastrado com sucesso!");
                    }

                        // Caso "2", lista todos os clientes cadastrados
                    case 2 -> {
                        List<Cliente> clientes = clienteADO.listarClientes();
                        for (Cliente cliente : clientes) {
                            System.out.println(cliente.getClienteId() + " | " + cliente.getNome() + " | " + cliente.getEmail() + " | " + cliente.getTelefone());
                        }
                    }

                    case 3 -> {
                        // Caso "3", busca um cliente pelo ID fornecido pelo usuário
                        System.out.print("ID do Cliente: ");
                        int buscarId = scanner.nextInt();
                        Cliente cliente = clienteADO.buscarCliente(buscarId);
                        if (cliente != null) {
                            System.out.println("Cliente encontrado: " + cliente.getNome());
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                    }

                    case 4 -> {
                        // Caso "4", permite atualizar as informações de um cliente existente
                        System.out.print("ID do Cliente: ");
                        int atualizarId = scanner.nextInt();
                        scanner.nextLine(); 

                        Cliente clienteAtualizar = clienteADO.buscarCliente(atualizarId);
                        if (clienteAtualizar != null) {
                            System.out.print("Novo Nome: ");
                            clienteAtualizar.setNome(scanner.nextLine());
                            System.out.print("Novo Email: ");
                            clienteAtualizar.setEmail(scanner.nextLine());
                            System.out.print("Novo Telefone: ");
                            clienteAtualizar.setTelefone(scanner.nextLine());
                            clienteADO.atualizarCliente(clienteAtualizar);
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                    }

                    case 5 -> {
                        // Caso "5", permite excluir um cliente pelo ID
                        System.out.print("ID do Cliente: ");
                        int excluirId = scanner.nextInt();
                        
                        // Chama o método excluirCliente para remover o cliente do banco de dados
                        clienteADO.excluirCliente(excluirId);
                        System.out.println("Cliente excluído com sucesso!");
                    }

                    case 0 -> {
                        // Caso "0", fecha a conexão com o banco e encerra o programa
                        clienteADO.fecharConexao();
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}