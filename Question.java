import java.util.List;

public abstract class Question {
    protected String questionText;
    protected String answer;

    public abstract boolean isCorrect(String userAnswer);

    public abstract String getFormattedQuestion(); // For displaying

    public class MultipleChoiceQuestion extends Question {
        private List<String> options;

        public MultipleChoiceQuestion(String questionText, List<String> options, String answer) {
            this.questionText = questionText;
            this.options = options;
            this.answer = answer;
        }

        @Override
        public boolean isCorrect(String userAnswer) {
            return userAnswer.equalsIgnoreCase(answer);
        }

        @Override
        public String getFormattedQuestion() {
            return questionText + "\n" + String.join("\n", options);
        }
    }
}
