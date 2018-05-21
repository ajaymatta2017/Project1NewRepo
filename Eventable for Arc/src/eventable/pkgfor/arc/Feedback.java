/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class Feedback {
    
    public SimpleStringProperty feedbackQuestion;
    public SimpleStringProperty feedbackAnswer;

    public Feedback(String feedbackQuestion, String feedbackAnswer) {
        this.feedbackQuestion = new SimpleStringProperty(feedbackQuestion);
        this.feedbackAnswer = new SimpleStringProperty(feedbackAnswer);
    }
    
    public String getFeedbackQuestion() {
        return feedbackQuestion.get();
    }

    public String getFeedbackAnswer() {
        return feedbackAnswer.get();
    }
}