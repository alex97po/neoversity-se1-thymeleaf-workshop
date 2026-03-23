package com.pohorelov.handler;

import static com.pohorelov.listener.AppInitializer.QUIZ_REPOSITORY;

import com.pohorelov.model.QuizSession;
import com.pohorelov.repository.QuizRepository;
import com.pohorelov.util.ThymeleafConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuizQuestionHandler implements Handler {

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

    int indexQuestion = quizSession.getCurrentIndex();

    final var question = quiz.getQuestions().get(indexQuestion);

    Map<String, Object> model = new HashMap<>();
    model.put("quiz", quiz);
    model.put("question", question);
    model.put("questionNumber", indexQuestion + 1);
    model.put("totalQuestions", quiz.getQuestionCount());
    model.put("startTime", quizSession.getStartTime());
    model.put("progress", (int) Math.round(indexQuestion * 100.0 / quiz.getQuestionCount()));

    ThymeleafConfig.render("question", model, req, resp);
  }
}
