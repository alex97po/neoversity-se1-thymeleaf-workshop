package com.pohorelov.handler;

import com.pohorelov.model.QuizSession;
import com.pohorelov.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class QuizStartHandler implements Handler {

  @Override
  public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String quizId = req.getParameter("id");

    final var nickname = CookieUtil.get(req, CookieUtil.QUIZ_NICKNAME).orElse("Анонім");

    HttpSession session = req.getSession();
    QuizSession quizSession = new QuizSession(quizId, nickname);
    session.setAttribute("quizSession", quizSession);

    resp.sendRedirect(req.getContextPath() + "/quiz/question");
  }

}
