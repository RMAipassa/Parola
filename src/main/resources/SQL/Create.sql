-- Drop and create the database
DROP DATABASE IF EXISTS parola;
CREATE DATABASE parola;
USE parola;

-- Create the users table
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       credits INT NOT NULL
);

-- Create the quiz table
CREATE TABLE quiz (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      theme VARCHAR(255) NOT NULL
);

-- Create the vraag table for questions
CREATE TABLE vraag (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       question_text TEXT NOT NULL,
                       is_multiple_choice BOOLEAN NOT NULL,
                       quiz_id INT NOT NULL,
                       FOREIGN KEY (quiz_id) REFERENCES quiz (id)
);

-- Create the antwoord table for answers
CREATE TABLE antwoord (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      answer_text TEXT NOT NULL,
                      is_juist_antwoord BOOLEAN NOT NULL,
                      vraag_id INT NOT NULL,
                      FOREIGN KEY (vraag_id) REFERENCES vraag (id)
);

CREATE TABLE User_Played (
                             UserPlayedID INT AUTO_INCREMENT PRIMARY KEY,
                             Quiz_id INT,
                             User_id INT,
                             Heeft_gespeeld BOOLEAN NOT NULL,
                             FOREIGN KEY (Quiz_ID) REFERENCES quiz (id),
                             FOREIGN KEY (User_id) REFERENCES users (id)
);

-- Insert a user
INSERT INTO users (username, password, credits) VALUES ('user123', 'password123', 1000);
INSERT INTO users (username, password, credits) VALUES ('user', 'password123', 20);


INSERT INTO quiz (name, theme) VALUES ('Wereldhoofdsteden', 'Geography');


-- Question 1
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wat is de hoofdstad van Frankrijk?', 1, 1);

INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Londen', 0, 1);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Berlijn', 0, 1);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Madrid', 0, 1);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Parijs', 1, 1);

-- Question 2
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke stad is de hoofdstad van Japan?', 1, 1);

INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Peking', 0, 2);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Tokio', 1, 2);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Seoel', 0, 2);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Bangkok', 0, 2);

-- Question 3
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('In welke stad vind je het Vaticaan?', 0, 1);

INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Rome', 1, 3);

-- Question 4
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wat is de hoofdstad van Australië?', 1, 1);

INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Canberra', 1, 4);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Sydney', 0, 4);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Melbourne', 0, 4);
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Brisbane', 0, 4);

-- Question 5
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke stad is de hoofdstad van Canada?', 0, 1);

INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Ottawa', 1, 5);

-- Question 6
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Noem de hoofdstad van Zuid-Afrika.', 0, 1);
-- Answers for Question 6
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Pretoria', 1, 6);

-- Question 7
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wat is de hoofdstad van India?', 0, 1);
-- Answers for Question 7
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('New Delhi', 1, 7);

-- Question 8
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('In welke stad ligt het Kremlin?', 0, 1);
-- Answers for Question 8
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Moskou', 1, 8);

-- Insert quiz data for "Beroemde Schilders"
INSERT INTO quiz (name, theme) VALUES ('Beroemde Schilders', 'Art');

-- Questions and answers for Quiz 2
-- Question 1
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie schilderde de "Mona Lisa"?', 0, 2);
-- Answers for Question 1
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Leonardo da Vinci', 1, 9);

-- Question 2
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke Nederlandse kunstenaar schilderde "Sterrennacht"?', 0, 2);
-- Answers for Question 2
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Vincent van Gogh', 1, 10);

-- Question 3
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie is de kunstenaar achter het schilderij "De Schreeuw"?', 0, 2);
-- Answers for Question 3
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Edvard Munch', 1, 11);

-- Question 4
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke schilder staat bekend om zijn zonnebloemen?', 0, 2);
-- Answers for Question 4
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Vincent van Gogh', 1, 12);

-- Question 5
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie maakte het schilderij "Guernica" dat de gevolgen van oorlog weergeeft?', 0, 2);
-- Answers for Question 5
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Pablo Picasso', 1, 13);

-- Question 6
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Noem de schilder van "Het Meisje met de Parel".', 0, 2);
-- Answers for Question 6
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Johannes Vermeer', 1, 14);

-- Question 7
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke kunstenaar schilderde "Het Laatste Avondmaal"?', 0, 2);
-- Answers for Question 7
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Leonardo da Vinci', 1, 15);

-- Question 8
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie is de maker van het schilderij "Zelfportret met afgesneden oor"?', 0, 2);
-- Answers for Question 8
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Vincent van Gogh', 1, 16);

-- Insert quiz data for "Beroemde Literaire Werken"
INSERT INTO quiz (name, theme) VALUES ('Beroemde Literaire Werken', 'Literature');

-- Questions and answers for Quiz 3
-- Question 1
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welk boek begint met de zin "Het was de beste der tijden, het was de slechtste der tijden"?', 0, 3);
-- Answers for Question 1
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('A Tale of Two Cities by Charles Dickens', 1, 17);

-- Question 2
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie schreef "Romeo en Julia"?', 0, 3);
-- Answers for Question 2
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('William Shakespeare', 1, 18);

-- Question 3
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('In welk boek vind je het personage Atticus Finch?', 0, 3);
-- Answers for Question 3
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('To Kill a Mockingbird by Harper Lee', 1, 19);

-- Question 4
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welk boek beschrijft de avonturen van Harry Potter?', 0, 3);
-- Answers for Question 4
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Harry Potter and the Sorcerer\'s Stone by J.K. Rowling', 1, 20);

-- Question 5
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Noem het beroemde werk waarin Jane Eyre de hoofdrol speelt.', 0, 3);
-- Answers for Question 5
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Jane Eyre by Charlotte Brontë', 1, 21);

-- Question 6
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Wie is de auteur van "Misdaad en straf"?', 0, 3);
-- Answers for Question 6
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Fyodor Dostoevsky', 1, 22);

-- Question 7
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welk boek vertelt het verhaal van een ontsnapte slaaf genaamd Sethe?', 0, 3);
-- Answers for Question 7
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Beloved by Toni Morrison', 1, 23);

-- Question 8
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Welke auteur schreef "Het Bernini Mysterie"?', 0, 3);
-- Answers for Question 8
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES ('Dan Brown', 1, 24);