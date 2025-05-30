package model;

import java.io.Serializable;

public abstract class Question implements Serializable {
    private final int myId;
    private final String myQuestionText;
    private final String myAnswer;

    protected Question(int theId, String theQuestionText, String theAnswer) {
        myId = theId;
        myQuestionText = theQuestionText;
        myAnswer = theAnswer;
    }

    public int getId() {
        return myId;
    }

    public String getQuestionText() {
        return myQuestionText;
    }

    public String getAnswer() {
        return myAnswer;
    }

    /**
     * Checks if the provided answer is correct.
     */
    public abstract boolean checkAnswer(String userAnswer);

    /**
     * Returns the type of question: "MC", "TF", "SA", etc.
     */
    public abstract String getType();
}
