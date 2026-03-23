package com.pohorelov.model;

import java.util.List;

public class Quiz {

    private final String id;
    private final String title;
    private final String description;
    private final int timeLimitSeconds;
    private final List<Question> questions;

    public Quiz(String id, String title, String description,
                int timeLimitSeconds, List<Question> questions) {
        this.id               = id;
        this.title            = title;
        this.description      = description;
        this.timeLimitSeconds = timeLimitSeconds;
        this.questions        = List.copyOf(questions);
    }

    public String getId()              { return id; }
    public String getTitle()           { return title; }
    public String getDescription()     { return description; }
    public int getTimeLimitSeconds()   { return timeLimitSeconds; }
    public List<Question> getQuestions() { return questions; }
    public int getQuestionCount()      { return questions.size(); }
}
