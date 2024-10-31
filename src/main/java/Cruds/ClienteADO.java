package Cruds;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteADO {
    private Connection connection;

    // Construtor para abrir a conexão
    public ClienteADO(String url, String usuario, String senha) throws SQLException {
        this.connection = DriverManager.getConnection(url, usuario, senha);
    }

    // Método para cadastrar um novo cliente
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (Cliente_ID, Nome, Email, Telefone, Data_Cadastro) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getClienteId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setDate(5, cliente.getDataCadastro());
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("Cliente_ID"),
                    rs.getString("Nome"),
                    rs.getString("Email"),
                    rs.getString("Telefone"),
                    rs.getDate("Data_Cadastro")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    // Método para pesquisar um cliente pelo ID
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
        return null;
    }

    // Método para atualizar um cliente
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

    // Método para excluir um cliente
    public void excluirCliente(int clienteId) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE Cliente_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        }
    }

    // Método para fechar a conexão
    public void fecharConexao() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
