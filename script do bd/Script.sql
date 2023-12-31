/*
	Script para a criação do banco de dados para uma Loja de Peças Automotivas.
*/

create table cliente (
    id serial not null primary key,
	nome varchar(100),
	cpf varchar(13),
	telefone varchar(15),
	data_nascimento date,
	
	uf varchar(2),
	cep varchar(10),
	cidade varchar(50),
	rua varchar(50),
	numero int
);

insert into cliente(nome, cpf, data_nascimento) values ('Calebe', '12345678910', '2023-12-07');
insert into cliente(nome, cpf, data_nascimento) values ('Henrique', '66677788899', '2023-12-07');

create table funcionario (
	id serial not null primary key,
	nome varchar(100),
	cpf varchar(13),
	telefone varchar(15),
	data_nascimento date,
	
	usuario varchar(20),
	senha varchar(20),
	tipo_usuario char,
	
	salario decimal(18,4) check (salario >= 0),
	
	uf varchar(2),
	cep varchar(10),
	cidade varchar(50),
	rua varchar(50),
	numero int
);

insert into funcionario(nome, cpf, data_nascimento, usuario, senha, tipo_usuario, salario) values('Lucas', '11122233344', '2023-12-07', 'admin', 'admin', 'A', 5000.0);
insert into funcionario(nome, cpf, data_nascimento, usuario, senha, tipo_usuario, salario) values('Igor', '55566677788', '2023-12-07', 'igor', 'senha', 'C', 3000.0);

create table venda(
	id serial not null primary key,
	valor decimal(18,4),
	data_venda timestamp,
	id_funcionario int,
	id_cliente int,
	foreign key (id_funcionario) references funcionario (id),
	foreign key (id_cliente) references cliente (id)
);

create table unidade(
	id serial not null primary key,
	descricao varchar(10)
);

insert into unidade(descricao) values('Unidade');
insert into unidade(descricao) values('L');

create table produto(
	id serial not null primary key,
	nome varchar(100),
	preco decimal(18,4) check (preco >= 0),
	estoque int,
	id_unidade int,
	foreign key (id_unidade) references unidade (id)
);

insert into produto(nome, preco, estoque, id_unidade) values ('Roda', 300, 12, 1);
insert into produto(nome, preco, estoque, id_unidade) values ('Oléo', 1, 100, 2);

create table itens_venda(
	id serial not null primary key,
	quantidade_produto decimal(4,0),
	id_venda int,
	id_produto int,
	foreign key (id_venda) references venda (id),
	foreign key (id_produto) references produto (id)
);

create table fornecedor(
	id serial not null primary key,
	nome varchar(100),
	telefone varchar(15),
	cnpj varchar(14),
	
	uf varchar(2),
	cep varchar(10),
	cidade varchar(50),
	rua varchar(50),
	numero int
);

insert into fornecedor(nome, cnpj) values ('KRMAI', '12345678000100');

create table compra(
	id serial not null primary key,
	data_requerimento timestamp,
	valor decimal(18,4),
	
	id_fornecedor int,
	foreign key (id_fornecedor) references fornecedor(id)
);

create table itens_compra(
	id serial not null primary key,
	quantidade decimal(4,0),
	
	id_compra int,
	id_produto int,
	
	foreign key (id_compra) references compra(id),
	foreign key (id_produto) references produto(id)
);

/*
	Função Verificar Login
*/

create or replace function verificarLogin(usuariop text, senhap text) returns integer
as $$ 
begin
	return (select id from funcionario where usuario = usuariop and senha = senhap);
end; $$
language plpgsql;

/*
	Triggers Compra
*/

create or replace function f_insert_estoque_compra()
returns trigger
as
$$
begin
	update produto set estoque = produto.estoque + new.quantidade where new.id_produto = produto.id;
	return new;
end;
$$
language 'plpgsql';

create trigger insert_estoque_compra
after insert on itens_compra
for each row
execute procedure f_insert_estoque_compra();


create or replace function f_update_estoque_compra()
returns trigger
as
$$
begin
	if(new.quantidade != old.quantidade) then
		update produto set estoque = produto.estoque + (new.quantidade - old.quantidade) where produto.id =  new.id_produto;
		return new;
	else
		return new;
	end if;
end;
$$
language 'plpgsql';

create trigger update_estoque_compra
after update on itens_compra
for each row
execute procedure f_update_estoque_compra();


create or replace function f_timestamp_compra()
returns trigger
as
$$
begin
	update compra set data_requerimento = (select NOW()::TIMESTAMP) where id = new.id;
	return new;
end;
$$
language 'plpgsql';

insert into unidade(descricao) values ('teste');

create trigger timestamp_compra
after insert on compra
for each row
execute procedure f_timestamp_compra();

/*
	Triggers Venda
*/

create or replace function f_insert_estoque_venda()
returns trigger
as
$$
begin
	update produto set estoque = produto.estoque - new.quantidade_produto where new.id_produto = produto.id;
	return new;
end;
$$
language 'plpgsql';

create or replace trigger insert_estoque_venda
after insert on itens_venda
for each row
execute procedure f_insert_estoque_venda();


create or replace function f_update_estoque_venda()
returns trigger
as
$$
begin
	if(new.quantidade_produto != old.quantidade_produto) then
		update produto set estoque = produto.estoque - (new.quantidade_produto - old.quantidade_produto) where produto.id =  new.id_produto;
		return new;
	else
		return new;
	end if;
end;
$$
language 'plpgsql';

create or replace trigger update_estoque_venda
after update on itens_venda
for each row
execute procedure f_update_estoque_venda();


create or replace function f_timestamp_venda()
returns trigger
as
$$
begin
	update venda set data_venda = (select NOW()::TIMESTAMP) where id = new.id;
	return new;
end;
$$
language 'plpgsql';

create or replace trigger timestamp_venda
after insert on venda
for each row
execute procedure f_timestamp_venda();