package com.pohorelov.servlet;

import com.pohorelov.handler.Handler;
import com.pohorelov.handler.HomeHandler;
import com.pohorelov.handler.ProfileHandler;
import com.pohorelov.handler.QuizAnswerHandler;
import com.pohorelov.handler.QuizQuestionHandler;
import com.pohorelov.handler.QuizResultHandler;
import com.pohorelov.handler.QuizStartHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class FrontController extends HttpServlet {

  // METHOD /path => Handler
  private final Map<String, Handler> ROUTES = new HashMap<>();

  @Override
  public void init(ServletConfig config) {
    ROUTES.put("GET /", new HomeHandler());
    ROUTES.put("POST /profile", new ProfileHandler());
    ROUTES.put("GET /quiz/start", new QuizStartHandler());
    ROUTES.put("GET /quiz/question", new QuizQuestionHandler());
    ROUTES.put("POST /quiz/answer", new QuizAnswerHandler());
    ROUTES.put("GET /quiz/result", new QuizResultHandler());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String path = req.getPathInfo();
    String method = req.getMethod();
    String key = method + " " + path;
    System.out.println(key);
    final var handler = ROUTES.get(key);
    handler.handle(req, resp);
  }

}
