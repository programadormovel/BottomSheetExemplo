--SCRIPT DO BANCO DE DADOS AULA06
use master
go
drop DATABASE PRAP3
go
CREATE DATABASE PRAP3
GO
USE PRAP3
GO

CREATE TABLE login(
id INT PRIMARY KEY IDENTITY,
usuario VARCHAR(100) NOT NULL unique,
senha VARCHAR(20) NOT NULL,
nome VARCHAR(250) NOT NULL,
email VARCHAR(100) NOT NULL unique,
nivel_acesso VARCHAR(100) NOT NULL)
GO

INSERT INTO login VALUES ('admin', '123456', 'Administrador do Sistema', 'admin@admin.com', 0);
GO

select * from login;
GO

------------------------------------------------------------------------------------------------
-- TABELAS PARA O EXEMPLO DO BOTTOM_SHEET
------------------------------------------------------------------------------------------------

CREATE TABLE Colaborador(
id INT PRIMARY KEY IDENTITY,
nome VARCHAR(250) NOT NULL,
cargo VARCHAR(250) NOT NULL,
cpf VARCHAR(11) NOT NULL,
endereco VARCHAR(250) NOT NULL,
email VARCHAR(100) NOT NULL unique,
caminhoFoto VARCHAR(250) NULL,
foto varbinary(MAX) NULL)
GO

INSERT INTO Colaborador (nome, cargo, cpf, endereco, email, caminhoFoto, foto)
SELECT 'Adriano Domingues', 'Programador Móvel', '11111111111', 'Rua X, 77 - Jd. Paraíso', 
	'programadormovel@gmail.com', 'minha_foto.jpeg', *
FROM OPENROWSET(BULK N'C:\DOMTEC\ITB\2020\PRAP3\imagens\minha_foto.jpeg', SINGLE_BLOB) Load
GO

INSERT INTO Colaborador (nome, cargo, cpf, endereco, email, caminhoFoto, foto)
SELECT 'Neia Domingues', 'Veterinária', '11111111111', 'Rua X, 77 - Jd. Paraíso', 
	'neiadomingues@gmail.com', 'neia.jpg', *
FROM OPENROWSET(BULK N'C:\DOMTEC\ITB\2020\PRAP3\imagens\neia.jpg', SINGLE_BLOB) Load
GO

INSERT INTO Colaborador (nome, cargo, cpf, endereco, email, caminhoFoto, foto)
SELECT 'Meire Domingues', 'Professora de Línguas', '11111111111', 'Rua Júpiter, 87 - Jd. Celestial', 
	'meme@gmail.com', 'meire.jpg', *
FROM OPENROWSET(BULK N'C:\DOMTEC\ITB\2020\PRAP3\imagens\meire.jpg', SINGLE_BLOB) Load
GO

INSERT INTO Colaborador (nome, cargo, cpf, endereco, email, caminhoFoto, foto)
SELECT 'Samuel Nascimento', 'Educador', '11111111111', 'Rua Júpiter, 87 - Jd. Celestial', 
	'samuca@gmail.com', 'samuel.jpg', *
FROM OPENROWSET(BULK N'C:\DOMTEC\ITB\2020\PRAP3\imagens\samuel.jpg', SINGLE_BLOB) Load
GO

select * from Colaborador;
GO

CREATE TABLE login2(
id INT PRIMARY KEY IDENTITY,
usuario VARCHAR(100) NOT NULL unique,
senha VARCHAR(20) NOT NULL,
nivel_acesso VARCHAR(100) NOT NULL,
cod_colab int constraint fk_colab foreign key (cod_colab) references Colaborador (id))
GO

INSERT INTO login2 VALUES ('admin', '123456', 0, 1);
GO


select * from login2;
GO

select c.id, l.usuario, l.senha, c.nome, c.cargo, c.foto from Colaborador c
inner join login2 l
on c.id = l.cod_colab;

--------------------------------------------------------------------------------------------


CREATE TABLE produto(
codigo varchar(50) PRIMARY KEY,
descricao VARCHAR(100) NOT NULL unique,
qtde int not null,
valor_unit decimal(15,2) not null,
status int NOT NULL)   --0 INATIVO / 1 ATIVO
GO

INSERT INTO PRODUTO VALUES (1, 'Coca-cola', 10, 4.99, 1);
GO
INSERT INTO PRODUTO VALUES (2, 'Bolacha', 30, 2.99, 1);
GO
INSERT INTO PRODUTO VALUES (3, 'Chocolate', 100, 3.99, 1);
GO
INSERT INTO PRODUTO VALUES (4, 'Salgado', 20, 1.99, 1);
GO
INSERT INTO PRODUTO VALUES (5, 'Suco', 30, 4.99, 1);
GO


select * from produto;
GO

-----------------------------------------------------------------------------------

-- drop table login2
-- drop table Colaborador
