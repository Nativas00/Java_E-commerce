package Cruds;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClienteADO {
    private Connection connection;  // Objeto de conexão para gerenciar a conexão com o banco de dados

    // Construtor que inicializa a conexão com o banco de dados
    public ClienteADO() throws SQLException, IOException {
        Properties props = new Properties();
        
        // Carrega as propriedades do arquivo db.properties usando ClassLoader
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Desculpe, o arquivo db.properties não foi encontrado");
                return;
            }
            props.load(input);
        }

        // Extrai as propriedades do arquivo
        String url = props.getProperty("db.url");         // URL do banco de dados
        String user = props.getProperty("db.username");   // Nome de usuário para login no banco
        String password = props.getProperty("db.password"); // Senha para login no banco

        // Cria a conexão com o banco usando as propriedades extraídas
        this.connection = DriverManager.getConnection(url, user, password);
    }

    // Método para cadastrar um novo cliente no banco de dados
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (Cliente_ID, Nome, Email, Telefone, Data_Cadastro) VALUES (?, ?, ?, ?, ?)";
        
        // Usa PreparedStatement para evitar SQL Injection e enviar os dados ao banco de forma segura
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getClienteId());      // Define o valor do ID do cliente
            stmt.setString(2, cliente.getNome());        // Define o nome do cliente
            stmt.setString(3, cliente.getEmail());       // Define o email do cliente
            stmt.setString(4, cliente.getTelefone());    // Define o telefone do cliente
            stmt.setDate(5, cliente.getDataCadastro());  // Define a data de cadastro do cliente
            
            // Executa a inserção no banco
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os clientes do banco de dados
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();  // Lista que armazenará os clientes recuperados
        String sql = "SELECT * FROM Clientes";      
        
        // Usa Statement para executar o comando SQL
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Loop para iterar por cada cliente
            while (rs.next()) {
                // Cria um novo objeto Cliente com os dados do ResultSet
                Cliente cliente = new Cliente(
                    rs.getInt("Cliente_ID"),       // ID do cliente
                    rs.getString("Nome"),          // Nome do cliente
                    rs.getString("Email"),         // Email do cliente
                    rs.getString("Telefone"),      // Telefone do cliente
                    rs.getDate("Data_Cadastro")    // Data de cadastro do cliente
                );
                
                // Adiciona o cliente à lista de clientes
                clientes.add(cliente);
            }
        }
        
        return clientes;
    }

    // Método para buscar um cliente específico pelo seu ID
    public Cliente buscarCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE Cliente_ID = ?"; // Comando SQL para buscar cliente por ID
        
        // Usa PreparedStatement para garantir segurança e flexibilidade
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId); // Define o valor do parâmetro para o ID do cliente
            ResultSet rs = stmt.executeQuery();
            
            // Verifica se a consulta retornou algum resultado
            if (rs.next()) {
                // Retorna o cliente
                return new Cliente(
                    rs.getInt("Cliente_ID"),
                    rs.getString("Nome"),
                    rs.getString("Email"),
                    rs.getString("Telefone"),
                    rs.getDate("Data_Cadastro")
                );
            }
        }
        
        // Retorna null se nenhum cliente com o ID especificado foi encontrado
        return null;
    }

    // Método para atualizar as informações de um cliente específico
    public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET Nome = ?, Email = ?, Telefone = ? WHERE Cliente_ID = ?";
        
        // Usa PreparedStatement para atualizar o cliente com segurança
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());        // Define o novo nome do cliente
            stmt.setString(2, cliente.getEmail());       // Define o novo email do cliente
            stmt.setString(3, cliente.getTelefone());    // Define o novo telefone do cliente
            stmt.setInt(4, cliente.getClienteId());      // Define o novo ID do cliente
            
            // Executa a atualização no banco
            stmt.executeUpdate();
        }
    }

    // Método para excluir um cliente do banco de dados pelo ID
    public void excluirCliente(int clienteId) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE Cliente_ID = ?"; // Comando SQL para exclusão de cliente
        
        // Usa PreparedStatement para excluir o cliente de forma segura
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);  // Define o ID do cliente a ser excluído
            
            // Executa a exclusão no banco
            stmt.executeUpdate();
        }
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}