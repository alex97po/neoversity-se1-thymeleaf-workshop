package com.pohorelov.model;

/**
 * Результат відповіді на одне питання.
 * Використовується тільки на сторінці результатів — не зберігається в сесії.
 */
public class QuizQuestionResult {

    private final Question question;
    private final String   userKey;         // що обрав студент (може бути null)
    private final boolean  correct;

    public QuizQuestionResult(Question question, String userKey) {
        this.question = question;
        this.userKey  = userKey;
        this.correct  = question.isCorrect(userKey);
    }

    public Question getQuestion()        { return question; }
    public String   getUserKey()         { return userKey; }
    public boolean  isCorrect()          { return correct; }

    /** Текст варіанту, який обрав студент. */
    public String getUserAnswerText() {
        return question.getAnswerText(userKey);
    }

    /** Текст правильного варіанту. */
    public String getCorrectAnswerText() {
        return question.getAnswerText(question.getCorrectKey());
    }
}
