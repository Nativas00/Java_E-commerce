package Cruds;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
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
                    case 1:
                        // Caso o usuário selecione "1", permite cadastrar um novo cliente
                        System.out.print("ID do Cliente: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer do scanner
                        
                        // Captura os demais dados do cliente
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = scanner.nextLine();
                        
                        // Cria um novo objeto Cliente com os dados capturados
                        Cliente novoCliente = new Cliente(id, nome, email, telefone, new Date(System.currentTimeMillis()));
                        
                        // Usa ClienteADO para inserir o novo cliente no banco de dados
                        clienteADO.cadastrarCliente(novoCliente);
                        System.out.println("Cliente cadastrado com sucesso!");
                        break;

                    case 2:
                        // Caso "2", lista todos os clientes cadastrados
                        List<Cliente> clientes = clienteADO.listarClientes(); // Obtem lista de clientes
                        
                        // Exibe as informações de cada cliente no console
                        for (Cliente cliente : clientes) {
                            System.out.println(cliente.getClienteId() + " | " + cliente.getNome() + " | " + cliente.getEmail() + " | " + cliente.getTelefone());
                        }
                        break;

                    case 3:
                        // Caso "3", busca um cliente pelo ID fornecido pelo usuário
                        System.out.print("ID do Cliente: ");
                        int buscarId = scanner.nextInt();
                        
                        // Chama o método buscarCliente de ClienteADO para localizar o cliente
                        Cliente cliente = clienteADO.buscarCliente(buscarId);
                        
                        // Exibe o resultado da busca
                        if (cliente != null) {
                            System.out.println("Cliente encontrado: " + cliente.getNome());
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;

                    case 4:
                        // Caso "4", permite atualizar as informações de um cliente existente
                        System.out.print("ID do Cliente: ");
                        int atualizarId = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer do scanner
                        
                        // Busca o cliente pelo ID
                        Cliente clienteAtualizar = clienteADO.buscarCliente(atualizarId);
                        
                        // Se o cliente for encontrado, solicita novos dados para atualização
                        if (clienteAtualizar != null) {
                            System.out.print("Novo Nome: ");
                            clienteAtualizar.setNome(scanner.nextLine());
                            System.out.print("Novo Email: ");
                            clienteAtualizar.setEmail(scanner.nextLine());
                            System.out.print("Novo Telefone: ");
                            clienteAtualizar.setTelefone(scanner.nextLine());
                            
                            // Atualiza o cliente no banco de dados
                            clienteADO.atualizarCliente(clienteAtualizar);
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;

                    case 5:
                        // Caso "5", permite excluir um cliente pelo ID
                        System.out.print("ID do Cliente: ");
                        int excluirId = scanner.nextInt();
                        
                        // Chama o método excluirCliente para remover o cliente do banco de dados
                        clienteADO.excluirCliente(excluirId);
                        System.out.println("Cliente excluído com sucesso!");
                        break;

                    case 0:
                        // Caso "0", fecha a conexão com o banco e encerra o programa
                        clienteADO.fecharConexao();
                        System.out.println("Saindo...");
                        return; // Sai do loop e encerra o método main

                    default:
                        // Caso o usuário insira uma opção inválida
                        System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            // Captura e exibe qualquer exceção que ocorrer durante a execução do programa
            e.printStackTrace();
        } finally {
            // Garante que o scanner será fechado após o término do programa
            scanner.close();
        }
    }
}