package Cruds;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Classe responsável pelas operações de acesso aos dados (ADO) para a entidade Cliente.
 * Gerencia a conexão com o banco de dados e realiza operações CRUD (Create, Read, Update, Delete).
 */
public class ClienteADO {
    private Connection connection;

    /**
     * Construtor que inicializa a conexão com o banco de dados a partir do arquivo de propriedades.
     *
     * @throws SQLException se ocorrer um erro de SQL
     * @throws IOException se ocorrer um erro de I/O ao ler o arquivo de propriedades
     */
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

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        this.connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Cadastra um novo cliente no banco de dados.
     *
     * @param cliente o objeto Cliente a ser cadastrado
     * @throws SQLException se ocorrer um erro de SQL
     */
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (Nome, Email, Telefone, Data_Cadastro) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setDate(4, cliente.getDataCadastro());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setClienteId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retorna uma lista de todos os clientes cadastrados no banco de dados.
     *
     * @return uma lista de objetos Cliente
     * @throws SQLException se ocorrer um erro de SQL
     */
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Cria um novo objeto Cliente com os dados do ResultSet
                Cliente cliente = new Cliente(
                    rs.getInt("Cliente_ID"),
                    rs.getString("Nome"),
                    rs.getString("Email"),
                    rs.getString("Telefone"),
                    rs.getDate("Data_Cadastro")
                );
                
                // Adiciona o cliente à lista de clientes
                clientes.add(cliente);
            }
        }
        
        return clientes;
    }

    /**
     * Busca um cliente específico pelo seu ID.
     *
     * @param clienteId o ID do cliente a ser buscado
     * @return o objeto Cliente correspondente ou null se não encontrado
     * @throws SQLException se ocorrer um erro de SQL
     */
    public Cliente buscarCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE Cliente_ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
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

    /**
     * Atualiza as informações de um cliente específico.
     *
     * @param cliente o objeto Cliente com as novas informações
     * @throws SQLException se ocorrer um erro de SQL
     */
    public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET Nome = ?, Email = ?, Telefone = ? WHERE Cliente_ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getClienteId());
            
            stmt.executeUpdate();
        }
    }

    /**
     * Exclui um cliente do banco de dados pelo ID.
     *
     * @param clienteId o ID do cliente a ser excluído
     * @throws SQLException se ocorrer um erro de SQL
     */
    public void excluirCliente(int clienteId) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE Cliente_ID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            
            stmt.executeUpdate();
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao fechar a conexão
     */
    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}