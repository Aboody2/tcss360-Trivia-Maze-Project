package model;

public class ShortAnswerQuestion extends Question {
    public ShortAnswerQuestion(int id, String questionText, String answer) {
        super(id, questionText, answer);
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        // Case-insensitive, trimmed comparison
        return userAnswer.trim().equalsIgnoreCase(getAnswer().trim());
    }

    @Override
    public String getType() {
        return "";
    }
}
