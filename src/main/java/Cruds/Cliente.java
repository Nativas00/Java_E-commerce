package Cruds;

/**
 * Representa um cliente com informações como ID, nome, email, telefone e data de cadastro.
 */
public class Cliente {
    private int clienteId; // ID do cliente
    private String nome;    // Nome do cliente
    private String email;   // Email do cliente
    private String telefone; // Telefone do cliente
    private java.sql.Date dataCadastro; // Data do cadastro do cliente

    /**
     * Construtor para inicializar um cliente com todos os atributos.
     *
     * @param clienteId      o ID do cliente
     * @param nome           o nome do cliente
     * @param email          o email do cliente
     * @param telefone       o telefone do cliente
     * @param dataCadastro   a data de cadastro do cliente
     */
    public Cliente(int clienteId, String nome, String email, String telefone, java.sql.Date dataCadastro) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

    /**
     * Obtém o ID do cliente.
     * @return o ID do cliente
     */
    public int getClienteId() { return clienteId; }

    /**
     * Define o ID do cliente.
     * @param clienteId o novo ID do cliente
     */
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    /**
     * Obtém o nome do cliente.
     * @return o nome do cliente
     */
    public String getNome() { return nome; }

    /**
     * Define o nome do cliente.
     * @param nome o novo nome do cliente
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Obtém o email do cliente.
     * @return o email do cliente
     */
    public String getEmail() { return email; }

    /**
     * Define o email do cliente.
     * @param email o novo email do cliente
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Obtém o telefone do cliente.
     * @return o telefone do cliente
     */
    public String getTelefone() { return telefone; }

    /**
     * Define o telefone do cliente.
     * @param telefone o novo telefone do cliente
     */
    public void setTelefone(String telefone) { this.telefone = telefone; }

    /**
     * Obtém a data de cadastro do cliente.
     * @return a data de cadastro do cliente
     */
    public java.sql.Date getDataCadastro() { return dataCadastro; }

    /**
     * Define a data de cadastro do cliente.
     * @param dataCadastro a nova data de cadastro do cliente
     */
    public void setDataCadastro(java.sql.Date dataCadastro) { this.dataCadastro = dataCadastro; }
}