package com.pohorelov.model;

import java.util.List;

public class Question {

    private final String text;
    private final List<Answer> answers;
    private final String correctKey;
    private final String explanation; // пояснення на сторінці результатів

    public Question(String text, List<Answer> answers,
                    String correctKey, String explanation) {
        this.text        = text;
        this.answers     = List.copyOf(answers);
        this.correctKey  = correctKey;
        this.explanation = explanation;
    }

    // -------------------------------------------------------------------------

    public String getText()          { return text; }
    public List<Answer> getAnswers() { return answers; }
    public String getCorrectKey()    { return correctKey; }
    public String getExplanation()   { return explanation; }

    public boolean isCorrect(String key) {
        return correctKey.equals(key);
    }

    /** Повертає текст варіанту за ключем (для відображення у результатах). */
    public String getAnswerText(String key) {
        if (key == null) return "—";
        return answers.stream()
                .filter(a -> a.key().equals(key))
                .map(Answer::text)
                .findFirst()
                .orElse("—");
    }
}
