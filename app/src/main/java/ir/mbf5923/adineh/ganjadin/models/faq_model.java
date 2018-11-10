package ir.mbf5923.adineh.ganjadin.models;

public class faq_model {
    private String question, answer;

    public faq_model(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
