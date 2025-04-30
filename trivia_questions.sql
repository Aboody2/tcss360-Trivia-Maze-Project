CREATE TABLE trivia_questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question TEXT NOT NULL,
    option_a TEXT NOT NULL,
    option_b TEXT NOT NULL,
    option_c TEXT NOT NULL,
    option_d TEXT NOT NULL,
    correct_answer TEXT NOT NULL,
    category TEXT,
    difficulty TEXT
);
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (1, 'What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'Paris', 'Geography', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (2, 'Who wrote *Romeo and Juliet*?', 'William Wordsworth', 'William Shakespeare', 'Charles Dickens', 'Jane Austen', 'William Shakespeare', 'Literature', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (3, 'What is the chemical symbol for water?', 'H2O', 'CO2', 'NaCl', 'O2', 'H2O', 'Science', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (4, 'Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Saturn', 'Mars', 'Science', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (5, 'What year did the Titanic sink?', '1912', '1905', '1920', '1898', '1912', 'History', 'Medium');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (6, 'How many continents are there on Earth?', '5', '6', '7', '8', '7', 'Geography', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (7, 'What is the hardest natural substance?', 'Iron', 'Diamond', 'Gold', 'Quartz', 'Diamond', 'Science', 'Medium');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (8, 'Who painted the Mona Lisa?', 'Van Gogh', 'Picasso', 'Da Vinci', 'Rembrandt', 'Da Vinci', 'Art', 'Medium');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (9, 'What is the square root of 144?', '11', '12', '13', '14', '12', 'Math', 'Easy');
INSERT INTO trivia_questions (id, question, option_a, option_b, option_c, option_d, correct_answer, category, difficulty) VALUES (10, 'What is the largest mammal?', 'Elephant', 'Whale Shark', 'Blue Whale', 'Giraffe', 'Blue Whale', 'Biology', 'Medium');
