package model;

public class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(int id, String questionText, String answer) {
        super(id, questionText, answer);
        if (!"true".equalsIgnoreCase(answer) && !"false".equalsIgnoreCase(answer)) {
            throw new IllegalArgumentException("Answer must be 'true' or 'false'");
        }
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        return userAnswer.trim().equalsIgnoreCase(getAnswer().trim());
    }

    @Override
    public String getType() {
        return "TF";  // Representing True/False question type
    }
}
