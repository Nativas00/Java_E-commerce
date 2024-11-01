package Cruds;

public class Cliente {
    private int clienteId;                 // ID do cliente
    private String nome;                    // Nome do cliente
    private String email;                   // Email do cliente
    private String telefone;                // Telefone do cliente
    private java.sql.Date dataCadastro;     // Data do cadastro do cliente

    // Construtor
    public Cliente(int clienteId, String nome, String email, String telefone, java.sql.Date dataCadastro) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

    // Métodos Getters e Setters para modificar os atributos
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public java.sql.Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(java.sql.Date dataCadastro) { this.dataCadastro = dataCadastro; }
}