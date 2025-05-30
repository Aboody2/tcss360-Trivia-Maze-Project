package model;

import persistence.DatabaseHandler;

public class QuestionFactory {
    private final DatabaseHandler myDbHandler;

    public QuestionFactory(DatabaseHandler theDbHandler) {
        myDbHandler = theDbHandler;
    }

    public Question createQuestion() {
        String type = getRandomType();
        return myDbHandler.getRandomQuestion(type);
    }

    private String getRandomType() {
        String[] types = {"MC", "TF", "SA"};
        return types[(int) (Math.random() * types.length)];
    }
}
