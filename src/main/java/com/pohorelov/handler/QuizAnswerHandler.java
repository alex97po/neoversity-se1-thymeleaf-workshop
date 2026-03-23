package com.pohorelov.handler;

import static com.pohorelov.listener.AppInitializer.QUIZ_REPOSITORY;

import com.pohorelov.model.QuizSession;
import com.pohorelov.repository.QuizRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class QuizAnswerHandler implements Handler {

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

    String selectedAnswer = req.getParameter("answer");
    int currentIndex = quizSession.getCurrentIndex();

    quizSession.saveAnswer(currentIndex, selectedAnswer);

    quizSession.advance();

    if (quizSession.isFinished(quiz.getQuestionCount())) {
      resp.sendRedirect(req.getContextPath() + "/quiz/result");
    } else {
      resp.sendRedirect(req.getContextPath() + "/quiz/question");
    }
  }
}
