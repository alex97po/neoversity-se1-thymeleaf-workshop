package com.pohorelov.handler;

import static com.pohorelov.listener.AppInitializer.QUIZ_REPOSITORY;
import static com.pohorelov.util.CookieUtil.QUIZ_NICKNAME;

import com.pohorelov.repository.QuizRepository;
import com.pohorelov.util.CookieUtil;
import com.pohorelov.util.ThymeleafConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeHandler implements Handler {

  @Override
  public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    QuizRepository quizRepository =
        (QuizRepository) req.getServletContext().getAttribute(QUIZ_REPOSITORY);
    final var quizzes = quizRepository.findAll();

    final var nickname = CookieUtil.get(req, QUIZ_NICKNAME).orElse("");

    Map<String, Object> model = new HashMap<>();
    model.put("quizzes", quizzes);
    model.put("previousResults", new HashMap<String, int[]>());
    model.put("nickname", nickname);

    ThymeleafConfig.render("home", model, req, resp);

  }
}
