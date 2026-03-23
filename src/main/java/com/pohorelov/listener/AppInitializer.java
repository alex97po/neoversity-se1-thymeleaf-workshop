package com.pohorelov.listener;

import com.pohorelov.model.LeaderboardEntry;
import com.pohorelov.repository.QuizRepository;
import com.pohorelov.util.ThymeleafConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Ініціалізує застосунок при старті.
 *
 * Ключі атрибутів ServletContext:
 *   "quizRepository" → QuizRepository
 *   "leaderboard"    → CopyOnWriteArrayList<LeaderboardEntry>
 */
@WebListener
public class AppInitializer implements ServletContextListener {

    public static final String QUIZ_REPOSITORY = "quizRepository";
    public static final String LEADERBOARD     = "leaderboard";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var ctx = sce.getServletContext();

        // 1. Ініціалізуємо Thymeleaf (один раз — дорога операція)
        ThymeleafConfig.init();

        // 2. Репозиторій квізів (immutable дані)
        ctx.setAttribute(QUIZ_REPOSITORY, new QuizRepository());

        // 3. Лідерборд — CopyOnWriteArrayList:
        //    - Потоко-безпечний для читання (без блокування)
        //    - Запис — Copy-on-Write: кожен add() копіює весь масив
        //    - Підходить коли читань БАГАТО, записів МАЛО (саме наш випадок)
        ctx.setAttribute(LEADERBOARD, new CopyOnWriteArrayList<LeaderboardEntry>());

        ctx.log("=== Quiz Workshop запущено ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("=== Quiz Workshop зупинено ===");
    }
}
