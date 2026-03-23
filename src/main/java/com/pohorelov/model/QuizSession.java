package com.pohorelov.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Стан поточного проходження квізу.
 * Зберігається як атрибут HttpSession.
 *
 * Implements Serializable — вимога контейнера: атрибути сесії мають
 * бути серіалізовані для реплікації між нодами або персистентності при рестарті.
 */
public class QuizSession implements Serializable {

    public static final String SESSION_KEY = "quizSession";

    private final String quizId;
    private final String nickname;
    private final long   startTime;           // System.currentTimeMillis() при старті

    private int currentIndex = 0;             // індекс поточного питання
    private final Map<Integer, String> answers = new HashMap<>();
    // key = індекс питання, value = ключ вибраного варіанту (A/B/C/D або null)

    public QuizSession(String quizId, String nickname) {
        this.quizId    = quizId;
        this.nickname  = nickname;
        this.startTime = System.currentTimeMillis();
    }

    // -------------------------------------------------------------------------

    public String getQuizId()     { return quizId; }
    public String getNickname()   { return nickname; }
    public long   getStartTime()  { return startTime; }
    public int    getCurrentIndex() { return currentIndex; }

    public void saveAnswer(int index, String key) {
        answers.put(index, key);
    }

    public String getAnswer(int index) {
        return answers.get(index); // null якщо не відповів
    }

    public void advance() {
        currentIndex++;
    }

    public boolean isFinished(int totalQuestions) {
        return currentIndex >= totalQuestions;
    }

    /** Скільки секунд пройшло від старту. */
    public long elapsedSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    /** Скільки секунд залишилось (може бути від'ємним якщо час вийшов). */
    public long remainingSeconds(int timeLimitSeconds) {
        return timeLimitSeconds - elapsedSeconds();
    }
}
