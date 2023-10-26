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

CREATE TABLE user_played(
    Quiz_id INT,
    user_id INT PRIMARY KEY,
    Heeft_gespeeld BOOLEAN NOT NULL,
    FOREIGN KEY (Quiz_ID) REFERENCES quiz (id),
    FOREIGN KEY (user_id) REFERENCES  users (id)
);
-- Insert a user
INSERT INTO users (username, password, credits) VALUES ('user123', 'password123', 1000);

-- Insert multiple quizzes with questions and answers
-- Quiz 1
INSERT INTO quiz (name, theme) VALUES ('Quiz 1', 'Theme A');


-- Questions for Quiz 1
-- 4 multiple-choice questions
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES
                                                                   ('Multiple Choice Question 1', true, 1),
                                                                   ('Multiple Choice Question 2', true, 1),
                                                                   ('Multiple Choice Question 3', true, 1),
                                                                   ('Multiple Choice Question 4', true, 1);

-- 4 non-multiple-choice questions
INSERT INTO vraag (question_text, is_multiple_choice, quiz_id) VALUES
                                                                   ('Non-Multiple Choice Question 1', false, 1),
                                                                   ('Non-Multiple Choice Question 2', false, 1),
                                                                   ('Non-Multiple Choice Question 3', false, 1),
                                                                   ('Non-Multiple Choice Question 4', false, 1);

-- Answers for the questions
-- Answers for Multiple Choice Question 1
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
                    ('Option A', true,1),
                    ('Option B', false,1),
                    ('Option C', false,1);

-- Answers for Multiple Choice Question 2
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
                    ('Option X', true, 2),
                    ('Option Y', false, 2),
                    ('Option Z', false, 2);

-- Answers for Multiple Choice Question 3
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
                    ('Choice Alpha', true, 3),
                    ('Choice Beta', false, 3),
                    ('Choice Gamma', false, 3);

-- Answers for Multiple Choice Question 4
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
                    ('Answer 1', true, 4),
                    ('Answer 2', false, 4),
                    ('Answer 3', false, 4);

-- Answers for Non-Multiple Choice Questions
-- For non-multiple-choice questions, there is only one correct answer.

-- Question 5
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
    ('Answer for Question 5', true, 5);

-- Question 6
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
    ('Answer for Question 6', true,6);

-- Question 7
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
    ('Answer for Question 7', true, 7);

-- Question 8
INSERT INTO antwoord (answer_text, is_juist_antwoord, vraag_id) VALUES
    ('Answer for Question 8', true, 8);

