package kr.hs.dgsw.quizapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionBean {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;

    private int id;
    private String question;
    private int score;
    private int answer;
    private String[] choices;
    private int type;
    private String quizDate;

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String date) {
        this.quizDate = date;
    }

    public void setQuizDate(Date date) {
        this.quizDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
