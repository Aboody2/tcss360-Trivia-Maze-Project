package persistence;

import model.MultipleChoiceQuestion;
import model.Question;
import model.ShortAnswerQuestion;
import model.TrueFalseQuestion;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:questions.db";

    public Question getRandomQuestion(final String theType) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM questions WHERE type = '" + theType + "' ORDER BY RANDOM() LIMIT 1");

            if (rs.next()) {
                final int id = rs.getInt("id");
                final String text = rs.getString("text");
                final String answer = rs.getString("answer");
                final String optionsStr = rs.getString("options");
                final List<String> optionsList = optionsStr == null ? List.of() : Arrays.asList(optionsStr.split(";"));

                switch (theType) {
                    case "MC":
                        return new MultipleChoiceQuestion(id, text, answer, optionsList);
                    case "TF":
                        return new TrueFalseQuestion(id, text, answer);
                    case "SA":
                        return new ShortAnswerQuestion(id, text, answer);
                    default:
                        throw new IllegalArgumentException("Invalid question type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create questions table
            stmt.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT NOT NULL CHECK(type IN ('MC', 'TF', 'SA')), " +
                    "text TEXT NOT NULL, " +
                    "answer TEXT NOT NULL, " +
                    "options TEXT)");

            // Insert sample questions
            insertSampleQuestions(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSampleQuestions(Connection conn) throws SQLException {
        // Multiple Choice
        insertQuestion(conn, "MC", "What is 2 + 2?", "4", List.of("1", "2", "3", "4"));
        insertQuestion(conn, "MC", "Capital of France?", "Paris", List.of("Paris", "London", "Berlin", "Rome"));

        // True/False
        insertQuestion(conn, "TF", "The sky is blue.", "true", null);
        insertQuestion(conn, "TF", "Java is a compiled language.", "true", null);

        // Short Answer
        insertQuestion(conn, "SA", "What is the largest planet?", "Jupiter", null);
        insertQuestion(conn, "SA", "How many sides does a hexagon have?", "6", null);
    }

    private void insertQuestion(Connection conn, String type, String text,
                                String answer, List<String> options) throws SQLException {
        String optionsStr = null;
        if (options != null && !options.isEmpty()) {
            optionsStr = String.join(";", options);
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT OR IGNORE INTO questions (type, text, answer, options) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, type);
            pstmt.setString(2, text);
            pstmt.setString(3, answer);
            pstmt.setString(4, optionsStr);
            pstmt.executeUpdate();
        }
    }
}
