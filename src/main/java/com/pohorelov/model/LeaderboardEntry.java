package com.pohorelov.model;

import java.time.Instant;

/**
 * Один запис у лідерборді.
 * Зберігається в ServletContext (CopyOnWriteArrayList).
 */
public record LeaderboardEntry(
        String  nickname,
        String  quizId,
        int     score,
        int     total,
        long    durationSeconds,
        Instant completedAt
) implements java.io.Serializable {

    /** Відсоток правильних відповідей. */
    public int percentage() {
        return total == 0 ? 0 : (int) Math.round(score * 100.0 / total);
    }
}
