package com.example.hp_awareness_app;

public class FAQ {
    private String question,answer;

    public FAQ(String q, String a) {
        question = q;
        answer = a;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
