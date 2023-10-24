DROP DATABASE IF EXISTS parola;
CREATE DATABASE parola;


USE parola;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       credits INT NOT NULL
);

CREATE TABLE quiz (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      theme VARCHAR(255) NOT NULL
);


CREATE TABLE vraag (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       question_text TEXT NOT NULL,
                       is_multiple_choice BOOLEAN NOT NULL,
                       quiz_id INT NOT NULL,
                       FOREIGN KEY (quiz_id) REFERENCES quiz (id)
);
INSERT INTO users (username, password, credits) VALUES ('user123', 'password123', 1000);

INSERT INTO quiz (name, theme) VALUES ('Quiz 1', 'Theme A');
INSERT INTO quiz (name, theme) VALUES ('Quiz 2', 'Theme B');

INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Question 1', true, 1);
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Question 2', false, 1);
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Question 3', true, 2);
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES ('Question 4', false, 2);

