package model;

import java.io.Serializable;

public class Door implements Serializable {
    private final Question myQuestion;
    private boolean myIsLocked;

    public Door(Question theQuestion) {
        myQuestion = theQuestion;
        myIsLocked = false;
    }

    public boolean isLocked() {
        return myIsLocked;
    }

    public void lock() {
        myIsLocked = true;
    }

    public Question getQuestion() {
        return myQuestion;
    }

    public boolean answerQuestion(String theAnswer) {
        boolean correct = myQuestion.checkAnswer(theAnswer);
        if (!correct) lock();
        return correct;
    }
}
