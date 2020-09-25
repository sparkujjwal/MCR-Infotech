package com.codinghelper.mscii;

import com.google.firebase.database.DatabaseReference;

public class StudentQuestion {
    private String QuestionAsked,Answer,AskerImage,AnswererImage,AskerName,Topic,position;

    public StudentQuestion() {
    }

    public StudentQuestion(String QuestionAsked,String Answer,String AskerImage,String AnswererImage,String AskerName,String Topic,String position) {
        this.QuestionAsked = QuestionAsked;
        this.Answer=Answer;
        this.AskerImage=AskerImage;
        this.AnswererImage=AnswererImage;
        this.AskerName=AskerName;
        this.Topic=Topic;
        this.position=position;
    }

    public String getQuestionAsked() {
        return QuestionAsked;
    }
    public String getAnswer() {
        return Answer;
    }
    public String getAskerImage() {
        return AskerImage;
    }
    public String getAnswererImage() {
        return AnswererImage;
    }
    public String getAskerName() {
        return AskerName;
    }
    public String getTopic() {
        return Topic;
    }
    public String getposition() {
        return position;
    }


}
