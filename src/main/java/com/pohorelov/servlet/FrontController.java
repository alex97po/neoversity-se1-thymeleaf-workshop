package com.pohorelov.servlet;

import com.pohorelov.handler.Handler;
import com.pohorelov.handler.HomeHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class FrontController extends HttpServlet {

  // METHOD /path => Handler
  private final Map<String, Handler> ROUTES = new HashMap<>();

  @Override
  public void init(ServletConfig config) {
    ROUTES.put("GET /", new HomeHandler());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    String path = req.getServletPath();
    String method = req.getMethod();
    final var handler = ROUTES.get(method + " " + path);
    handler.handle(req, resp);
  }

}
