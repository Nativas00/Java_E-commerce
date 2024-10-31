package Cruds;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            ClienteADO clienteADO = new ClienteADO();
            while (true) {
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Listar Clientes");
                System.out.println("3. Buscar Cliente");
                System.out.println("4. Atualizar Cliente");
                System.out.println("5. Excluir Cliente");
                System.out.println("0. Sair");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        // Cadastrar Cliente
                        System.out.print("ID do Cliente: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = scanner.nextLine();
                        Cliente novoCliente = new Cliente(id, nome, email, telefone, new Date(System.currentTimeMillis()));
                        clienteADO.cadastrarCliente(novoCliente);
                        System.out.println("Cliente cadastrado com sucesso!");
                        break;

                    case 2:
                        // Listar Clientes
                        List<Cliente> clientes = clienteADO.listarClientes();
                        for (Cliente cliente : clientes) {
                            System.out.println(cliente.getClienteId() + " | " + cliente.getNome() + " | " + cliente.getEmail() + " | " + cliente.getTelefone());
                        }
                        break;

                    case 3:
                        // Buscar Cliente
                        System.out.print("ID do Cliente: ");
                        int buscarId = scanner.nextInt();
                        Cliente cliente = clienteADO.buscarCliente(buscarId);
                        if (cliente != null) {
                            System.out.println("Cliente encontrado: " + cliente.getNome());
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;

                    case 4:
                        // Atualizar Cliente
                        System.out.print("ID do Cliente: ");
                        int atualizarId = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
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
                        break;

                    case 5:
                        // Excluir Cliente
                        System.out.print("ID do Cliente: ");
                        int excluirId = scanner.nextInt();
                        clienteADO.excluirCliente(excluirId);
                        System.out.println("Cliente excluído com sucesso!");
                        break;

                    case 0:
                        // Sair
                        clienteADO.fecharConexao();
                        System.out.println("Saindo...");
                        return;

                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}