CREATE TABLE IF NOT EXISTS Diretor (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    local_nascimento VARCHAR(100),
    filmes_trabalhados TEXT
);

CREATE TABLE IF NOT EXISTS Genero (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Ator (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    local_nascimento VARCHAR(100),
    filmes_trabalhados TEXT
);

CREATE TABLE IF NOT EXISTS Filme (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    diretor_id INT,  -- Deve ser INT, compatível com Diretor(ID)
    genero_id INT,   -- Deve ser INT, compatível com Genero(ID)
    ano_lancamento INT,
    sinopse TEXT,
    poster_url VARCHAR(255),
    FOREIGN KEY (diretor_id) REFERENCES Diretor(ID),
    FOREIGN KEY (genero_id) REFERENCES Genero(ID)
);


CREATE TABLE IF NOT EXISTS Usuario (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nome_usuario VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Avaliacao (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    filme_id INT,    -- Deve ser INT, compatível com Filme(ID)
    usuario_id INT,  -- Deve ser INT, compatível com Usuario(ID)
    classificacao INT CHECK (classificacao BETWEEN 1 AND 5),
    comentario TEXT,
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID) REFERENCES Filme(ID),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(ID)
);




CREATE TABLE IF NOT EXISTS FilmeAtor (
    filme_id INT,    -- Deve ser INT, compatível com Filme(ID)
    ator_id INT,     -- Deve ser INT, compatível com Ator(ID)
    PRIMARY KEY (filme_id, ator_id),
    FOREIGN KEY (filme_id) REFERENCES Filme(ID),
    FOREIGN KEY (ator_id) REFERENCES Ator(ID)
);


-- Inserção de dados
INSERT INTO Diretor (nome, data_nascimento, local_nascimento, filmes_trabalhados) VALUES 
('Sean S. Cunningham', '1941-12-31', 'Nova Iorque, EUA', 'Sexta-feira 13'),
('George Lucas', '1944-05-14', 'Modesto, Califórnia, EUA', 'Star Wars: Episódio IV - Uma Nova Esperança'),
('Steven Spielberg', '1946-12-18', 'Cincinnati, Ohio, EUA', 'Tubarão, E.T. - O Extraterrestre, Os Caça-Fantasmas, Indiana Jones e Os Caçadores da Arca Perdida, De Volta para o Futuro'),
('Wes Craven', '1939-08-02', 'Cleveland, Ohio, EUA', 'Pânico'),
('Tobe Hooper', '1943-01-25', 'Austin, Texas, EUA', 'Os Caça-Fantasmas'),
('Robert Zemeckis', '1952-05-14', 'Chicago, Illinois, EUA', 'De Volta para o Futuro'),
('Ridley Scott', '1937-11-30', 'South Shields, Reino Unido', 'Alien, o Oitavo Passageiro');

INSERT INTO Ator (nome, data_nascimento, local_nascimento, filmes_trabalhados) VALUES 
('Mark Hamill', '1951-09-25', 'Oakland, Califórnia, EUA', 'Star Wars: Episódio IV - Uma Nova Esperança, E.T. - O Extraterrestre'),
('Harrison Ford', '1942-07-13', 'Chicago, Illinois, EUA', 'Star Wars: Episódio IV - Uma Nova Esperança, Guerra nas Estrelas, Indiana Jones e Os Caçadores da Arca Perdida, De Volta para o Futuro'),
('Carrie Fisher', '1956-10-21', 'Beverly Hills, Califórnia, EUA', 'Star Wars: Episódio IV - Uma Nova Esperança, Guerra nas Estrelas, E.T. - O Extraterrestre'),
('Jamie Lee Curtis', '1958-11-22', 'Los Angeles, Califórnia, EUA', 'Sexta-feira 13, Pânico'),
('Sigourney Weaver', '1949-10-08', 'Nova Iorque, EUA', 'Tubarão, Alien, o Oitavo Passageiro'),
('Bill Murray', '1950-09-21', 'Wilmette, Illinois, EUA', 'Os Caça-Fantasmas'),
('David Naughton', '1951-02-13', 'Hartford, Connecticut, EUA', 'Sexta-feira 13'),
('Heather O''Rourke', '1975-12-27', 'Santee, Califórnia, EUA', 'Poltergeist'),
('Robert MacNaughton', '1966-12-19', 'Nova Iorque, EUA', 'E.T. - O Extraterrestre'),
('Tom Skerritt', '1933-08-25', 'Detroit, Michigan, EUA', 'Tubarão, Alien, o Oitavo Passageiro'),
('Veronica Cartwright', '1949-04-20', 'Bristol, Reino Unido', 'Alien, o Oitavo Passageiro'),
('Ian Holm', '1931-09-12', 'Goodmayes, Reino Unido', 'Alien, o Oitavo Passageiro'),
('Paul Reiser', '1956-03-30', 'Nova Iorque, EUA', 'Aliens'),
('Lance Henriksen', '1940-05-05', 'Nova Iorque, EUA', 'Aliens'),
('Michael Biehn', '1956-07-31', 'Anniston, Alabama, EUA', 'Aliens, Alien³'),
('Carrie Henn', '1976-05-07', 'Panorama City, Califórnia, EUA', 'Aliens'),
('Winona Ryder', '1971-10-29', 'Winona, Minnesota, EUA', 'Alien: Resurrection'),
('Ron Perlman', '1950-04-13', 'Nova Iorque, EUA', 'Alien: Resurrection'),
('Charles Dance', '1946-10-10', 'Redditch, Reino Unido', 'Alien³'),
('Charles S. Dutton', '1951-01-30', 'Baltimore, Maryland, EUA', 'Alien³'),
('Christopher McDonald', '1955-02-15', 'Nova Iorque, EUA', 'Thelma & Louise');

INSERT INTO Genero (nome) VALUES 
('Terror'),
('Ficção Científica'),
('Ação');

INSERT INTO Filme (titulo, diretor_id, genero_id, ano_lancamento, sinopse) VALUES
('Sexta-feira 13', 1, 1, 1980, 'Um grupo de jovens é perseguido por um assassino no acampamento Crystal Lake.'),
('Star Wars: Episódio IV - Uma Nova Esperança', 2, 2, 1977, 'Um jovem fazendeiro se une a um grupo de rebeldes para combater um império tirânico.'),
('Tubarão', 3, 3, 1975, 'Um gigantesco tubarão branco ameaça uma cidade litorânea.'),
('Pânico', 4, 1, 1996, 'Uma série de assassinatos em uma cidade é inspirada nos filmes de terror.'),
('E.T. - O Extraterrestre', 3, 2, 1982, 'Um garoto faz amizade com um alienígena perdido na Terra.'),
('Guerra nas Estrelas', 3, 2, 1977, 'Um grupo de rebeldes luta contra um império galáctico.'),
('Os Caça-Fantasmas', 5, 1, 1984, 'Um grupo de caçadores de fantasmas salva Nova York de uma invasão paranormal.'),
('Indiana Jones e Os Caçadores da Arca Perdida', 3, 3, 1981, 'Um arqueólogo aventureiro busca uma arca sagrada perdida.'),
('De Volta para o Futuro', 6, 2, 1985, 'Um adolescente viaja no tempo com a ajuda de um cientista excêntrico.'),
('Alien, o Oitavo Passageiro', 7, 2, 1979, 'Tripulantes de uma nave espacial lutam para sobreviver a um alienígena assassino.');

INSERT INTO FilmeAtor (filme_id, ator_id) VALUES
(1, 4), -- Sexta-feira 13 - Jamie Lee Curtis
(2, 1), -- Star Wars: Episódio IV - Uma Nova Esperança - Mark Hamill
(2, 2), -- Star Wars: Episódio IV - Uma Nova Esperança - Harrison Ford
(3, 10), -- Tubarão - Tom Skerritt
(3, 17), -- Tubarão - Charles Dance
(4, 4), -- Pânico - Jamie Lee Curtis
(5, 1), -- E.T. - O Extraterrestre - Mark Hamill
(5, 2), -- E.T. - O Extraterrestre - Harrison Ford
(7, 6), -- Os Caça-Fantasmas - Bill Murray
(8, 2), -- Indiana Jones e Os Caçadores da Arca Perdida - Harrison Ford
(8, 10), -- Indiana Jones e Os Caçadores da Arca Perdida - Tom Skerritt
(9, 6), -- De Volta para o Futuro - Bill Murray
(10, 10); -- Alien, o Oitavo Passageiro - Tom Skerritt
