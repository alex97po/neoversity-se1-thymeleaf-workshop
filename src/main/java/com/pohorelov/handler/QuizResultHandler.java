package com.pohorelov.handler;

import static com.pohorelov.listener.AppInitializer.QUIZ_REPOSITORY;

import com.pohorelov.model.QuizQuestionResult;
import com.pohorelov.model.QuizSession;
import com.pohorelov.repository.QuizRepository;
import com.pohorelov.util.CookieUtil;
import com.pohorelov.util.ThymeleafConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuizResultHandler implements Handler {

  @Override
  public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession httpSession = req.getSession(false);
    if (httpSession == null) {
      resp.sendRedirect(req.getContextPath() + "/");
      return;
    }
    QuizSession quizSession = (QuizSession) httpSession.getAttribute("quizSession");
    QuizRepository quizRepository =
        (QuizRepository) req.getServletContext().getAttribute(QUIZ_REPOSITORY);
    final var quiz = quizRepository.findById(quizSession.getQuizId()).orElseThrow();

    List<QuizQuestionResult> resultList = new ArrayList<>();
    int score = 0;

    for (int i = 0; i <= quiz.getQuestionCount(); i++) {
      final var question = quiz.getQuestions().get(i);
      final var answer = quizSession.getAnswer(i);
      final var result = new QuizQuestionResult(question, answer);
      resultList.add(result);
      if (result.isCorrect()) score++;
    }
    long duration = quizSession.elapsedSeconds();

    httpSession.invalidate();

    CookieUtil.set(resp, "quiz_result_" + quizSession.getQuizId(), score + ":" + quiz.getQuestionCount() + ":" + duration, 24 * 60 * 60);

    Map<String, Object> model = new HashMap<>();
    model.put("quiz", quiz);
    model.put("score", score);
    model.put("total", quiz.getQuestionCount());
    model.put("nickname", quizSession.getNickname());
    model.put("questionResults", resultList);

    ThymeleafConfig.render("result", model, req, resp);
  }
}
