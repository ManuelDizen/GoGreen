package ar.edu.itba.paw.models;

public class FAQ {
    private long faqId;
    private String question;
    private String answer;

    public FAQ(long faqId, String question, String answer) {
        this.faqId = faqId;
        this.question = question;
        this.answer = answer;
    }

    public long getFaqId() {
        return faqId;
    }

    public void setFaqId(long faqId) {
        this.faqId = faqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
