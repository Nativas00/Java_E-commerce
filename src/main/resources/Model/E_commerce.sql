CREATE DATABASE E_commerce;
USE E_commerce;
CREATE TABLE Clientes (
 Cliente_ID INT PRIMARY KEY,
 Nome VARCHAR(100) NOT NULL,
 Email VARCHAR(100) NOT NULL,
 Telefone VARCHAR(15),
 Data_Cadastro	 DATE NOT NULL
);
CREATE TABLE Enderecos (
 Endereco_ID INT PRIMARY KEY,
 Cliente_ID INT,
 Rua VARCHAR(100) NOT NULL,
 Cidade VARCHAR(50) NOT NULL,
 Estado VARCHAR(2) NOT NULL,
 CEP VARCHAR(10) NOT NULL,
 FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID)
);
CREATE TABLE Fornecedores (
 Fornecedor_ID INT PRIMARY KEY,
 Nome VARCHAR(100) NOT NULL,
 Contato VARCHAR(100),
 Telefone VARCHAR(15),
 Email VARCHAR(100)
);
CREATE TABLE Categorias (
 Categoria_ID INT PRIMARY KEY,
 Nome VARCHAR(100) NOT NULL,
 Descricao TEXT
);
CREATE TABLE Produtos (
 Produto_ID INT PRIMARY KEY,
 Nome VARCHAR(100) NOT NULL,
 Descricao TEXT,
 Preco DECIMAL(10, 2) NOT NULL,
 Estoque INT NOT NULL,
 Categoria_ID INT,
 Fornecedor_ID INT,
 FOREIGN KEY (Categoria_ID) REFERENCES Categorias(Categoria_ID),
 FOREIGN KEY (Fornecedor_ID) REFERENCES
Fornecedores(Fornecedor_ID)
);
CREATE TABLE Status_Pedido (
 Status_ID INT PRIMARY KEY,
 Descricao VARCHAR(50) NOT NULL
);
CREATE TABLE Vendas (
 Venda_ID INT PRIMARY KEY,
 Cliente_ID INT,
 Data_Venda DATE NOT NULL,
 Status_ID INT,
 FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID),
 FOREIGN KEY (Status_ID) REFERENCES Status_Pedido(Status_ID)
);
CREATE TABLE Itens_Venda (
 Item_ID INT PRIMARY KEY,
 Venda_ID INT,
 Produto_ID INT,
 Quantidade INT NOT NULL,
 Preco_Unitario DECIMAL(10, 2) NOT NULL,
 FOREIGN KEY (Venda_ID) REFERENCES Vendas(Venda_ID),
 FOREIGN KEY (Produto_ID) REFERENCES Produtos(Produto_ID)
);
CREATE TABLE Carrinho (
 Carrinho_ID INT PRIMARY KEY,
 Cliente_ID INT,
 Produto_ID INT,
 Quantidade INT NOT NULL,
 FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID),
 FOREIGN KEY (Produto_ID) REFERENCES Produtos(Produto_ID)
);
CREATE TABLE Pagamentos (
 Pagamento_ID INT PRIMARY KEY,
 Venda_ID INT,
 Data_Pagamento DATE NOT NULL,
 Valor_Pago DECIMAL(10, 2) NOT NULL,
 Metodo_Pagamento VARCHAR(50),
 FOREIGN KEY (Venda_ID) REFERENCES Vendas(Venda_ID)
);