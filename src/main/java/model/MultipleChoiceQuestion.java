package model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private final List<String> myOptions;

    public MultipleChoiceQuestion(int theId, String theQuestionText, String theAnswer, List<String> theOptions) {
        super(theId, theQuestionText, theAnswer);
        myOptions = theOptions;
    }

    public List<String> getOptions() {
        return myOptions;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) {
            return false;
        }
        // Case-insensitive and trimmed comparison
        return userAnswer.trim().equalsIgnoreCase(getAnswer().trim());
    }

    @Override
    public String getType() {
        return "MC";
    }
}
