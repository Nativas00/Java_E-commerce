-- Criar o banco de dados e usar o banco
CREATE DATABASE E_commerce;
USE E_commerce;

-- Criar a tabela Clientes e adicionar um registro
CREATE TABLE Clientes (
    Cliente_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Telefone VARCHAR(15),
    Data_Cadastro DATE NOT NULL
);

INSERT INTO Clientes (Nome, Email, Telefone, Data_Cadastro)
VALUES ('Maria Silva', 'maria.silva@hotmail.com', '61923456789', CURDATE()),
       ('Robson Teixeira de Assis', 'robsonteixeiradeassis12@gmail.com', '26933422466', CURDATE());

-- Criar a tabela Enderecos e adicionar um registro
CREATE TABLE Enderecos (
    Endereco_ID INT PRIMARY KEY AUTO_INCREMENT,
    Cliente_ID INT,
    Rua VARCHAR(100) NOT NULL,
    Cidade VARCHAR(50) NOT NULL,
    Estado VARCHAR(2) NOT NULL,
    CEP VARCHAR(10) NOT NULL,
    FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID)
);

INSERT INTO Enderecos (Cliente_ID, Rua, Cidade, Estado, CEP)
VALUES (1, 'Rua das Flores', 'São Paulo', 'SP', '12345-678');

-- Criar a tabela Fornecedores e adicionar um registro
CREATE TABLE Fornecedores (
    Fornecedor_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Contato VARCHAR(100),
    Telefone VARCHAR(15),
    Email VARCHAR(100)
);

INSERT INTO Fornecedores (Nome, Contato, Telefone, Email)
VALUES ('Carlos Almeida', 'Fornecedor A', '987654321', 'fornecedorA@gmail.com'),
('Takamasa Nomuro', 'Taka-san', '818065455086', 'takanomurosan@line.me');

-- Criar a tabela Categorias e adicionar um registro
CREATE TABLE Categorias (
    Categoria_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Descricao TEXT
);

INSERT INTO Categorias (Nome, Descricao)
VALUES ('Eletrônicos', 'Produtos eletronicos em geral');

-- Criar a tabela Produtos e adicionar um registro
CREATE TABLE Produtos (
    Produto_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Descricao TEXT,
    Preco DECIMAL(10, 2) NOT NULL,
    Estoque INT NOT NULL,
    Categoria_ID INT,
    Fornecedor_ID INT,
    FOREIGN KEY (Categoria_ID) REFERENCES Categorias(Categoria_ID),
    FOREIGN KEY (Fornecedor_ID) REFERENCES Fornecedores(Fornecedor_ID)
);

INSERT INTO Produtos (Nome, Descricao, Preco, Estoque, Categoria_ID, Fornecedor_ID)
VALUES ('Smartphone', 'Smartphone com 64GB de memoria', 999.99, 10, 1, 1);

-- Criar a tabela Status_Pedido e adicionar um registro
CREATE TABLE Status_Pedido (
    Status_ID INT PRIMARY KEY AUTO_INCREMENT,
    Descricao VARCHAR(50) NOT NULL
);

INSERT INTO Status_Pedido (Descricao)
VALUES ('Pendente');

-- Criar a tabela Vendas e adicionar um registro
CREATE TABLE Vendas (
    Venda_ID INT PRIMARY KEY AUTO_INCREMENT,
    Cliente_ID INT,
    Data_Venda DATE NOT NULL,
    Status_ID INT,
    FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID),
    FOREIGN KEY (Status_ID) REFERENCES Status_Pedido(Status_ID)
);

INSERT INTO Vendas (Cliente_ID, Data_Venda, Status_ID)
VALUES (1, CURDATE(), 1);

-- Criar a tabela Itens_Venda e adicionar um registro
CREATE TABLE Itens_Venda (
    Item_ID INT PRIMARY KEY AUTO_INCREMENT,
    Venda_ID INT,
    Produto_ID INT,
    Quantidade INT NOT NULL,
    Preco_Unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (Venda_ID) REFERENCES Vendas(Venda_ID),
    FOREIGN KEY (Produto_ID) REFERENCES Produtos(Produto_ID)
);

INSERT INTO Itens_Venda (Venda_ID, Produto_ID, Quantidade, Preco_Unitario)
VALUES (1, 1, 1, 999.99);

-- Criar a tabela Carrinho e adicionar um registro
CREATE TABLE Carrinho (
    Carrinho_ID INT PRIMARY KEY AUTO_INCREMENT,
    Cliente_ID INT,
    Produto_ID INT,
    Quantidade INT NOT NULL,
    FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID),
    FOREIGN KEY (Produto_ID) REFERENCES Produtos(Produto_ID)
);

INSERT INTO Carrinho (Cliente_ID, Produto_ID, Quantidade)
VALUES (1, 1, 2);

-- Criar a tabela Pagamentos e adicionar um registro
CREATE TABLE Pagamentos (
    Pagamento_ID INT PRIMARY KEY AUTO_INCREMENT,
    Venda_ID INT,
    Data_Pagamento DATE NOT NULL,
    Valor_Pago DECIMAL(10, 2) NOT NULL,
    Metodo_Pagamento VARCHAR(50),
    FOREIGN KEY (Venda_ID) REFERENCES Vendas(Venda_ID)
);

INSERT INTO Pagamentos (Venda_ID, Data_Pagamento, Valor_Pago, Metodo_Pagamento)
VALUES (1, CURDATE(), 999.99, 'Cartao de Credito');